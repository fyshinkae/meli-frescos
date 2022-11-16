package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockResponseDTO;
import com.example.mercadofrescos.exception.InvalidBatchStockException;
import com.example.mercadofrescos.exception.InvalidPurchaseException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.model.enums.OrderBy;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.repository.IInboundOrderRepo;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.ISectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

    private final IBatchStockRepo repo;
    private final IProductService serviceProduct;
    private final ISectionService serviceSection;
    private final IInboundOrderRepo inboundOrderRepo;


    /**
     * Busca um BatchStock ou lança um erro caso não encontre
     * @author Gabriel
     * @param id do BatchStock
     */
    @Override
    public BatchStock findById(Long id) {
        Optional<BatchStock> batchStock = repo.findById(id);

        return batchStock.orElseThrow(() -> new NotFoundException("BatchStock not found"));
    }

    /**
     * Salva uma lista de batches na base de dados
     * @author Gabriel
     * @param batches uma lista de batches a ser salva na base de dados
     */
    @Override
    public void saveBatchStockList(List<BatchStock> batches) {
        this.repo.saveAll(batches);
    }

    /**
     * Valida a lista de lotes de um InboundOrder
     * @author Gabriel
     * @param inboundOrder Um objeto de inboundOrder com as informações com uma lista de lotes
     * @return Retorna uma lista de lotes validados
     */
    @Override
    public List<BatchStock> validBatchStockList(InboundOrder inboundOrder) {
        List<BatchStock> batches = new ArrayList<>();
        Section section = inboundOrder.getSection();

        for(BatchStock batch : inboundOrder.getBatches()) {
            Product product = serviceProduct.findById(batch.getProduct().getId());
            batch.setProduct(product);
            batch.setInboundOrder(inboundOrder);

            validateBatchStock(batch, section);
            batches.add(batch);
        }

        updateSectionVolume(batches, section);

        return batches;
    }

    /**
     * Verifica se todos os batches de uma lista existem na base de dados
     * @author Gabriel
     * @param batches a lista ser verificada
     */
    public void verifyIfAllBatchStockExists(List<BatchStock> batches) {
        for(BatchStock batch : batches) {
           this.findById(batch.getId());
        }
    }

    /**
     * Valida se um lote é válido
     * @author Gabriel
     * @param batchStock o lote a ser validado
     * @param section Setor onde o lote será alocado
     */
    private void validateBatchStock(BatchStock batchStock, Section section){
        float minTemperature = section.getMinTemperature();

        if(!validateTemperature(batchStock, minTemperature)){
            throw new InvalidBatchStockException("Current temperature is invalid." +
                    "The temperature must be at least " + minTemperature + "C in this section");
        }

        if(!validateDueTime(batchStock)){
            throw new InvalidBatchStockException("The due time is invalid, must be before the actual date");
        }
    }

    /**
     * Valida se o lote tem uma temperatura válida
     * @author Gabriel
     * @param batchStock o lote a ser validado
     * @param sectionTemperature a temperatura minima permitida no setor
     * @return retorna true caso o setor tenha a temperatura permitida para o setor
     */
    private boolean validateTemperature(BatchStock batchStock, float sectionTemperature){
        return batchStock.getCurrentTemperature() >= sectionTemperature;
    }

    /**
     * Valida se o lote possui uma data de vencimento valida
     * @author Gabriel
     * @param batchStock o lote a ser validado
     * @return Retorna true caso o setor tenha a capacidade suficiente
     */
    private boolean validateDueTime(BatchStock batchStock){
        return batchStock.getDueDate().isAfter(LocalDate.now());
    }

    /**
     * Valida se o setor possui espaço suficiente para os lotes e atualiza
     * @author Gabriel
     * @param batches A lista de batches a serem salvas
     * @param section O setor onde os batches serão alocados
     */
    private void updateSectionVolume(List<BatchStock> batches, Section section){
        float sectionCapacity = section.getCapacity();
        float batchStockListTotalVolume = 0;

        for(BatchStock batch : batches){
            float batchVolume = batch.getVolume();

            if(batch.getId() != null){
                BatchStock batchRepo = this.findById(batch.getId());
                batchVolume -= batchRepo.getVolume();
            }

            batchStockListTotalVolume += batchVolume;
        }

        if(sectionCapacity - batchStockListTotalVolume < 0) {
            throw new InvalidBatchStockException("The batchStock volume is larger than the section capacity");
        }

        section.setCapacity(sectionCapacity - batchStockListTotalVolume);
        this.serviceSection.save(section);
    }

    /**
     * Ordena pela data de vencimento
     *
     * @param id da 'section'
     * @return retorna uma lista de 'batchStocks'
     * @author Ma, Giovanna e Gabriel
     */
    public BatchStockResponseDTO getBatchStockOrderByDueDate(Integer days, Long id) {
        Optional<InboundOrder> inboundOrder = inboundOrderRepo.findById(id);

        // Section section = inboundOrder.get().getSection();

        List<BatchStock> batchStock = inboundOrder.get().getBatches();
        if (batchStock == null) {
            throw new InvalidPurchaseException("Produtos não encontrados");
        }
        // BatchStock batch = new BatchStock();
        for (BatchStock batchStock1 : batchStock) {
            LocalDate today = LocalDate.now();
            long daysBetween = today.until(batchStock1.getDueDate(), ChronoUnit.DAYS);
            if (daysBetween > days) {
                batchStock = batchStock.stream().filter(batchStock2 -> batchStock2.getId() != batchStock1.getId()).collect(Collectors.toList());
            }
        }
        List<BatchStock> dueDate = batchStock.stream().sorted((o1, o2) -> {
            if (o2.getDueDate().isEqual(o1.getDueDate())) {
                return 0;
            }
            if (o2.getDueDate().isBefore(o1.getDueDate())) {
                return -1;
            }
            return 1;
        }).collect(Collectors.toList());

        if (dueDate.isEmpty()) {
            throw new InvalidPurchaseException("Produtos não encontrados");
        }

        return new BatchStockResponseDTO(dueDate);
    }


    private Integer sortedByDueDateDesc(BatchStock a1, BatchStock a2) {
        if (a2.getDueDate().isEqual(a1.getDueDate())) {
            return 0;
        }
        if (a2.getDueDate().isBefore(a1.getDueDate())) {
            return -1;
        }
        return 1;
    }
    private Integer sortedByDueDateAsc(BatchStock a1, BatchStock a2) {
        if (a2.getDueDate().isEqual(a1.getDueDate())) {
            return 0;
        }
        if (a2.getDueDate().isBefore(a1.getDueDate())) {
            return 1;
        }
        return -1;
    }

    /**
     * De/Para da sigla de categoria para categoria de produto
     * @author Ma, Gabriel e Giovanna
     * @param days, category e orderBy
     * @return Uma lista de 'batchStocks' ordenados pelo número de dias até o vencimento, categoria e ordem(crescente ou decrescente)
     */
    public BatchStockResponseDTO getBatchStockOrderByDueDateAndCategory(Integer days, String category, OrderBy orderBy) {
        Category filterCategory = serviceProduct.filterCategory(category);
        List<BatchStock> batchStock = repo.getBatchStocksByCategory(filterCategory);
        for (BatchStock batchStock1 : batchStock) {
            LocalDate today = LocalDate.now();
            long daysBetween = today.until(batchStock1.getDueDate(), ChronoUnit.DAYS);
            if (daysBetween > days) {
                batchStock = batchStock.stream().filter(batchStock2 -> batchStock2.getId() != batchStock1.getId()).collect(Collectors.toList());
            }
        }
        if (batchStock.isEmpty()) {
            throw new InvalidPurchaseException("Produtos não encontrados");
        }
        if (orderBy == null || orderBy == OrderBy.DESC) {
            return new BatchStockResponseDTO(batchStock.stream().sorted((a1, a2) -> this.sortedByDueDateDesc(a1, a2)).collect(Collectors.toList()));
        }
            return new BatchStockResponseDTO(batchStock.stream().sorted((a1, a2) -> this.sortedByDueDateAsc(a1, a2)).collect(Collectors.toList()));
    }

}

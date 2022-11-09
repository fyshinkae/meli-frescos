package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.exception.InvalidBatchStockException;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
import com.example.mercadofrescos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

    private final IBatchStockRepo repo;
    private final IProductService serviceProduct;

    @Override
    public List<BatchStock> convertToListBatchStock(List<BatchStockDTO> batchesDTO, InboundOrder inboundOrder) {
    public BatchStockDTO save(InsertBatchRequestDTO request) {
        return null;
    }

    @Override
    public BatchStockDTO update(InsertBatchRequestDTO request) {
        return null;
    }

    /**
     * Valida a lista de lotes de um InboundOrder
     * @author Gabriel
     * @param inboundOrder Um objeto de inboundOrder com as informacoes com uma lista de lotes
     * @return Retorna uma lista de lotes validados
     */
    @Override
    public List<BatchStock> validBatchStockList(InboundOrder inboundOrder) {
        List<BatchStock> batches = new ArrayList<>();
        Section section = inboundOrder.getSection();

        float sectionCapacity = section.getCapacity();
        float batchStockListTotalVolume = 0;

        for(BatchStock batch : inboundOrder.getBatches()) {
            Product product = serviceProduct.findById(batch.getProduct().getId());

            batch.setProduct(product);
            batch.setInboundOrder(inboundOrder);

            validateBatchStock(batch, section);
            batchStockListTotalVolume += batch.getVolume();

            batches.add(batch);
        }

        if(!validateBatchStockVolume(batchStockListTotalVolume, sectionCapacity)){
            throw new InvalidBatchStockException("The section does have capacity");
        }

        return batches;
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
     * @return retorna true caso o setor tenha a temperatura permitida para os etor
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
     * Valida se o setor possui espaco suficiente para os lotes
     * @author Gabriel
     * @param batchStockTotalVolume Somatório das capacidades dos lotes
     * @param sectionCapacity Capacidade restante do setor
     * @return Retorna true caso o setor tenha capacidade suficiente
     */
    private boolean validateBatchStockVolume(float batchStockTotalVolume, float sectionCapacity){
        if(sectionCapacity - batchStockTotalVolume < 0){
            return false;
        }

        return true;
    }

}

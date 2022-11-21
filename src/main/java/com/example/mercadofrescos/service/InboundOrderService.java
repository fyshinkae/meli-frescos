package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.inboundOrder.InboundOrderResponseDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.repository.IInboundOrderRepo;
import com.example.mercadofrescos.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {

    private final IInboundOrderRepo repoOrder;
    private final ISectionService serviceSection;
    private final IBatchStockService serviceBatchStock;
    private final IWarehouseService serviceWarehouse;

    /**
     * Cria uma noa InboundOrder
     * @author Felipe, Gabriel, Matheus, Theus
     * @param inboundOrder novo InboundOrder a ser criado
     * @param warehouseId id do warehouse onde será armazenado
     * @return uma lista de batchStock criado
     */
    @Override
    public InboundOrderResponseDTO save(InboundOrder inboundOrder, Long warehouseId) {
        serviceWarehouse.findById(warehouseId);
        Section section = serviceSection.findById(inboundOrder.getSection().getId());
        serviceSection.findSectionByWarehouseId(warehouseId, inboundOrder.getSection().getId());

        inboundOrder.setSection(section);

        List<BatchStock> batches = serviceBatchStock.validBatchStockList(inboundOrder);
        inboundOrder.setBatches(batches);

        InboundOrder response = repoOrder.save(inboundOrder);
        serviceBatchStock.saveBatchStockList(batches);

        return new InboundOrderResponseDTO(response);
    }

    /**
     * Busca um InboundOrder e lança uma exceção caso não encontre
     * @author Theus
     * @param id do InboundOrder
     * @return um objeto do modelo InboundOrder
     */
    @Override
    public InboundOrder findById(Long id) {
        Optional<InboundOrder> inboundOrder = this.repoOrder.findById(id);

        return inboundOrder.orElseThrow(() -> new NotFoundException("Inbound order not found"));
    }

    /**
     * Atualiza os batchStocks
     * @author Gabriel
     * @param inboundOrder parâmetro do usuário contendo informações sobre o inboundOrder e uma lista de batchStocks
     * @return  a lista de batchStocks atualizada
     */
    @Override
    public InboundOrderResponseDTO update(InboundOrder inboundOrder, Long warehouseId) {
        serviceBatchStock.verifyIfAllBatchStockExists(inboundOrder.getBatches());

        return this.save(inboundOrder, warehouseId);
    }
}

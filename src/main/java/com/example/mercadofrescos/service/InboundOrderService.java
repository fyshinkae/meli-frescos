package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.inboundOrder.InboundOrderResponseDTO;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.repository.IInboundOrderRepo;
import com.example.mercadofrescos.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

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
     * @param request novo InboundOrder a ser criado
     * @param warehouseId id do warehouse onde será armazenado
     * @return uma lista de batchStock criado
     */
    @Override
    public InboundOrderResponseDTO save(InboundOrder request, Long warehouseId) {
        serviceWarehouse.findById(warehouseId);
        Section section = serviceSection.findById(request.getSection().getId());
        serviceSection.findSectionByWarehouseId(warehouseId, request.getSection().getId());

        request.setSection(section);

        List<BatchStock> batches = serviceBatchStock.validBatchStockList(request);
        request.setBatches(batches);

        InboundOrder response = repoOrder.save(request);
        serviceBatchStock.saveBatchStockList(batches);
        return new InboundOrderResponseDTO(response);
    }

    /**
     * Atualiza os batchStocks
     * @author Gabriel
     * @param request parâmetro do usuário contendo informações sobre o inboundOrder e uma lista de batchStocks
     * @return  a lista de batchStocks atualizada
     */
    @Override
    public InboundOrderResponseDTO update(InboundOrder request, Long warehouseId) {
        serviceBatchStock.verifyIfAllBatchStockExists(request.getBatches());
        return this.save(request, warehouseId);
    }
}

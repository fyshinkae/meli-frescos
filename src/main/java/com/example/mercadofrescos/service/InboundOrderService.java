package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InboundOrderResponseDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
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
     * Atualiza BatchStocks de um Section
     * @author Felipe, Gabriel, Matheus, Theus
     * @param request parametro do usuário contendo informacoes sobre o inboundorder e uma lista de batchstocks
     * @return a lista de batchstocks salva
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
        return new InboundOrderResponseDTO(response);
    }

    @Override
    public BatchStockDTO update(InsertBatchRequestDTO request) {
        return null;
    }
}

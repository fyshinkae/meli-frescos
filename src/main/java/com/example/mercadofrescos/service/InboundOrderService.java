package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InboundOrderResponseDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.repository.IInboundOrderRepo;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
import com.example.mercadofrescos.service.interfaces.IInboundOrderService;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.ISectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {

    private final IInboundOrderRepo repoOrder;
    private final ISectionService serviceSection;
   private final IBatchStockService serviceBatchStock;


    /**
     * Atualiza BatchStocks de um Section
     * @author Felipe, Gabriel, Matheus
     * @param request parametro do usuário contendo informacoes sobre o inboundorder e uma lista de batchstocks
     * @return a lista de batchstocks salva
     */

    // todo: buscar o wirehouse e comparar se ele é o mesmo da section
    @Override
    public InboundOrderResponseDTO save(InboundOrder request) {
        Section section = serviceSection.findById(request.getSection().getId());
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

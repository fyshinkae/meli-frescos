package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockDTO;
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
    @Override
    public List<BatchStockDTO> save(InsertBatchRequestDTO request) {
        Section section = serviceSection.findById(request.getInboundOrder().getSectionCode());

        InboundOrder inboundOrder = new InboundOrder();
        inboundOrder.setOrderDate(request.getInboundOrder().getOrderDate());
        inboundOrder.setSection(section);

        List<BatchStock> batches = serviceBatchStock.convertToValidBatchStockList(
                request.getInboundOrder().getBatchStock(), inboundOrder);

        inboundOrder.setBatches(batches);

        InboundOrder response = repoOrder.save(inboundOrder);
        return BatchStockDTO.convertToDTOList(response.getBatches());
    }

    @Override
    public BatchStockDTO update(InsertBatchRequestDTO request) {
        return null;
    }
}

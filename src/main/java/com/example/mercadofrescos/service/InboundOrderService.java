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
    private final IProductService serviceProduct;
    private final IBatchStockService repoBatch;


    @Override
    public List<BatchStockDTO> save(InsertBatchRequestDTO request) {
        InboundOrder inboundOrder = new InboundOrder();
        Section section = serviceSection.findById(request.getInboundOrder().getSectionCode());
        inboundOrder.setOrderDate(request.getInboundOrder().getOrderDate());
        inboundOrder.setSection(section);

        List<BatchStock> batches = new ArrayList<>();

        for(BatchStockDTO batch : request.getInboundOrder().getBatchStock()) {
            Product product = serviceProduct.findById(batch.getProductId());
            batches.add(new BatchStock(
                    batch.getBatchNumber(),
                    product,
                    inboundOrder,
                    batch.getCurrentTemperature(),
                    batch.getManufacturingDate(),
                    batch.getManufacturingTime(),
                    batch.getDueTime(),
                    batch.getProductQuantity(),
                    batch.getVolume()
            ));
        }

        inboundOrder.setBatches(batches);
        repoOrder.save(inboundOrder);
        return request.getInboundOrder().getBatchStock();
    }

    @Override
    public BatchStockDTO update(InsertBatchRequestDTO request) {
        return null;
    }
}

package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.model.enums.Category;

import java.util.HashSet;
import java.util.Set;

public class SectionMock {
    public static Section SectionTest () {
        Section section = new Section();
        Set<InboundOrder> inboundOrder;
        inboundOrder = new HashSet<InboundOrder>();
        
        section.setId(1L);
        section.setWarehouse(WarehouseMock.warehouseTest());
        section.setCategory(Category.FRESH);
        section.setCapacity(1000F);
        section.setMinTemperature(10F);
        section.setInboundOrders(inboundOrder);
        return section;
    }
}

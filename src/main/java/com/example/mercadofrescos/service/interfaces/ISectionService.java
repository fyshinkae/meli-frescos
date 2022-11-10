package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.Section;

public interface ISectionService {
    Section findById(Long id);
    Section save(Section section);
    void findSectionByWarehouseId(Long wirehouseId, Long sectionId);
}

package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.repository.ISectionRepo;
import com.example.mercadofrescos.service.interfaces.ISectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectionService implements ISectionService {

    private final ISectionRepo repo;

    /**
     * Busca uma Section ou lança um erro caso não encontre
     * @author Theus
     * @param id da Section
     */
    @Override
    public Section findById(Long id) {
        Optional<Section> section = repo.findById(id);

        return section.orElseThrow(() -> new NotFoundException("Section not found"));
    }

    /**
     * Busca uma Section relacionada a um Warehouse, e lança um erro caso não encontre
     * @author Theus
     * @param warehouseId id da Warehouse
     * @param sectionId id da Section
     */
    @Override
    public void findSectionByWarehouseId(Long warehouseId, Long sectionId) {
        Optional<Long> section = repo.findSectionByWarehouseId(warehouseId, sectionId);

        if (section.isEmpty()) throw new NotFoundException("The indicated section does not exist in the warehouse");
    }
}

package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionDTO {

    private Long sectionCode;

    private Long warehouseCode;

    /**
     * Converte um objeto do tipo Section para um DTO
     * @author Gabriel
     * @param section objeto a ser convertido
     */
    public SectionDTO(Section section){
        Warehouse warehouse = section.getWarehouse();
        this.sectionCode = section.getId();

        if(warehouse != null){
            this.warehouseCode = section.getWarehouse().getId();
        }
    }

}

package com.example.mercadofrescos.dto.product;

import com.example.mercadofrescos.dto.section.SectionDTO;
import com.example.mercadofrescos.dto.batchStock.BatchStockAgentResponseDTO;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProductAgentResponseDTO {

    private SectionDTO section;

    private Long productId;

    private List<BatchStockAgentResponseDTO> batchStock;

    /** Cria um objeto esperado de resposta de produto por um agente
     * @author Gabriel
     * @param product produto a ser retornado
     * @param section seção do produto a ser retornado
     * @param batchStocks uma lista de BatchStock do produto presente na seção
     */
    public ProductAgentResponseDTO(Product product, Section section, Set<BatchStock> batchStocks){
        this.productId = product.getId();
        this.section = new SectionDTO(section);

        this.batchStock = new ArrayList<>();
        for(BatchStock batch: batchStocks){
            this.batchStock.add(new BatchStockAgentResponseDTO(batch));
        }
    }

}

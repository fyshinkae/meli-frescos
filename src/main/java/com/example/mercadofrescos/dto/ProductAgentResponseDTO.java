package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductAgentResponseDTO {
    private SectionDTO section;
    private Long productId;
    private List<BatchStockAgentResponseDTO> batchStock;

    /** Cria um objeto esperado de resposta de produto por um agente
     * @author Gabriel
     * @param product produto a ser retornado
     * @param section secao do produto a ser retornado
     * @param batchStocks uma lista de batchstock do produto presente na secao
     */
    public ProductAgentResponseDTO(Product product, Section section, List <BatchStock> batchStocks){
        this.productId = getProductId();
        this.section = new SectionDTO(section);

        this.batchStock = new ArrayList<>();
        for(BatchStock batch: batchStocks){
            this.batchStock.add(new BatchStockAgentResponseDTO(batch));
        }
    }

}

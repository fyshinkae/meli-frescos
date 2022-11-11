package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.*;
import com.example.mercadofrescos.exception.InvalidQueryParamException;
import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.exception.CategoryNotFoundException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.exception.ProductsListNotFoundException;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.repository.IProductRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepo repo;

    /** Pega a lista de todos os produtos
     * @author Gabriel
     * @return Retorna a lista de todos os produtos
     */
    @Override
    public List<ProductResponseDTO> findAllProducts() {
        List<Product> products = repo.findAll();

        List<ProductResponseDTO> response = new ArrayList<>();

        if(products.isEmpty()){
            throw new NotFoundException("Products not found");
        }

        products.stream()
                .forEach(product ->  response.add(new ProductResponseDTO(product)));

        return response;
    }

    // todo: FAZER JAVADOC
    @Override
    public Product saveProduct(Product newProduct) {
        return repo.save(newProduct);
    }

    // todo: FAZER JAVADOC
    @Override
    public Product updatedProduct(Product product) {
        return repo.save(product);
    }

    /**
     * Busca um Product ou lança um erro caso não encontre
     * @author Theus
     * @param id do Product
     */
    @Override
    public Product findById(Long id) {
        Optional<Product> product = repo.findById(id);

        return product.orElseThrow(() -> new NotFoundException("Product not found"));
    }

    /**
     * Busca os produtos com o filtro de categoria
     * @author Giovana, Ma e Felipe
     * @param category categoria do produto
     * @return lista de produtos filtrado
     */
    @Override
    public List<ProductDTO> findByCategory(String category) {
        Category filterCategory = filterCategory(category);
        List<Product> productByCategory = repo.findAllByCategory(filterCategory);

        List<ProductDTO> products = productByCategory.stream()
                .map(product ->
                        new ProductDTO(
                                product.getId(), product.getName(), product.getPrice(), product.getCategory())
                    ).collect(Collectors.toList());

        if(products.isEmpty()) {
            throw new ProductsListNotFoundException("Products not found");
        }

        return products;
    }

    /**
     * Busca um produto para o representante
     * @author Theus
     * @param id do produto
     * @return Um ProductAgentResponseDTO com os dados da Section e Warehouse do produto
     */
    @Override
    public ProductAgentResponseDTO findByIdForAgent(Long id) {
        Optional<Product> product = repo.findById(id);

        if (product.isEmpty()) throw new NotFoundException("Product not found");

        Set<BatchStock> batches = product.get().getBatches();
        Section sectionProduct = new Section();

        if(!batches.isEmpty()) {
            sectionProduct = batches.iterator().next().getInboundOrder().getSection();
        }

        return new ProductAgentResponseDTO(product.get(), sectionProduct, batches);
    }

    /**
     * Ordena os lotes do produto
     * @author Theus
     * @param product um ProductAgentResponseDTO
     * @param typeOrder qual a ordenação aplicará
     * @return Um ProductAgentResponseDTO com os lotes ordenados
     */
    public ProductAgentResponseDTO orderProductForAgent(ProductAgentResponseDTO product, String typeOrder) {
        switch (typeOrder.toUpperCase()) {
            case "L":
                return this.sortByBatch(product);
            case "Q":
                return this.sortByQuantity(product);
            case "V":
                return this.sortByDueDate(product);
            default:
                throw new InvalidQueryParamException("Invalid sort parameter");
        }
    }

    /**
     * Ordena os lotes do produto por BatchNumber
     * @author Theus
     * @param product um ProductAgentResponseDTO
     * @return Um ProductAgentResponseDTO com os lotes ordenados
     */
    private ProductAgentResponseDTO sortByBatch(ProductAgentResponseDTO product) {
        List<BatchStockAgentResponseDTO> batchesOrdered = product.getBatchStock().stream()
                .sorted(Comparator.comparing(BatchStockAgentResponseDTO::getBatchNumber))
                .collect(Collectors.toList());

        product.setBatchStock(batchesOrdered);

        return product;
    }

    /**
     * Ordena os lotes do produto por quantidade de produtos no lote
     * @author Theus
     * @param product um ProductAgentResponseDTO
     * @return Um ProductAgentResponseDTO com os lotes ordenados
     */
    private ProductAgentResponseDTO sortByQuantity(ProductAgentResponseDTO product) {
        List<BatchStockAgentResponseDTO> batchesOrdered = product.getBatchStock().stream()
                .sorted(Comparator.comparing(BatchStockAgentResponseDTO::getCurrentQuantity))
                .collect(Collectors.toList());
    
        product.setBatchStock(batchesOrdered);

        return product;
    }

    /**
     * Ordena os lotes do produto por data de validade do produto
     * @author Theus
     * @param product um ProductAgentResponseDTO
     * @return Um ProductAgentResponseDTO com os lotes ordenados
     */
    private ProductAgentResponseDTO sortByDueDate(ProductAgentResponseDTO product) {
        List<BatchStockAgentResponseDTO> batchesOrdered = product.getBatchStock().stream()
                .sorted(Comparator.comparing(BatchStockAgentResponseDTO::getDueDate))
                .collect(Collectors.toList());

        product.setBatchStock(batchesOrdered);

        return product;
    }

    // todo: FAZER JAVADOC
    private Category filterCategory(String word) {
        switch (word) {
            case "FS":
                return Category.FRESH;
            case "RF":
                return Category.REFRIGERATED;
            case "FR":
                return Category.FROZEN;
            default:
                throw new InvalidQueryParamException("No products with this category were found");
        }
    }

}

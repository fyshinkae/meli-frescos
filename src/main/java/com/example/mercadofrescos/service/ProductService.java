package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.repository.IProductRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        return product.orElseThrow(() -> new NotFoundException("Section not found"));
    }
}

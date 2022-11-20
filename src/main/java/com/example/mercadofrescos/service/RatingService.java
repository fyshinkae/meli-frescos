package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.rating.RatingByProductDTO;
import com.example.mercadofrescos.dto.rating.RatingByUserDTO;
import com.example.mercadofrescos.dto.rating.RatingDTO;
import com.example.mercadofrescos.dto.rating.RatingProductDTO;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Rating;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.repository.IRatingRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IRatingService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService {

    private final IRatingRepo repo;
    private final IUserService userService;
    private final IProductService productService;

    /**
     * Cria um rating no banco de dados
     * @author Gabriel
     * @param rating Rating a ser salvo no banco de dados
     * @return Retorna o RatingDTO com os dados salvos no banco
     */
    @Override
    public RatingDTO createRating(Rating rating) {
        User customer =  this.userService.findById(rating.getId().getCustomerId());
        rating.setCustomer(customer);

        Product product = this.productService.findById(rating.getId().getProductId());
        rating.setProduct(product);

        rating.setCreatedAt(LocalDateTime.now());

        return RatingDTO.convert(this.repo.save(rating));
    }

    /**
     * Obtém a lista de avaliações associadas a um customerId
     * @author Gabriel
     * @param customerId Identificação do comprador que queremos saber as avaliações
     * @return Todas as avaliações de um determinado customerId
     */
    @Override
    public RatingByUserDTO getRatingByUser(Long customerId) {
        User user = this.userService.findById(customerId);
        return RatingByUserDTO.convert(this.repo.findAllByCustomerId(user.getId()));
    }

    /**
     * Obtém a lista de avaliações associadas a um productId
     * @author Gabriel
     * @param productId Identificação do produto que queremos saber as avaliações
     * @return Todas as avaliações de um determinado productId;
     */
    @Override
    public RatingByProductDTO getRatingByProduct(Long productId) {
        Product product =  this.productService.findById(productId);
        return RatingByProductDTO.convert(this.repo.findAllByProductId(product.getId()));
    }
}

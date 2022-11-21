package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.rating.*;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Rating;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.enums.Reputation;
import com.example.mercadofrescos.repository.IRatingRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IRatingService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Rating> ratings = this.repo.findAllByCustomerId(user.getId());

        if(ratings.isEmpty()){
            throw new NotFoundException("Ratings not found");
        }

        return RatingByUserDTO.convert(ratings);
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

        List<Rating> ratings = this.repo.findAllByProductId(product.getId());

        if(ratings.isEmpty()){
            throw new NotFoundException("Ratings not found");
        }

        return RatingByProductDTO.convert(ratings);
    }

    /**
     * Obtém a lista de avaliações associadas com informações de vendedores
     * @author Gabriel
     * @param reputation filtro opcional de reputação
     * @return Todas as avaliações com informações sobre vendedores;
     */
    @Override
    public List<RatingBySellerDTO> getRatingBySeller(Reputation reputation) {
        List<Rating> ratings = this.repo.findAll();
        HashMap<Long, List<Rating>> ratingsBySeller = getHashMapRatingsBySeller(ratings);

        List<RatingBySellerDTO> response = new ArrayList<>();

        for(Long sellerId : ratingsBySeller.keySet()){
            Reputation sellerReputation = this.getReputation(ratingsBySeller.get(sellerId));
            if(sellerReputation == reputation || reputation == null) {
                response.add(new RatingBySellerDTO(ratingsBySeller.get(sellerId), sellerId, sellerReputation));
            }
        }

        if(response.isEmpty()){
            throw new NotFoundException("Ratings not found");
        }

        return response;
    }

    /**
     * Obtém a avaliação de um usuário de um produto específico
     * @author Gabriel
     * @param customerId Id do comprador
     * @param productId Id do produto
     * @return a avaliação de um usuário de um produto específico
     */
    @Override
    public RatingByUserDTO getRatingByUserAndProduct(Long customerId, Long productId) {
        RatingByUserDTO userRatings = this.getRatingByUser(customerId);
        List<RatingProductDTO> ratings = userRatings.getRatings();
        List<RatingProductDTO> filteredRatings = this.getFilteredRatingProductDTO(ratings, productId);
        userRatings.setRatings(filteredRatings);
        return userRatings;
    }

    /**
     * Obtém uma lista de todos as avaliações de todos os usuários
     * @author Gabriel
     * @return uma lista de todos as avaliações de todos os usuários
     */
    @Override
    public List<RatingByUserDTO> getRatingByUsers() {
        List<Rating> ratings = this.repo.findAll();
        HashMap<Long, List<Rating>> ratingsByUser = this.getHashMapRatingsByCustomer(ratings);

        if(ratings.isEmpty()){
            throw new NotFoundException("Ratings not found");
        }

        List<RatingByUserDTO> response = new ArrayList<>();
        for(Long customerId : ratingsByUser.keySet()){
            response.add(RatingByUserDTO.convert(ratingsByUser.get(customerId)));
        }

        return response;
    }

    /**
     * Filtra a lista de RatingProductDTO por productId
     * @author Gabriel
     * @param ratings uma lista de DTOs
     * @param productId Id do produto
     * @return Retorna uma lista com um único produto
     */
    private List<RatingProductDTO> getFilteredRatingProductDTO(List<RatingProductDTO> ratings, Long productId){
        List<RatingProductDTO> filteredRatings = ratings.stream()
                .filter(rating -> rating.getProductId() == productId)
                .collect(Collectors.toList());

        if(filteredRatings.isEmpty()){
            throw new NotFoundException("Ratings not found");
        }
        return filteredRatings;
    }

    /**
     *Obtém a reputação a partir de uma lista de avaliações
     * @author Gabriel
     * @param ratings uma lista de avaliações
     * @return uma reputação
     */
    private Reputation getReputation(List <Rating> ratings){
        int numberOfRatings = ratings.size();
        BigDecimal averageRating = this.getAverageRating(ratings);

        if(this.isReputationGreen(numberOfRatings,averageRating)){
            return Reputation.GREEN;
        }

        if(this.isReputationYellow(numberOfRatings, averageRating)){
            return Reputation.YELLOW;
        }

        return Reputation.RED;
    }

    /**
     * Verifica se a reputação é Green a partir de informações sobre avaliações
     * @param numberOfRatings número de avaliações
     * @param averageRating média de avaliações
     * @return true se for green ou false caso não seja
     */
    private Boolean isReputationGreen(int numberOfRatings, BigDecimal averageRating){
        if(numberOfRatings >= Reputation.GREEN.getMinimumNumberOfRatings() &&
                averageRating.doubleValue() >= Reputation.GREEN.getMinimumAverageRating().doubleValue()){
            return true;
        }

        return false;
    }

    /**
     * Verifica se a reputação é Yellow a partir de informações sobre avaliações
     * @param numberOfRatings número de avaliações
     * @param averageRating média de avaliações
     * @return true se for yellow ou false caso não seja
     */
    private Boolean isReputationYellow(int numberOfRatings, BigDecimal averageRating){
        if(numberOfRatings >= Reputation.YELLOW.getMinimumNumberOfRatings() &&
                averageRating.doubleValue() >= Reputation.YELLOW.getMinimumAverageRating().doubleValue()){
            return true;
        }

        return false;
    }

    /**
     * Obtém a média de avaliação a partir de uma lista de avaliações
     * @author Gabriel
     * @param ratings uma lista de avaliações
     * @return a média de avaliação obtida a partir da lista de avaliacões
     */
    private BigDecimal getAverageRating(List <Rating> ratings){
        BigDecimal sum = new BigDecimal(0);

        for(Rating rating : ratings){
            sum = sum.add(rating.getRating());
        }

        return sum.divide(new BigDecimal(ratings.size()), 2, RoundingMode.HALF_UP);
    }

    /**
     * Obtém um HashMap onde a chave é o id do vendedor e o valor é a lista de avaliações que o vendedor possui
     * @author Gabriel
     * @param ratings Lista com todas as avaliações
     * @return um HashMap
     */
    private HashMap<Long, List<Rating>> getHashMapRatingsBySeller(List<Rating> ratings){
        HashMap<Long, List<Rating>> ratingsBySeller = new HashMap<>();

        for(Rating rating : ratings){
            Long sellerId = rating.getProduct().getSeller().getId();
            if(ratingsBySeller.containsKey(sellerId)){
                ratingsBySeller.get(sellerId).add(rating);
            } else {
                List<Rating> sellerRatings = new ArrayList<>();
                sellerRatings.add(rating);
                ratingsBySeller.put(sellerId, sellerRatings);
            }
        }

        return ratingsBySeller;
    }

    /**
     * Obtém um HashMap onde a chave é o id do comprador e o valor é a lista de avaliações
     * @author Gabriel
     * @param ratings Lista com todas as avaliações
     * @return um HashMap
     */
    private HashMap<Long, List<Rating>> getHashMapRatingsByCustomer(List<Rating> ratings){
        HashMap<Long, List<Rating>> ratingsByCustomer = new HashMap<>();

        for(Rating rating : ratings){
            Long customerId = rating.getId().getCustomerId();
            if(ratingsByCustomer.containsKey(customerId)){
                ratingsByCustomer.get(customerId).add(rating);
            } else {
                List<Rating> customerRatings = new ArrayList<>();
                customerRatings.add(rating);
                ratingsByCustomer.put(customerId, customerRatings);
            }
        }

        return ratingsByCustomer;
    }


}

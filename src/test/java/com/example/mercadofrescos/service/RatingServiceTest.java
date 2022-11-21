package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.rating.RatingByProductDTO;
import com.example.mercadofrescos.dto.rating.RatingBySellerDTO;
import com.example.mercadofrescos.dto.rating.RatingByUserDTO;
import com.example.mercadofrescos.dto.rating.RatingDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.ProductMock;
import com.example.mercadofrescos.mocks.RatingMock;
import com.example.mercadofrescos.mocks.UserMock;
import com.example.mercadofrescos.model.CustomerProductId;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Rating;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.repository.IRatingRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private IRatingRepo ratingRepo;

    @Mock
    private IUserService userService;

    @Mock
    private IProductService productService;

    @InjectMocks
    private RatingService ratingService;

    private Rating ratingMock;

    @BeforeEach
    public void setup(){
        this.ratingMock = RatingMock.generateRatingMock();
    }

    @Test
    @DisplayName("getRatingByUser throws NotFoundException when customerId is invalid")
    public void getRatingByUser_throwsNotFoundException_whenCustomerIdIsInvalid(){
        final Long invalidCustomerId = 999l;

        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> ratingService.getRatingByUser(invalidCustomerId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getRatingByUser throws NotFoundException when customerId don't have ratings")
    public void getRatingByUser_throwsNotFoundException_whenRatingListIsEmpty(){
        User userMock = UserMock.userTest();

        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(userMock);

        Mockito.when(this.ratingRepo.findAllByCustomerId(ArgumentMatchers.anyLong()))
                        .thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> ratingService.getRatingByUser(userMock.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getRatingByUser return RatingByUserDTO when customerId is valid")
    public void getRatingByUser_returnRatingByUserDTO_whenCustomerIdIsValid(){
        User userMock = UserMock.userTest();

        List<Rating> ratings = new ArrayList<>();
        ratings.add(this.ratingMock);

        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(userMock);

        Mockito.when(this.ratingRepo.findAllByCustomerId(ArgumentMatchers.anyLong()))
                .thenReturn(ratings);

        RatingByUserDTO response = this.ratingService.getRatingByUser(userMock.getId());

        assertThat(response).isNotNull();
        assertThat(response.getCustomerId()).isEqualTo(userMock.getId());
        assertThat(response.getRatings().size()).isEqualTo(ratings.size());
    }

    @Test
    @DisplayName("getRatingByProduct throws NotFoundException when ProductId is invalid")
    public void getRatingByProduct_throwsNotFoundException_whenProductIdIsInvalid(){
        final Long invalidProductId = 999l;

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> ratingService.getRatingByProduct(invalidProductId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getRatingByProduct throws NotFoundException when rating list is empty")
    public void getRatingByProduct_throwsNotFoundException_whenRatingListIsEmpty(){
        Product product = ProductMock.getProductFresh();

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(product);

        Mockito.when(this.ratingRepo.findAllByProductId(ArgumentMatchers.anyLong()))
                        .thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> ratingService.getRatingByProduct(product.getId()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getRatingByProduct return RatingByProductDTO when productIdIsValid")
    public void getRatingByProduct_returnRatingByProductDTO_whenProductIdIsValid(){
        Product product = ProductMock.getProductFresh();

        List<Rating> ratings = new ArrayList<>();
        this.ratingMock.setProduct(product);
        ratings.add(this.ratingMock);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(product);

        Mockito.when(this.ratingRepo.findAllByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(ratings);

        RatingByProductDTO response = ratingService.getRatingByProduct(product.getId());

        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(product.getId());
        assertThat(response.getAverageRating()).isNotNegative();
        assertThat(response.getRatings().size()).isEqualTo(ratings.size());
    }

    @Test
    @DisplayName("createRating throws NotFoundException when CustomerId is invalid")
    public void createRating_throwsNotFoundException_whenCustomerIdIsInvalid(){
        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> this.ratingService.createRating(this.ratingMock))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("createRating throws NotFoundException when ProductId is invalid")
    public void createRating_throwsNotFoundException_whenProductIdIsInvalid(){
        User userMock = UserMock.userTest();

        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(userMock);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                        .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> this.ratingService.createRating(this.ratingMock))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("createRating return RatingDTO when request is valid")
    public void createRating_returnRatingDTO_whenRequestIsValid(){
        User userMock = UserMock.userTest();
        Product productMock = ProductMock.getProductFresh();

        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(userMock);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(productMock);

        Mockito.when(this.ratingRepo.save(ArgumentMatchers.any()))
                .thenReturn(this.ratingMock);

        RatingDTO response = this.ratingService.createRating(this.ratingMock);

        assertThat(response).isNotNull();
        assertThat(response.getCustomerId()).isEqualTo(this.ratingMock.getId().getCustomerId());
        assertThat(response.getRating()).isEqualTo(this.ratingMock.getRating());
        assertThat(response.getProductId()).isEqualTo(productMock.getId());
    }

    @Test
    @DisplayName("getRatingBySeller throws NotFoundException when response is empty")
    public void getRatingBySeller_throwsNotFoundException_whenResponseIsEmpty(){
        Mockito.when(this.ratingRepo.findAll())
                .thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> this.ratingService.getRatingBySeller(null))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getRatingBySellers return RatingBySeller list when response is valid")
    public void getRatingBySeller_returnRatingBySeller_whenResponseIsValid(){
        List<Rating> ratings = new ArrayList<>();
        User seller = UserMock.userTest();
        Product product = ProductMock.getProductFresh();
        product.setSeller(seller);

        this.ratingMock.setProduct(product);
        ratings.add(this.ratingMock);

        Mockito.when(this.ratingRepo.findAll())
                .thenReturn(ratings);

        List<RatingBySellerDTO> response = this.ratingService.getRatingBySeller(null);

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("getRatingByUsers throws NotFoundException when ratings is empty")
    public void getRatingByUsers_throwsNotFoundException_whenRatingsIsEmpty(){
        Mockito.when(this.ratingRepo.findAll())
                .thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> this.ratingService.getRatingByUsers())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getRatingByUsers return a list of RatingByUser")
    public void getRatingByUsers_returnsRatingByUsersDTOList_whenResponseIsValid(){
        List<Rating> ratings = new ArrayList<>();

        User seller = UserMock.userTest();
        seller.setId(1L);
        Product product = ProductMock.getProductFresh();
        product.setSeller(seller);

        this.ratingMock.setProduct(product);
        ratings.add(this.ratingMock);

        Mockito.when(this.ratingRepo.findAll())
                .thenReturn(ratings);

        Rating ratingMock2 = RatingMock.generateRatingMock();
        ratingMock2.setId(new CustomerProductId(2L,2L));
        User seller2 = UserMock.userTest();
        seller2.setId(2L);
        Product product2 = ProductMock.getProductFresh();
        product2.setId(2L);
        product2.setSeller(seller2);
        ratingMock2.setProduct(product2);

        ratings.add(ratingMock2);

        List<RatingByUserDTO> response = this.ratingService.getRatingByUsers();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getRatingByUserAndProduct return ratingByUserDTO")
    public void getRatingByUserAndProduct_returnRatingByUserDTO_whenResponseIsValid(){
        User userMock = UserMock.userTest();
        List<Rating> ratings = new ArrayList<>();

        Product productMock = ProductMock.getProductFresh();
        this.ratingMock.setProduct(productMock);
        ratings.add(this.ratingMock);

        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(userMock);

        Mockito.when(this.ratingRepo.findAllByCustomerId(ArgumentMatchers.anyLong()))
                .thenReturn(ratings);

        RatingByUserDTO response = this.ratingService.getRatingByUserAndProduct(userMock.getId(), productMock.getId());

        assertThat(response).isNotNull();
        assertThat(response.getRatings().size()).isEqualTo(ratings.size());
    }

    @Test
    @DisplayName("getRatingByUserAndProduct throws NotFoundException when ProductId is invalid")
    public void getRatingByUserAndProduct_throwsNotFoundException_whenProductIdIsInvalid(){
        User userMock = UserMock.userTest();
        List<Rating> ratings = new ArrayList<>();
        Long invalidProductId = 999L;

        ratings.add(this.ratingMock);

        Mockito.when(this.userService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(userMock);

        Mockito.when(this.ratingRepo.findAllByCustomerId(ArgumentMatchers.anyLong()))
                .thenReturn(ratings);

        assertThatThrownBy(() ->
                this.ratingService.getRatingByUserAndProduct(
                        userMock.getId(), invalidProductId))
                .isInstanceOf(NotFoundException.class);
    }


}

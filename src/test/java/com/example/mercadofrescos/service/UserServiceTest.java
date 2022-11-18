package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.UserMock;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.repository.IUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepo repo;

    @InjectMocks
    private UserService service;

    private User user;

    @BeforeEach
    public void setup(){
        this.user = UserMock.userTest();
    }

    @Test
    @DisplayName("findById return a user when userId is valid")
    public void findById_returnUser_WhenUserIdIsValid(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(this.user));

        User response = this.service.findById(user.getId());

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(user.getId());
        assertThat(response.getName()).isEqualTo(user.getName());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("findById throws not found exception when userId is invalid")
    public void findById_throwsNotFoundException_whenUserIdIsInvalid(){
        Long invalidId = 1L;

        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> service.findById(invalidId))
                .isInstanceOf(NotFoundException.class);
    }

}

package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.repository.IWarehouseRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @InjectMocks
    private WarehouseService warehouseService;

    @Mock
    private IWarehouseRepo repo;

    @Test
    @DisplayName("returns an exception if it doesn't find a warehouse")
    void warehouseNotFound () {
        final Long id = 123456789L;

        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> warehouseService.findById(id))
                .isInstanceOf(NotFoundException.class);
    }
}

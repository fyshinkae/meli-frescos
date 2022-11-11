package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.repository.ISectionRepo;
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
public class SectionServiceTest {

    @InjectMocks
    private SectionService sectionService;

    @Mock
    private ISectionRepo repo;

    @Test
    @DisplayName("Validate if a section is not found")
    void sectionNotFound() {
        final Long id = 123456789L;

        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> sectionService.findById(id))
                .isInstanceOf(NotFoundException.class);
    }
}

package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.SectionMock;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.repository.ISectionRepo;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    @InjectMocks
    private SectionService sectionService;

    @Mock
    private ISectionRepo repo;

    private Section section;

    @BeforeEach
    void setup(){
        this.section = SectionMock.SectionTest();
    }
    @Test
    @DisplayName("findById return a section when id is valid")
    void findById_returnSection_whenIdIsValid() {
        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.ofNullable(this.section));

        Section response = this.sectionService.findById(this.section.getId());

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(this.section.getId());
        assertThat(response.getCapacity()).isEqualTo(this.section.getCapacity());
        assertThat(response.getMinTemperature()).isEqualTo(this.section.getMinTemperature());
        assertThat(response.getInboundOrders().size()).isEqualTo(this.section.getInboundOrders().size());
    }

    @Test
    @DisplayName("findById throws NotFoundException when id is invalid")
    void findById_throwsNotFoundException_whenIdIsInvalid() {
        final Long id = 123456789L;

        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> sectionService.findById(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("findById throws NotFoundException when Response is Empty")
    void findSectionByWarehouseId_throwsNotFoundException_whenResponseIsEmpty() {
        final Long sectionId = 999L;
        final Long warehouseId = 999L;

        Mockito.when(repo.findSectionByWarehouseId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.empty());

        assertThatThrownBy(() -> sectionService.findSectionByWarehouseId(sectionId, warehouseId))
                .isInstanceOf(NotFoundException.class);
    }
}

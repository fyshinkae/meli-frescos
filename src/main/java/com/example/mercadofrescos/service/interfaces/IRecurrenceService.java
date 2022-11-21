package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.purchase.RecurrenceOrderDTO;
import com.example.mercadofrescos.dto.purchase.RecurrenceResponseDTO;
import com.example.mercadofrescos.model.RecurrenceOrder;

import java.util.List;

public interface IRecurrenceService {
    RecurrenceResponseDTO createRecurrence(RecurrenceOrderDTO recurrenceOrder);

    List<RecurrenceOrder> getAllRecurrences();

    void deleteByID(Long id);
}

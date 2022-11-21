package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.purchase.RecurrenceOrderDTO;
import com.example.mercadofrescos.dto.purchase.RecurrenceResponseDTO;
import com.example.mercadofrescos.model.RecurrenceOrder;

public interface IRecurrenceService {
    RecurrenceResponseDTO createRecurrence(RecurrenceOrderDTO recurrenceOrder);
}

package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.shipping.ShippingRequestDTO;
import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.repository.IShippingRepo;
import com.example.mercadofrescos.service.interfaces.IShippingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingService implements IShippingService {

    private final IShippingRepo repo;

    @Override
    public Shipping update(Long id, Shipping shipping) {

        Shipping shippingObj = this.repo.findById(id).orElseThrow(
                () -> new RuntimeException("Shipping Not Found"));

        shippingObj.setStatusShipping(shipping.getStatusShipping());
        shippingObj.setDescription(shipping.getDescription());

        return repo.save(shippingObj);
    }

    @Override
    public List<Shipping> findAll() {
        List<Shipping> shippingList = repo.findAll();

        if (shippingList.isEmpty()){
            throw new EntityNotFoundException("Shipping not found");
        }
        return  shippingList;
    }
}

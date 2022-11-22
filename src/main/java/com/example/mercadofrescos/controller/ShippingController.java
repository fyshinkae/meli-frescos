package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.shipping.ShippingRequestDTO;
import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.service.interfaces.IShippingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/orders/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final IShippingService service;


    @GetMapping("")
    public ResponseEntity<List<Shipping>> findAll() {
        return ResponseEntity.ok().body(this.service.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipping> update(
            @PathVariable Long id, @RequestBody ShippingRequestDTO shipping) {

        Shipping shippingMapper = new Shipping();
        shippingMapper.setUpdateDate(LocalDateTime.now());
        shippingMapper.setStatusShipping(shipping.getStatusShipping());
        shippingMapper.setDescription(shipping.getDescription());

        Shipping shippingUpdate = service.update(id, shippingMapper);

        return ResponseEntity.ok().body(shippingUpdate);
    }


}

package com.example.mercadofrescos.model;

import com.example.mercadofrescos.model.enums.StatusShipping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusShipping statusShipping = StatusShipping.PREPARING;

    private String description = "Your order has been received";

    private LocalDateTime updateDate = LocalDateTime.now();
}

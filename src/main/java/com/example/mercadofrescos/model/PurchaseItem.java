package com.example.mercadofrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    @JsonIgnoreProperties("productId")
    private Product productId;

    @ManyToOne
    @JoinColumn(name = "purchaseOrderId", nullable = false)
    @JsonIgnoreProperties("purchaseOrderId")
    private PurchaseOrder purchaseOrderId;

    @Column(nullable = false)
    private Integer productQuantity;
}

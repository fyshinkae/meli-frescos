package com.example.mercadofrescos.model;

import com.example.mercadofrescos.model.enums.StatusOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "customer_id")
    private User customer;

    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "purchaseOrderId")
    @JsonIgnoreProperties("productId")
    private List<PurchaseItem> itemList;

    @Column(nullable = false)
    private Boolean reservation;
}

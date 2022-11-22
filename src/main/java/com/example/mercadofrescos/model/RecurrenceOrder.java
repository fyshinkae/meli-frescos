package com.example.mercadofrescos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecurrenceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long dayOfMonth;

    @Column(nullable = false)
    private LocalDate createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaseOrderId")
    private PurchaseOrder purchaseOrder;
}

package com.example.mercadofrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BatchStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    @JsonIgnoreProperties("batches")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "inboundOrderId", nullable = false)
    @JsonIgnoreProperties("batches")
    private InboundOrder inboundOrder;

    @Column(nullable = false)
    private Float currentTemperature;

    @Column(nullable = false)
    private LocalDate manufacturingDate;

    @Column(nullable = false)
    private LocalDateTime manufacturingTime;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private int productQuantity;

    @Column(nullable = false)
    private Float volume;
}

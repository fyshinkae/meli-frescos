package com.example.mercadofrescos.model;

import com.example.mercadofrescos.model.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouseId", nullable = false)
    @JsonIgnoreProperties("warehouse")
    private Warehouse warehouse;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Float capacity, minTemperature;

    @OneToMany(mappedBy = "section", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("section")
    private Set<InboundOrder> inboundOrders;
}

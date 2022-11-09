package com.example.mercadofrescos.model;

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
public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "sectionId", nullable = false)
    @JsonIgnoreProperties("inboundOrders")
    private Section section;

    @OneToMany(mappedBy = "inboundOrder", cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("batches")
    private List<BatchStock> batches;

}

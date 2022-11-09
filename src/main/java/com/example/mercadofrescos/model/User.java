package com.example.mercadofrescos.model;

import com.example.mercadofrescos.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name, email, password;

    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "seller")
    @JsonIgnoreProperties("seller")
    private Set<Product> products;

    @OneToOne(mappedBy = "agent", cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties("agent")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private List<PurchaseOrder> order;
}

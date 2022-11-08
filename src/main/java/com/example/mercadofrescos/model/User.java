package com.example.mercadofrescos.model;

import com.example.mercadofrescos.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
}

package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerResponseDTO {
    private String name, email;

    /**
     * Converte o modelo user de banco para um DTO
     * @author Gabriel
     * @param user objeto a ser convertido para dto
     */
    public SellerResponseDTO(User user){
        this.name = user.getName();
        this.email = user.getEmail();
    }
}

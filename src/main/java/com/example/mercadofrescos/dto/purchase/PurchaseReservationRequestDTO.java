package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReservationRequestDTO {
    @NotNull
    private Long buyerId;

    @NotNull
    private LocalDate date;

    @NotEmpty
    @Valid
    private List<PurchaseItemDTO> products;

    /**
     * Converte um PurchaseRequestDTO para um objeto PurchaseOrder com reserva
     * @author Theus
     * @param purchase DTO a ser convertido
     * @return um objeto do tipo PurchaseOrder
     */
    public static PurchaseOrder convert(PurchaseReservationRequestDTO purchase) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStatusOrder(StatusOrder.ABERTO);
        purchaseOrder.setDate(purchase.getDate());
        purchaseOrder.setReservation(true);

        User customer = new User();
        customer.setId(purchase.getBuyerId());
        purchaseOrder.setCustomer(customer);

        purchaseOrder.setItemList(purchase.getProducts().stream().map((item) -> {
            PurchaseItem purchaseItem = PurchaseItemDTO.convert(item);
            purchaseItem.setPurchaseOrder(purchaseOrder);

            return purchaseItem;
        }).collect(Collectors.toList()));

        return purchaseOrder;
    }
}

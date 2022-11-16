package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.PurchaseRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseReservationResponseDTO;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.repository.IPurchaseItemRepo;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseReservationService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseReservationService implements IPurchaseReservationService {

    private final IPurchaseOrderRepo purchaseOrderRepo;
    private final IUserService userService;
    private final IPurchaseItemRepo purchaseItemRepo;
    private final IProductService productService;

    /**
     * Cria uma reserva de pedido
     * @author Theus
     * @param purchase Uma ordem de reserva mandada pelo usuário
     * @return Retorna um objeto do modelo PurchaseReservationResponseDTO
     */
    @Override
    public PurchaseReservationResponseDTO createReservation(PurchaseOrder purchase) {
        User customer = this.userService.findById(purchase.getCustomer().getId());
        purchase.setCustomer(customer);

        List<PurchaseItem> purchaseItemList = purchase.getItemList();

        productService.validAllExists(
                purchaseItemList.stream()
                        .map(item -> item.getProductId().getId())
                        .collect(Collectors.toList())
        );

        PurchaseOrder purchaseCreated = purchaseOrderRepo.save(purchase);
        purchaseItemRepo.saveAll(purchaseItemList);

        return new PurchaseReservationResponseDTO(purchaseCreated);
    }

    /**
     * Busca todos os pedidos reservados
     * @author Theus
     * @return Retorna uma lista de objetos do modelo PurchaseRequestDTO
     */
    @Override
    public List<PurchaseRequestDTO> findAll() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepo.findAllReservation();

        return purchaseOrders.stream().map(PurchaseRequestDTO::convert).collect(Collectors.toList());
    }
}

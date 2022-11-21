package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.CheckAvailabilityResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseReservationResponseDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import com.example.mercadofrescos.service.interfaces.IPurchaseReservationService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseReservationService implements IPurchaseReservationService {

    private final IPurchaseOrderRepo purchaseOrderRepo;
    private final IPurchaseOrderService purchaseOrderService;
    private final IUserService userService;
    private final IProductService productService;
    private final IBatchStockRepo batchStockRepo;

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

        purchase.setItemList(purchase.getItemList()
                .stream()
                .peek((item) -> {
                    Product product = productService.findById(item.getProduct().getId());
                    item.setProduct(product);
                    item.setPurchaseOrder(purchase);
                }).collect(Collectors.toList()));

        PurchaseOrder purchaseCreated = purchaseOrderRepo.save(purchase);

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

    /**
     * Busca apenas um pedido reservado
     * @author Theus
     * @param id do pedido
     * @return Retorna um objeto do modelo PurchaseRequestDTO ou uma exceção
     */
    @Override
    public PurchaseReservationResponseDTO findById(Long id) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepo.findById(id);

        if (purchaseOrder.isEmpty()) throw new NotFoundException("Purchase order not found");

        return new PurchaseReservationResponseDTO(purchaseOrder.get());
    }

    /**
     * Exclui uma reserva de pedido
     * @author Theus
     * @param id do pedido
     */
    @Override
    public void deleteById(Long id) {
        PurchaseOrder purchaseOrder = this.purchaseOrderService.findById(id);

        if (!purchaseOrder.getReservation())
            throw new NotFoundException("Purchase order is not a reservation");

        purchaseOrderRepo.delete(purchaseOrder);
    }

    /**
     * Verifica a disponibilidade da reserva de um pedido
     * @author Theus
     * @param id do pedido
     */
    @Override
    public CheckAvailabilityResponseDTO verifyAvailability(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);

        purchaseOrderService.getValidProductList(purchaseOrder.getItemList());

        return new CheckAvailabilityResponseDTO(purchaseOrder);
    }

    /**
     * Finaliza a reserva de um pedido
     * @author Theus
     * @param id do pedido
     * @return um objeto do modelo PurchaseOrderRequestDTO
     */
    @Override
    public PurchaseOrderRequestDTO finishReservation(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);

        this.verifyAvailability(id);
        this.updateQntBatchStock(purchaseOrder.getItemList());

        purchaseOrder.setReservation(false);
        purchaseOrder.setStatusOrder(StatusOrder.FINALIZADO);

        PurchaseOrder purchaseUpdated = purchaseOrderRepo.save(purchaseOrder);

        return PurchaseOrderRequestDTO.convert(purchaseUpdated);
    }

    /**
     * Atualiza a quantidade do produto no lote
     * @author Theus
     * @param purchaseItems uma lista dos produtos do pedido
     */
    private void updateQntBatchStock(List<PurchaseItem> purchaseItems) {
        purchaseItems.forEach(purchaseItem -> {
            Set<BatchStock> batchStocks = purchaseItem.getProduct().getBatches();
            batchStocks.forEach(item -> {
                int currentQnt = item.getProductQuantity() - purchaseItem.getProductQuantity();
                item.setProductQuantity(currentQnt);
                batchStockRepo.save(item);
            });
        });
    }
}

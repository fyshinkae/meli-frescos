package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchasePriceDTO;
import com.example.mercadofrescos.exception.InvalidPurchaseException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseItemService;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderRepo purchaseOrderRepo;
    private final IUserService userService;
    private final IProductService productService;
    private final IPurchaseItemService purchaseItemService;

    // TODO: Atualizar o productQuantity no batchStock
    // TODO: Atualizar o capacity da Section do batchStock atualizado

    @Override
    public void createReservation(PurchaseOrder purchase) {

    }

    /**
     * Calcula o valor total dos itens do carrinho
     * @author Felipe, Giovanna, Matheus, Gabriel, Theus
     * @param purchaseOrder Uma ordem de compra mandada pelo usuário
     * @return Retorna o preço total da ordem de compra
     */
    @Override
    public PurchasePriceDTO getCartAmount(PurchaseOrder purchaseOrder) {
        double totalCartAmount = 0d;
        BigDecimal singleCartAmount;

        User customer = this.userService.findById(purchaseOrder.getCustomer().getId());
        purchaseOrder.setCustomer(customer);

        List<PurchaseItem> purchaseItemList = purchaseOrder.getItemList();
        List<Product> products = this.getValidProductList(purchaseOrder.getItemList());

        for (PurchaseItem item : purchaseItemList) {
            Product product = products.iterator().next();
            singleCartAmount = product.getPrice().multiply(BigDecimal.valueOf(item.getProductQuantity()));
            totalCartAmount = singleCartAmount.add(BigDecimal.valueOf(totalCartAmount)).doubleValue();
        }

        this.savePurchaseOrder(purchaseOrder);
        return new PurchasePriceDTO(totalCartAmount);
    }

    /**
     * Obtém uma lista de produtos possíveis de compra a partir de uma lista de PurchaseItem
     * @author Felipe, Giovanna, Matheus, Gabriel, Theus
     * @param purchaseItems Lista de produtos recebida pelo request do usuário
     * @return Uma lista de produtos validada
     */
    private List<Product> getValidProductList(List<PurchaseItem> purchaseItems){
        List<Product> response = new ArrayList<>();
        List<Long> productIdErrors = new ArrayList<>();
        List<Long> productIdExpiration = new ArrayList<>();

        for(PurchaseItem item : purchaseItems){
            Product product = productService.findById(item.getProductId().getId());
            BatchStock batchStock = getValidBatchStockByCapacity(product, item.getProductQuantity());
            if (batchStock == null) {
                productIdErrors.add(product.getId());
            } else {
                LocalDate today = LocalDate.now();
                long daysBetween = today.until(batchStock.getDueDate(), ChronoUnit.DAYS);

                if (daysBetween <= 21) productIdExpiration.add(product.getId());
                else response.add(product);
            }
        }

        if (!productIdErrors.isEmpty()) {
            throw new InvalidPurchaseException("Products " + productIdErrors + " is not available");
        }

        if (!productIdExpiration.isEmpty())
            throw new InvalidPurchaseException("Products " + productIdExpiration + " close to expiration");

        return response;
    }

    /**
     * Valida se o produto está suficientemente presente em algum BatchStock
     * @author Felipe, Giovanna, Matheus, Gabriel, Theus
     * @param product Produto a ser adquirido
     * @param purchaseQuantity Quantidade do produto a ser adquirido
     * @return BatchStock que possui o produto com quantidade suficiente para compra
     */
    private BatchStock getValidBatchStockByCapacity(Product product, int purchaseQuantity){
        Set<BatchStock> batches = product.getBatches();
        for(BatchStock batchStock : batches){
            if(batchStock.getProductQuantity() > purchaseQuantity) {
                return batchStock;
            }
        }
        return null;
    }

    /**
     * Salva uma ordem de compra e seus itens na base de dados
     * @author Gabriel
     * @param purchaseOrder Ordem de compra a ser salva
     */
    private void savePurchaseOrder(PurchaseOrder purchaseOrder){
        if(purchaseOrder.getDate() == null){
            purchaseOrder.setDate(LocalDate.now());
        }

        this.purchaseOrderRepo.save(purchaseOrder);
        this.purchaseItemService.savePurchaseItemList(purchaseOrder.getItemList());
    }

    /**
     * Salva uma ordem de compra e seus itens na base de dados
     * @author Ma, Theus, Giovanna
     * @param id da Ordem de compra
     */
    public List<PurchaseItemResponseDTO> getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = this.findById(id);
        List<PurchaseItem> purchaseItems = purchaseOrder.getItemList();

        return purchaseItems.stream()
                .map(PurchaseItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma ordem de compra e seus itens na base de dados
     * @author Theus
     * @param id da Ordem de compra
     */
    @Override
    public PurchaseOrder findById(Long id) {
        Optional<PurchaseOrder> purchaseOrder = this.purchaseOrderRepo.findById(id);

        return purchaseOrder.orElseThrow(() -> new NotFoundException("Purchase order not found"));
    }

    /**
     * Altera o 'orderStatus'
     * @author Ma, Gabriel, Giovanna
     * @param updateStatus da Ordem
     */
    public PurchaseOrderRequestDTO updateOrderStatus(StatusOrder updateStatus, Long id) {
        PurchaseOrder purchaseOrder = this.findById(id);
        purchaseOrder.setStatusOrder(updateStatus);
        return PurchaseOrderRequestDTO.convert(purchaseOrderRepo.save(purchaseOrder));
    }
}

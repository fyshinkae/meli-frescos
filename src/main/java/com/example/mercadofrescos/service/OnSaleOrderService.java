package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchasePriceDTO;
import com.example.mercadofrescos.exception.InvalidPurchaseException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.service.interfaces.IOnSaleOrderService;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseItemService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnSaleOrderService implements IOnSaleOrderService {

    private final IPurchaseOrderRepo purchaseOrderRepo;
    private final IUserService userService;
    private final IProductService productService;
    private final IPurchaseItemService purchaseItemService;

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

    /**
     * Calcula o valor total dos itens do carrinho aplicando o desconto
     * progressivo relativo aos dias restantes para vencimento
     * @author Ma
     * @param purchaseOrder Uma ordem de compra mandada pelo usuário
     * @return Retorna o preço total da ordem de compra já com o desconto aplicado
     */
    @Override
    public PurchasePriceDTO getCartOnSale(PurchaseOrder purchaseOrder) {
        double totalCartOnSale = 0d;
        BigDecimal singleCartOnSale;

        User customer = this.userService.findById(purchaseOrder.getCustomer().getId());
        purchaseOrder.setCustomer(customer);

        List<PurchaseItem> purchaseItemList = purchaseOrder.getItemList();
        List<Product> products = this.getValidProductList(purchaseOrder.getItemList());


        for (PurchaseItem item : purchaseItemList) {
            Product product = products.iterator().next();
            BatchStock batchStock = getValidBatchStockByCapacityAndDueDate(product, item.getProductQuantity());
            BigDecimal productPrice =  product.getPrice();

            // Cálculo de dias para vencimento:
            LocalDate today = LocalDate.now();
            long daysBetween = today.until(batchStock.getDueDate(), ChronoUnit.DAYS);

            // Porcentagens:
            BigDecimal fivePerCent = productPrice.multiply(BigDecimal.valueOf(0.05));
            BigDecimal twoPerCent = productPrice.multiply(BigDecimal.valueOf(0.02));
            BigDecimal onePerCent = productPrice.multiply(BigDecimal.valueOf(0.01));

            // Relação Vencimento/Desconto
            if (daysBetween >= 21 && daysBetween <= 28) {
                productPrice = productPrice.subtract(fivePerCent);
            } else if (daysBetween > 29 && daysBetween <= 36) {
                productPrice = productPrice.subtract(twoPerCent);
            } else if (daysBetween > 37 && daysBetween <= 42) {
                productPrice = productPrice.subtract(onePerCent);
            }

            // Somando o valor do carrinho com desconto:
            singleCartOnSale = productPrice.multiply(BigDecimal.valueOf(item.getProductQuantity()));
            totalCartOnSale = singleCartOnSale.add(BigDecimal.valueOf(totalCartOnSale)).doubleValue();
        }

        this.savePurchaseOrder(purchaseOrder);

        return new PurchasePriceDTO(totalCartOnSale);
    }

    /**
     * Salva uma ordem de compra promocional e seus itens na base de dados
     * @author Ma, Theus, Giovanna
     * @param id da Ordem de compra
     */
    @Override
    public List<PurchaseItemResponseDTO> getOnSaleOrderById(Long id) {
        PurchaseOrder purchaseOrder = this.findById(id);
        List<PurchaseItem> purchaseItems = purchaseOrder.getItemList();

        return purchaseItems.stream()
                .map(PurchaseItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Altera o 'orderStatus' de uma ordem promocional
     * @author Ma, Gabriel, Giovanna
     * @param updateStatus da Ordem
     */
    public PurchaseOrderRequestDTO updateOnSaleOrderStatus(StatusOrder updateStatus, Long id) {
        PurchaseOrder purchaseOrder = this.findById(id);
        purchaseOrder.setStatusOrder(updateStatus);
        return PurchaseOrderRequestDTO.convert(purchaseOrderRepo.save(purchaseOrder));
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

        for(PurchaseItem item : purchaseItems){
            Product product = productService.findById(item.getProduct().getId());
            BatchStock batchStock = getValidBatchStockByCapacityAndDueDate(product, item.getProductQuantity());
            if (batchStock == null) {
                productIdErrors.add(product.getId());
            } else {
                response.add(product);
            }
        }
        this.verifyErrors(productIdErrors);

        return response;
    }

    /**
     * Verifica se existem erros e lança exceções
     * @author Felipe, Giovanna, Matheus, Gabriel, Theus
     * @param productIdErrors Lista de IDs de produtos com lotes vazios
     */
    private void verifyErrors(List<Long> productIdErrors) {
        if (!productIdErrors.isEmpty()) {
            throw new InvalidPurchaseException("Products " + productIdErrors + " is not available");
        };
    }

    /**
     * Valida se o produto está suficientemente presente em algum BatchStock
     * @author Felipe, Giovanna, Matheus, Gabriel, Theus
     * @param product Produto a ser adquirido
     * @param purchaseQuantity Quantidade do produto a ser adquirido
     * @return BatchStock que possui o produto com quantidade suficiente para compra
     */
    private BatchStock getValidBatchStockByCapacityAndDueDate(Product product, int purchaseQuantity){
        Set<BatchStock> batches = product.getBatches();
        Integer threeWeeks = 21;
        for(BatchStock batchStock : batches){
            if(batchStock.getProductQuantity() > purchaseQuantity
                    && validateDueDate(batchStock.getDueDate(), threeWeeks)) {
                return batchStock;
            }
        }

        return null;
    }

    /**
     * Valida se o lote está dentro do prazo esperado
     * @author Gabriel
     * @param batchStockDueDate prazo de validade do lote
     * @param days numero de dias minimo
     */
    private Boolean validateDueDate(LocalDate batchStockDueDate, Integer days){
        LocalDate today = LocalDate.now();
        long daysBetween = today.until(batchStockDueDate, ChronoUnit.DAYS);
        if (daysBetween <= days) return false;

        return true;
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
}

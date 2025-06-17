package com.arka.microservice.sales_ms.application.usecases;

import com.arka.microservice.sales_ms.domain.exception.NotFoundException;
import com.arka.microservice.sales_ms.domain.model.CartDetailModel;
import com.arka.microservice.sales_ms.domain.model.CartModel;
import com.arka.microservice.sales_ms.domain.ports.in.ICartInPort;
import com.arka.microservice.sales_ms.domain.ports.out.ICartDetailOutPort;
import com.arka.microservice.sales_ms.domain.ports.out.ICartOutPort;
import com.arka.microservice.sales_ms.domain.ports.out.IInventoryOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.arka.microservice.sales_ms.domain.Util.CartConstant.*;

@Component
@RequiredArgsConstructor
public class CartUseCaseImpl implements ICartInPort {

  private final ICartOutPort cartOutPort;
  private final IInventoryOutPort inventoryOutPort;
  private final ICartDetailOutPort cartDetailOutPort;

  @Override
  public Mono<String> createCart(Long userId) {
    CartModel cartModel = new CartModel();
    cartModel.setUserId(userId);
    cartModel.setStatus(CART_ACTIVE);
    cartModel.setCartDetails(new ArrayList<>());
    cartModel.setCreatedAt(LocalDateTime.now());
    cartModel.setUpdatedAt(LocalDateTime.now());
    return cartOutPort.save(cartModel)
            .thenReturn(CART_CREATED);
  }

  @Override
  public Mono<String> insertProductToCart(Long cartId, Long productId) {
    int quantityToAdd = 1;

    return inventoryOutPort.getProductInventory(productId)
            .flatMap(inventoryDto -> {
              if (inventoryDto.getActualStock() < quantityToAdd) {
                return Mono.error(new RuntimeException("Sold Out"));
              }
              return inventoryOutPort.removeStockFromInventory(inventoryDto.getId(), productId, quantityToAdd)
                      .then(inventoryOutPort.getProductById(productId));
            })
            .flatMap(productDto -> {
              CartDetailModel newDetail = new CartDetailModel();
              newDetail.setCartId(cartId);
              newDetail.setProductId(productId);
              newDetail.setQuantity(quantityToAdd);
              newDetail.setSubtotal(productDto.getPrice() * quantityToAdd);
              newDetail.setCreatedAt(LocalDateTime.now());
              newDetail.setUpdatedAt(LocalDateTime.now());
              return cartDetailOutPort.save(newDetail);
            })
            .thenReturn(PRODUCT_ADDED_TO_CART);
  }

  @Override
  public Mono<String> removeProductFromCart(Long cartId, Long productId) {
    return cartDetailOutPort.findByCartIdAndProductId(cartId, productId)
            .switchIfEmpty(Mono.error(new NotFoundException("Product not found in cart " + cartId)))
            .flatMap(detailFound -> {
              int quantityInCart = detailFound.getQuantity();
              return inventoryOutPort.getProductInventory(productId)
                      .flatMap(inventoryDto ->
                              inventoryOutPort.addStockToInventory(inventoryDto.getId(), productId, quantityInCart)
                      )
                      .then(cartDetailOutPort.delete(detailFound.getId()))
                      .thenReturn(PRODUCT_REMOVED_FROM_CART);
            });
  }

  @Override
  public Flux<CartDetailModel> showProductsInCart(Long cartId) {
    return cartDetailOutPort.findByCartId(cartId)
            .switchIfEmpty(Mono.error(new NotFoundException("Cart not found " + cartId)));
  }
}

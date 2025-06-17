package com.arka.microservice.sales_ms.infra.driver.rest.controller;

import com.arka.microservice.sales_ms.domain.ports.in.ICartInPort;
import com.arka.microservice.sales_ms.infra.driver.rest.dto.response.CartDetailResponseDto;
import com.arka.microservice.sales_ms.infra.driver.rest.mapper.ICartDetailRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Gestión de carritos de compras")
public class CartController {

  private final ICartInPort cartInPort;
  private final ICartDetailRestMapper cartRestMapper;

  @Operation(summary = "Create Cart", description = "Create and save a cart per user")
  @PostMapping("/create/{userId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<String> createCart(@PathVariable Long userId) {
    return cartInPort.createCart(userId);
  }

  @Operation(summary = "Add Product to Cart", description = "Add an existing product to a cart")
  @PutMapping("/add/{cartId}/product/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
    return cartInPort.insertProductToCart(cartId, productId);
  }

  @Operation(summary = "Remove Product From Cart", description = "Remove an existing product from a existing cart")
  @PutMapping("/remove/{cartId}/product/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
    return cartInPort.removeProductFromCart(cartId, productId);
  }

  @Operation(summary = "Show Products in Cart", description = "Show all the products in cart")
  @GetMapping("/show/{cartId}")
  @ResponseStatus(HttpStatus.OK)
  public Flux<CartDetailResponseDto> showProductsInCart(@PathVariable Long cartId) {
    return cartInPort.showProductsInCart(cartId)
            .map(cartRestMapper::modelToResponseDto);
  }
}

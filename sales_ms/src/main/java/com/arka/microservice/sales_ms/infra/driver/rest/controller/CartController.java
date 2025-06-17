package com.arka.microservice.sales_ms.infra.driver.rest.controller;

import com.arka.microservice.sales_ms.domain.ports.in.ICartInPort;
import com.arka.microservice.sales_ms.infra.driver.rest.dto.response.CartDetailResponseDto;
import com.arka.microservice.sales_ms.infra.driver.rest.mapper.ICartDetailRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final ICartInPort cartInPort;
  private final ICartDetailRestMapper cartRestMapper;

  @PostMapping("/create/{userId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<String> createCart(@PathVariable Long userId) {
    return cartInPort.createCart(userId);
  }

  @PutMapping("/add/{cartId}/product/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
    return cartInPort.insertProductToCart(cartId, productId);
  }

  @PutMapping("/remove/{cartId}/product/{productId}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
    return cartInPort.removeProductFromCart(cartId, productId);
  }

  @GetMapping("/show/{cartId}")
  @ResponseStatus(HttpStatus.OK)
  public Flux<CartDetailResponseDto> showProductsInCart(@PathVariable Long cartId) {
    return cartInPort.showProductsInCart(cartId)
            .map(cartRestMapper::modelToResponseDto);
  }
}

package com.arka.microservice.customer_ms.infra.driven.webclient.adapter;


import com.arka.microservice.customer_ms.domain.ports.out.ICartOutPort;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartAdapter implements ICartOutPort {

  private static final String CART_SERVICE = "cartService";

  @Qualifier("cartApiClient")
  private final WebClient webClient;

  @Override
  @CircuitBreaker(name = CART_SERVICE, fallbackMethod = "getCartFallback")
  @Retry(name = CART_SERVICE)
  @Bulkhead(name = CART_SERVICE)
  @TimeLimiter(name = CART_SERVICE)
  public Mono<String> createCart(Long userId, String jwtToken) {
    return webClient.post()
            .uri(uriBuilder ->
                    uriBuilder
                            .path("/api/cart/create/{userId}")
                            .build(userId)
            )
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
            .body(BodyInserters.empty())
            .retrieve()
            .bodyToMono(String.class);
  }

  private Mono<String> getCartFallback(Long userId, String jwtToken, Exception e) {
    log.warn("Fallback: no se pudo obtener el carrito con ID {}: {}", userId, e.getMessage());
    return Mono.just("No se pudo validar el carrito de compras (fallback).");
  }
}


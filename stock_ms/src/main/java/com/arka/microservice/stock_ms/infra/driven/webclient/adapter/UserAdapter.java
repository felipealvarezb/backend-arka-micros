package com.arka.microservice.stock_ms.infra.driven.webclient.adapter;

import com.arka.microservice.stock_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.stock_ms.infra.driven.webclient.dto.UserLogisticDTO;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAdapter implements IUserOutPort {

  private static final String USER_SERVICE = "userService";

  @Qualifier("userApiClient")
  private final WebClient webClient;

  @Override
  @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "getUserFallback")
  @Retry(name = USER_SERVICE)
  @Bulkhead(name = USER_SERVICE)
  @TimeLimiter(name = USER_SERVICE)
  public Mono<String> getUserAdminLogistic(Long userId) {
    return webClient.get()
            .uri(uriBuilder ->
                    uriBuilder
                            .path("/api/admin/admin-logistic/{id}")
                            .build(userId)
            )
            .retrieve()
            .bodyToMono(UserLogisticDTO.class)
            .map(user -> String.format(
                    "Usuario válido: %s %s - Email: %s - Id: %s",
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getId()
            ));
  }

  private Mono<String> getUserFallback(Long userId, Exception e) {
    log.warn("Fallback: no se pudo obtener el usuario con ID {}: {}", userId, e.getMessage());
    return Mono.just("No se pudo validar el supervisor logístico (fallback).");
  }
}

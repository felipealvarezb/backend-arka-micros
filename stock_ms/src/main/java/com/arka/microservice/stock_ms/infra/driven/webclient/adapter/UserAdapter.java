package com.arka.microservice.stock_ms.infra.driven.webclient.adapter;

import com.arka.microservice.stock_ms.domain.ports.out.IUserOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAdapter implements IUserOutPort {

  @Override
  public Mono<String> getUserAdminLogistic(Long userId) {
    return null;
  }
}

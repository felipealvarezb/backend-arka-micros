package com.arka.microservice.customer_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.customer_ms.domain.model.RolModel;
import com.arka.microservice.customer_ms.domain.ports.out.IRolOutPort;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.mapper.IRolEntityMapper;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.repository.IRolEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RolRepositoryAdapter implements IRolOutPort {

  private final IRolEntityRepository rolEntityRepository;
  private final IRolEntityMapper rolEntityMapper;

  @Override
  public Mono<RolModel> findByName(String name) {
    return rolEntityRepository
            .findByName(name)
            .map(rolEntityMapper::entityToModel);
  }
}

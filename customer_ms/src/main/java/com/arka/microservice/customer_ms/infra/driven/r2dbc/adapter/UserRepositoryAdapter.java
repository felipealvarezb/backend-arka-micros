package com.arka.microservice.customer_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.mapper.IUserEntityMapper;
import com.arka.microservice.customer_ms.infra.driven.r2dbc.repository.IUserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements IUserOutPort {

  private final IUserEntityRepository userEntityRepository;
  private final IUserEntityMapper userEntityMapper;


  @Override
  public Mono<UserModel> findByEmail(String email) {
    return userEntityRepository
            .findByEmail(email)
            .map(userEntityMapper::entityToModel);
  }

  @Override
  public Mono<UserModel> findByDni(String dni) {
    return userEntityRepository
            .findByDni(dni)
            .map(userEntityMapper::entityToModel);
  }

  @Override
  public Mono<UserModel> save(UserModel user) {
    return userEntityRepository
            .save(userEntityMapper.modelToEntity(user))
            .map(userEntityMapper::entityToModel);
  }
}

package com.arka.microservice.customer_ms.domain.ports.out;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserOutPort {
  Mono<UserModel> findByEmail(String email);
  Mono<UserModel> findByDni(String dni);
  Flux<UserModel> findAll();
  Mono<UserModel> save(UserModel user);

}

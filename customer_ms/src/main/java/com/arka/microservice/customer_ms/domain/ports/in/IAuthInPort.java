package com.arka.microservice.customer_ms.domain.ports.in;

import reactor.core.publisher.Mono;

public interface IAuthInPort {

  Mono<String> authenticate(String email, String password);
}

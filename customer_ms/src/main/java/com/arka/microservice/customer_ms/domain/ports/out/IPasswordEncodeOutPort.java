package com.arka.microservice.customer_ms.domain.ports.out;

import reactor.core.publisher.Mono;

public interface IPasswordEncodeOutPort {
  Mono<String> encodePassword(String password);
  Mono<Boolean> matchesPassword(String password, String encodedPassword);
}

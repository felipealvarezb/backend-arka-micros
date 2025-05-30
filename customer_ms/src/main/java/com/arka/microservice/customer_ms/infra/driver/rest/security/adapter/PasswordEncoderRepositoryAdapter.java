package com.arka.microservice.customer_ms.infra.driver.rest.security.adapter;

import com.arka.microservice.customer_ms.domain.ports.out.IPasswordEncodeOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PasswordEncoderRepositoryAdapter implements IPasswordEncodeOutPort {

  private final PasswordEncoder encoder;

  @Override
  public Mono<String> encodePassword(String password) {
    return Mono.fromCallable(() -> encoder.encode(password));
  }

  @Override
  public Mono<Boolean> matchesPassword(String password, String encodedPassword) {
    return Mono.fromCallable(() -> encoder.matches(password, encodedPassword));
  }
}

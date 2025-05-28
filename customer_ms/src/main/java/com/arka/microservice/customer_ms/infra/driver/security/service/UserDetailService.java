package com.arka.microservice.customer_ms.infra.driver.security.service;

import com.arka.microservice.customer_ms.domain.ports.in.IUserDetailInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserDetailService implements ReactiveUserDetailsService {

  private final IUserDetailInPort userDetailInPort;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userDetailInPort.execute(username);
  }
}

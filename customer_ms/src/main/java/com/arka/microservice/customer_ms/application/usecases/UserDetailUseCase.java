package com.arka.microservice.customer_ms.application.usecases;

import com.arka.microservice.customer_ms.domain.exception.NotFoundException;
import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.out.IRolOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.arka.microservice.customer_ms.domain.util.UserConstants.USER_ROLE_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class UserDetailUseCase implements ReactiveUserDetailsService {

  private final IUserOutPort userOutPort;
  private final IRolOutPort rolOutPort;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userOutPort.findByEmail(username)
            .flatMap(userModel -> rolOutPort.findById(userModel.getRoleId())
                    .switchIfEmpty(Mono.error(new RuntimeException(USER_ROLE_NOT_FOUND)))
                    .map(rol -> User.builder()
                            .username(userModel.getEmail())
                            .password(userModel.getPassword())
                            .authorities(Collections.singletonList(new SimpleGrantedAuthority(rol.getName())))
                            .build()));
  }
}

package com.arka.microservice.customer_ms.application.usecases;

import com.arka.microservice.customer_ms.domain.exception.NotFoundException;
import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.in.IUserDetailInPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserDetailUseCase implements IUserDetailInPort {

  private static final String ERR_USER_NOT_FOUND = "ERR_USER_NOT_FOUND";
  private final IUserOutPort userOutPort;

  @Override
  public Mono<UserDetails> execute(String username) {
    return userOutPort
            .findByEmail(username)
            .switchIfEmpty(Mono.error(new NotFoundException(ERR_USER_NOT_FOUND, "User not found")))
            .flatMap(user -> {
              return Mono.just(createUserDetail(user));
            });
  }

  private UserDetails createUserDetail(UserModel user) {
    return new User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRol().getName()))
    );
  }
}

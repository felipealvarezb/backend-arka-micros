package com.arka.microservice.customer_ms.application.usecases;


import com.arka.microservice.customer_ms.domain.ports.in.IAuthInPort;
import com.arka.microservice.customer_ms.domain.ports.out.ICartOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IPasswordEncodeOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.customer_ms.infra.driver.rest.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.arka.microservice.customer_ms.domain.util.SecurityConstants.INVALID_PASSWORD;
import static com.arka.microservice.customer_ms.domain.util.SecurityConstants.USER_EMAIL_NOT_FOUND;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthUserCase implements IAuthInPort {

  private final IUserOutPort userOutPort;
  private final IPasswordEncodeOutPort passwordEncodeOutPort;
  private final JwtUtil jwtUtil;
  private final ICartOutPort cartOutPort;

  @Override
  public Mono<String> authenticate(String email, String password) {
    return userOutPort.findByEmail(email)
            .switchIfEmpty(Mono.error(new BadCredentialsException(USER_EMAIL_NOT_FOUND)))
            .flatMap(user -> passwordEncodeOutPort.matchesPassword(password, user.getPassword())
                    .flatMap(matches -> {
                      if (!matches) {
                        return Mono.error(new BadCredentialsException(INVALID_PASSWORD));
                      }
                      return jwtUtil.generateToken(user)
                              .doOnNext(token -> {
                                cartOutPort.createCart(user.getId(), token)
                                        .subscribe(
                                                response -> log.info("Carrito creado exitosamente para usuario {}. Respuesta: {}", user.getId(), response),
                                                error -> log.error("Error al crear el carrito para el usuario {}: {}", user.getId(), error.getMessage())
                                        );
                              });
                    })
            );
  }
}

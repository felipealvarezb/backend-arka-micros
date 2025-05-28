package com.arka.microservice.customer_ms.application.usecases;


import com.arka.microservice.customer_ms.domain.ports.in.IAuthInPort;
import com.arka.microservice.customer_ms.domain.ports.out.IPasswordEncodeOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.customer_ms.infra.driver.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthUserCase implements IAuthInPort {

  private final IUserOutPort userOutPort;
  private final IPasswordEncodeOutPort passwordEncodeOutPort;
  private final JwtUtil jwtUtil;

  @Override
  public Mono<String> authenticate(String email, String password) {
    return userOutPort.findByEmail(email)
            .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
            .flatMap(user -> passwordEncodeOutPort.matchesPassword(password, user.getPassword())
                    .flatMap(matches -> {
                      if (!matches) {
                        return Mono.error(new RuntimeException("Invalid password"));
                      }
                      return Mono.just(jwtUtil.generateToken(user.getEmail()));
                    })
            );
  }
}

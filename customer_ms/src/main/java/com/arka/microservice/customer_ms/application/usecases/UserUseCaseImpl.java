package com.arka.microservice.customer_ms.application.usecases;

import com.arka.microservice.customer_ms.domain.model.RolModel;
import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.in.IUserInPort;
import com.arka.microservice.customer_ms.domain.ports.out.IPasswordEncodeOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IRolOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.customer_ms.domain.util.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.customer_ms.domain.util.UserConstants.*;

@Component
@RequiredArgsConstructor
public class UserUseCaseImpl implements IUserInPort {

  private final IUserOutPort userOutPort;
  private final IRolOutPort rolOutPort;
  private final IPasswordEncodeOutPort passwordEncodeOutPort;

  @Override
  public Mono<UserModel> registerUser(UserModel userModel) {
    Mono<RolModel> rolMono =
            rolOutPort.findByName(USER_ROLE_NAME)
                    .switchIfEmpty(Mono.error(new RuntimeException(USER_ROLE_NOT_FOUND)))
                    .cache();
    return Mono.just(userModel)
            .flatMap(user -> UserValidation.validateFirstName(user.getFirstName()).thenReturn(user))
            .flatMap(user -> UserValidation.validateEmail(user.getEmail()).thenReturn(user))
            .flatMap(user -> UserValidation.validatePhone(user.getPhone()).thenReturn(user))
            .flatMap(user -> UserValidation.validatePassword(user.getPassword()).thenReturn(user))
            .flatMap(user -> validateEmailDoesNotExist(user.getEmail()).thenReturn(user))
            .flatMap(user -> validateDniDoesNotExist(user.getDni()).thenReturn(user))
            .flatMap(user -> rolMono.map(rol ->{
              user.setRol(rol);
              return user;
            }))
            .flatMap(this::encodePassword)
            .map(user -> {
              user.setIsActive(true);
              user.setCreatedAt(LocalDateTime.now());
              user.setUpdatedAt(LocalDateTime.now());
              return user;
            })
            .flatMap(userOutPort::save)
           .flatMap(saved -> rolMono.map(rol -> {
             saved.setRol(rol);
             return saved;
           }));
  }

  @Override
  public Mono<UserModel> getUserProfileInfo() {
    return ReactiveSecurityContextHolder.getContext()
            .flatMap(context -> {
              Authentication authentication = context.getAuthentication();
              String email = authentication.getName();
              return userOutPort.findByEmail(email)
                      .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
            });
  }


  private Mono<Void> validateEmailDoesNotExist(String email) {
    return userOutPort.findByEmail(email)
            .flatMap(user -> user != null
                    ? Mono.error(new RuntimeException(EMAIL_ALREADY_EXISTS))
                    : Mono.empty()
            );
  }

  private Mono<Void> validateDniDoesNotExist(String dni) {
    return userOutPort.findByDni(dni)
            .flatMap(user -> user != null
                    ? Mono.error(new RuntimeException(DNI_ALREADY_EXISTS))
                    : Mono.empty()
            );

  }

  private Mono<UserModel> encodePassword(UserModel userModel) {
    return passwordEncodeOutPort.encodePassword(userModel.getPassword())
            .map(encodedPassword -> {
              userModel.setPassword(encodedPassword);
              return userModel;
            });
  }
}

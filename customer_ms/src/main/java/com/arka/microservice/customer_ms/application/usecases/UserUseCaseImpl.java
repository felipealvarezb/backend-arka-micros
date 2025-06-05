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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.arka.microservice.customer_ms.domain.util.UserConstants.*;

@Component
@RequiredArgsConstructor
public class UserUseCaseImpl implements IUserInPort {

  private final IUserOutPort userOutPort;
  private final IRolOutPort rolOutPort;
  private final IPasswordEncodeOutPort passwordEncodeOutPort;

  @Override
  public Mono<UserModel> registerUser(UserModel userModel) {
    Mono<Long> rolIdMono = rolOutPort.findByName(USER_ROLE_NAME)
            .switchIfEmpty(Mono.error(new RuntimeException(USER_ROLE_NOT_FOUND)))
            .map(RolModel::getId)
            .cache();

    return Mono.just(userModel)
            .flatMap(user -> Mono.when(
                    UserValidation.validateFirstName(user.getFirstName()),
                    UserValidation.validateEmail(user.getEmail()),
                    UserValidation.validatePhone(user.getPhone()),
                    UserValidation.validatePassword(user.getPassword())
            ).thenReturn(user))
            .flatMap(user -> Mono.when(
                    validateEmailDoesNotExist(user.getEmail()),
                    validateDniDoesNotExist(user.getDni())
            ).thenReturn(user))
            .flatMap(user -> rolIdMono.map(rolId -> {
              user.setRoleId(rolId);
              return user;
            }))
            .flatMap(this::encodePassword)
            .map(user -> {
              user.setIsActive(true);
              user.setCreatedAt(LocalDateTime.now());
              user.setUpdatedAt(LocalDateTime.now());
              return user;
            })
            .flatMap(userOutPort::save);
  }

  @Override
  public Mono<UserModel> getUserProfileInfo() {
    return getAuthenticatedEmail()
            .flatMap(email -> userOutPort.findByEmail(email)
                    .switchIfEmpty(Mono.error(new RuntimeException(USER_NOT_FOUND)))
            );
  }

  @Override
  public Mono<UserModel> updateUserProfile(UserModel userModel) {
    return getAuthenticatedEmail()
            .flatMap(email -> userOutPort.findByEmail(email)
                    .switchIfEmpty(Mono.error(new RuntimeException(USER_NOT_FOUND)))
                    .flatMap(existingUser -> validateProfileUpdate(existingUser, userModel))
                    .flatMap(userOutPort::save)
            );
  }

  @Override
  public Mono<UserModel> registerAdmin(UserModel userModel) {
    return getAuthenticatedEmail()
            .flatMap(userOutPort::findByEmail)
            .switchIfEmpty(Mono.error(new RuntimeException(USER_AUTHENTICATED_NOT_FOUND)))
            .flatMap(authUser -> rolOutPort.findById(authUser.getRoleId())
                    .filter(rol -> "ROLE_ADMIN".equals(rol.getName()))
                    .switchIfEmpty(Mono.error(new RuntimeException(USER_PERMISSION_DENIED))))
            .then(rolOutPort.findByName(ADMIN_ROLE_NAME)
                    .map(RolModel::getId))
            .flatMap(adminRoleId -> Mono.just(userModel)
                    .flatMap(user -> Mono.when(
                            UserValidation.validateFirstName(user.getFirstName()),
                            UserValidation.validateEmail(user.getEmail()),
                            UserValidation.validatePhone(user.getPhone()),
                            UserValidation.validatePassword(user.getPassword())
                    ).thenReturn(user))
                    .flatMap(user -> Mono.when(
                            validateEmailDoesNotExist(user.getEmail()),
                            validateDniDoesNotExist(user.getDni())
                    ).thenReturn(user))
                    .map(user -> {
                      user.setRoleId(adminRoleId);
                      user.setIsActive(true);
                      user.setCreatedAt(LocalDateTime.now());
                      user.setUpdatedAt(LocalDateTime.now());
                      return user;
                    })
                    .flatMap(this::encodePassword)
                    .flatMap(userOutPort::save));
  }

  @Override
  public Flux<UserModel> listAllUsers(Optional<String> email, Optional<String> dni, Optional<String> name, int page) {
    Flux<UserModel> usersFlux = userOutPort.findAll();

    if (email.isPresent()) {
      usersFlux = usersFlux.filter(user -> user.getEmail().equalsIgnoreCase(email.get()));
    }
    if (dni.isPresent()) {
      usersFlux = usersFlux.filter(user -> user.getDni().equals(dni.get()));
    }
    if (name.isPresent()) {
      usersFlux = usersFlux.filter(user -> user.getFirstName().equalsIgnoreCase(name.get()));
    }

    return usersFlux
            .skip(page * 50)
            .take(50);
  }

  @Override
  public Mono<UserModel> registerAdminLogistic(UserModel adminLogistic) {
    return getAuthenticatedEmail()
            .flatMap(userOutPort::findByEmail)
            .switchIfEmpty(Mono.error(new RuntimeException(USER_AUTHENTICATED_NOT_FOUND)))
            .flatMap(authUser -> rolOutPort.findById(authUser.getRoleId())
                    .filter(rol -> "ROLE_ADMIN".equals(rol.getName()))
                    .switchIfEmpty(Mono.error(new RuntimeException(USER_PERMISSION_DENIED))))
            .then(rolOutPort.findByName(ADMIN_LOGISTIC_ROLE_NAME)
                    .map(RolModel::getId))
            .flatMap(logisticRoleId -> Mono.just(adminLogistic)
                    .flatMap(user -> Mono.when(
                            UserValidation.validateFirstName(user.getFirstName()),
                            UserValidation.validateEmail(user.getEmail()),
                            UserValidation.validatePhone(user.getPhone()),
                            UserValidation.validatePassword(user.getPassword())
                    ).thenReturn(user))
                    .flatMap(user -> Mono.when(
                            validateEmailDoesNotExist(user.getEmail()),
                            validateDniDoesNotExist(user.getDni())
                    ).thenReturn(user))
                    .map(user -> {
                      user.setRoleId(logisticRoleId);
                      user.setIsActive(true);
                      user.setCreatedAt(LocalDateTime.now());
                      user.setUpdatedAt(LocalDateTime.now());
                      return user;
                    })
                    .flatMap(this::encodePassword)
                    .flatMap(userOutPort::save));
  }

  @Override
  public Mono<UserModel> getAdminLogistic(Long id) {
    return userOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(USER_NOT_FOUND)))
            .flatMap(user ->
                    rolOutPort.findById(user.getRoleId())
                            .switchIfEmpty(Mono.error(new RuntimeException(USER_ROLE_NOT_FOUND)))
                            .flatMap(rol -> {
                              if (!ADMIN_LOGISTIC_ROLE_NAME.equals(rol.getName())) {
                                return Mono.error(new RuntimeException(NOT_ADMIN_LOGISTIC));
                              }
                              return Mono.just(user);
                            })
            );
  }

  private Mono<UserModel> validateProfileUpdate(UserModel existingUser, UserModel updatedUser) {
    return Mono.when(
                    UserValidation.validateFirstName(updatedUser.getFirstName()),
                    UserValidation.validatePhone(updatedUser.getPhone())
            )
            .thenReturn(existingUser)
            .map(user -> {
              user.setFirstName(updatedUser.getFirstName());
              user.setLastName(updatedUser.getLastName());
              user.setPhone(updatedUser.getPhone());
              user.setUpdatedAt(LocalDateTime.now());
              return user;
            });
  }

  private Mono<String> getAuthenticatedEmail() {
    return ReactiveSecurityContextHolder.getContext()
            .map(context -> {
              Authentication authentication = context.getAuthentication();
              return authentication.getName();
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

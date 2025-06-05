package com.arka.microservice.customer_ms.domain.ports.in;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.FileChannel;
import java.util.Optional;

public interface IUserInPort {
  Mono<UserModel> registerUser(UserModel userModel);
  Mono<UserModel> getUserProfileInfo();
  Mono<UserModel> updateUserProfile(UserModel userModel);
  Mono<UserModel> registerAdmin(UserModel admin);
  Flux<UserModel> listAllUsers(Optional<String> email, Optional<String> dni, Optional<String> name, int page);
  Mono<UserModel> registerAdminLogistic(UserModel adminLogistic);
  Mono<UserModel> getAdminLogistic(Long id);
}

package com.arka.microservice.customer_ms.domain.ports.in;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import reactor.core.publisher.Mono;

import java.nio.channels.FileChannel;

public interface IUserInPort {
  Mono<UserModel> registerUser(UserModel userModel);
  Mono<UserModel> getUserProfileInfo();
}

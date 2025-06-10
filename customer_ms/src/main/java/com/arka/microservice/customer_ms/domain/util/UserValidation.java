package com.arka.microservice.customer_ms.domain.util;

import com.arka.microservice.customer_ms.domain.exception.BadRequestException;
import reactor.core.publisher.Mono;

import static com.arka.microservice.customer_ms.domain.util.UserConstants.*;

public class UserValidation {

  public static Mono<Void> validateFirstName(String firstName) {
    if(firstName == null || firstName.isBlank()){
      return Mono.error(new BadRequestException(FIRST_NAME_REQUIRED));
    }
    if(firstName.length() > FIRST_NAME_LENGTH){
      return Mono.error(new BadRequestException(FIRST_NAME_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validatePassword(String password) {
    if(password == null || password.isBlank()){
      return Mono.error(new BadRequestException(PASSWORD_REQUIRED));
    }
    if(password.length() < PASSWORD_MIN_LENGTH){
      return Mono.error(new BadRequestException(PASSWORD_MIN_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateEmail(String email) {
    if(email == null || email.isBlank()){
      return Mono.error(new BadRequestException(EMAIL_REQUIRED));
    }
    if(!email.matches(EMAIL_REGEX)){
      return Mono.error(new BadRequestException(EMAIL_FORMAT_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validatePhone(String phone) {
    if(phone == null || phone.isBlank()){
      return Mono.error(new BadRequestException(PHONE_REQUIRED));
    }
    if(!phone.matches(PHONE_REGEX)){
      return Mono.error(new BadRequestException(PHONE_FORMAT_ERROR));
    }
    return Mono.empty();
  }
}

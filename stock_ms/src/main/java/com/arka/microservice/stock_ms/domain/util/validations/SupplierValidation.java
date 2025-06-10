package com.arka.microservice.stock_ms.domain.util.validations;

import reactor.core.publisher.Mono;

import static com.arka.microservice.stock_ms.domain.util.SupplierConstant.*;

public class SupplierValidation {

  public static Mono<Void> validateName(String name) {
    if(name == null || name.isBlank()){
      return Mono.error(new RuntimeException(SUPPLIER_NAME_REQUIRED));
    }
    if(name.length() > SUPPLIER_NAME_LENGTH){
      return Mono.error(new RuntimeException(SUPPLIER_NAME_LENGTH_ERROR));
    }
    return Mono.empty();
  }
  public static Mono<Void> validateEmail(String email) {
    if(email == null || email.isBlank()){
      return Mono.error(new RuntimeException(SUPPLIER_EMAIL_REQUIRED));
    }
    if(!email.matches(EMAIL_REGEX)){
      return Mono.error(new RuntimeException(EMAIL_FORMAT_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validatePhone(String phone) {
    if(phone == null || phone.isBlank()){
      return Mono.error(new RuntimeException(SUPPLIER_PHONE_REQUIRED));
    }
    if(!phone.matches(PHONE_REGEX)){
      return Mono.error(new RuntimeException(PHONE_FORMAT_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateAddress(String address) {
    if(address == null || address.isBlank()){
      return Mono.error(new RuntimeException(SUPPLIER_ADDRESS_REQUIRED));
    }
    if(address.length() > SUPPLIER_ADDRESS_LENGTH){
      return Mono.error(new RuntimeException(SUPPLIER_ADDRESS_LENGTH_ERROR));
    }
    return Mono.empty();
  }
}

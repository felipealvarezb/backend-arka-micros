package com.arka.microservice.stock_ms.domain.util.validations;

import reactor.core.publisher.Mono;

import static com.arka.microservice.stock_ms.domain.util.AttributeConstant.*;

public class AttributeValidation {

  public static Mono<Void> validateName(String name) {
    if(name == null || name.isBlank()){
      return Mono.error(new RuntimeException(ATTRIBUTE_NAME_REQUIRED));
    }
    if(name.length() > ATTRIBUTE_NAME_LENGTH){
      return Mono.error(new RuntimeException(ATTRIBUTE_NAME_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateDescription(String description) {
    if(description == null || description.isBlank()){
      return Mono.error(new RuntimeException(ATTRIBUTE_DESCRIPTION_REQUIRED));
    }
    if(description.length() > ATTRIBUTE_DESCRIPTION_LENGTH){
      return Mono.error(new RuntimeException(ATTRIBUTE_DESCRIPTION_LENGTH_ERROR));
    }
    return Mono.empty();
  }
}

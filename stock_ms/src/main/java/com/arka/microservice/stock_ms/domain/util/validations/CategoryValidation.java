package com.arka.microservice.stock_ms.domain.util.validations;

import reactor.core.publisher.Mono;

import static com.arka.microservice.stock_ms.domain.util.CategoryConstant.*;

public class CategoryValidation {

  public static Mono<Void> validateName(String name) {
    if(name == null || name.isBlank()){
      return Mono.error(new RuntimeException(CATEGORY_NAME_REQUIRED));
    }
    if(name.length() > CATEGORY_NAME_LENGTH){
      return Mono.error(new RuntimeException(CATEGORY_NAME_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateDescription(String description) {
    if(description == null || description.isBlank()){
      return Mono.error(new RuntimeException(CATEGORY_DESCRIPTION_REQUIRED));
    }
    if(description.length() > CATEGORY_NAME_LENGTH){
      return Mono.error(new RuntimeException(CATEGORY_DESCRIPTION_LENGTH_ERROR));
    }
    return Mono.empty();
  }
}

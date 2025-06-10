package com.arka.microservice.stock_ms.domain.util.validations;

import com.arka.microservice.stock_ms.domain.exception.BadRequestException;
import reactor.core.publisher.Mono;

import static com.arka.microservice.stock_ms.domain.util.BrandConstant.*;

public class BrandValidation {

  public static Mono<Void> validateName(String name) {
    if(name == null || name.isBlank()){
      return Mono.error(new BadRequestException(BRAND_NAME_REQUIRED));
    }
    if(name.length() > BRAND_NAME_LENGTH){
      return Mono.error(new BadRequestException(BRAND_NAME_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateDescription(String description) {
    if(description == null || description.isBlank()){
      return Mono.error(new BadRequestException(BRAND_DESCRIPTION_REQUIRED));
    }
    if(description.length() > BRAND_DESCRIPTION_LENGTH){
      return Mono.error(new BadRequestException(BRAND_DESCRIPTION_LENGTH_ERROR));
    }
    return Mono.empty();
  }
}

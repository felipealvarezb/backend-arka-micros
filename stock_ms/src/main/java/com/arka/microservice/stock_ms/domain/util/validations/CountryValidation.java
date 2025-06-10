package com.arka.microservice.stock_ms.domain.util.validations;

import com.arka.microservice.stock_ms.domain.exception.BadRequestException;
import reactor.core.publisher.Mono;

import static com.arka.microservice.stock_ms.domain.util.CountryConstant.*;

public class CountryValidation {

  public static Mono<Void> validateName(String name) {
    if(name == null || name.isBlank()){
      return Mono.error(new BadRequestException(COUNTRY_NAME_REQUIRED));
    }
    if(name.length() > COUNTRY_NAME_LENGTH){
      return Mono.error(new BadRequestException(COUNTRY_NAME_LENGTH_ERROR));
    }
    return Mono.empty();
  }
}

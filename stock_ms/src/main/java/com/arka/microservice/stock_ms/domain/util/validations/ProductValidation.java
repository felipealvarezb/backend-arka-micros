package com.arka.microservice.stock_ms.domain.util.validations;

import com.arka.microservice.stock_ms.domain.exception.BadRequestException;
import reactor.core.publisher.Mono;

import static com.arka.microservice.stock_ms.domain.util.ProductConstant.*;

public class ProductValidation {

  public static Mono<Void> validateName(String name) {
    if(name == null || name.isBlank()){
      return Mono.error(new BadRequestException(PRODUCT_NAME_REQUIRED));
    }
    if(name.length() > PRODUCT_NAME_MAX_LENGTH){
      return Mono.error(new BadRequestException(PRODUCT_NAME_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateDescription(String description) {
    if(description == null || description.isBlank()){
      return Mono.error(new BadRequestException(PRODUCT_DESCRIPTION_REQUIRED));
    }
    if(description.length() > PRODUCT_DESCRIPTION_MAX_LENGTH){
      return Mono.error(new BadRequestException(PRODUCT_DESCRIPTION_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validatePrice(Double price) {
    if(price == null){
      return Mono.error(new BadRequestException(PRODUCT_PRICE_REQUIRED));
    }
    if(price <= PRODUCT_PRICE_MIN_VALUE) {
      return Mono.error(new BadRequestException(PRODUCT_PRICE_PRICE_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateImageUrl(String imageUrl) {
    if(imageUrl == null || imageUrl.isBlank()){
      return Mono.error(new BadRequestException(PRODUCT_IMAGE_URL_REQUIRED));
    }
    if(imageUrl.length() > PRODUCT_IMAGE_URL_MAX_LENGTH){
      return Mono.error(new BadRequestException(PRODUCT_IMAGE_URL_LENGTH_ERROR));
    }
    return Mono.empty();
  }
}

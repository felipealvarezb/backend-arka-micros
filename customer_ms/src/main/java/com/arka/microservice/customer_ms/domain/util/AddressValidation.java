package com.arka.microservice.customer_ms.domain.util;

import com.arka.microservice.customer_ms.domain.exception.BadRequestException;
import reactor.core.publisher.Mono;

import static com.arka.microservice.customer_ms.domain.util.AddressConstants.*;

public class AddressValidation {

  public static Mono<Void> validateCity(String city) {
    if(city == null || city.isBlank()){
      return Mono.error(new BadRequestException(CITY_REQUIRED));
    }
    if(city.length() > CITY_LENGTH){
      return Mono.error(new BadRequestException(CITY_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateCountry(String country) {
    if(country == null || country.isBlank()){
      return Mono.error(new BadRequestException(COUNTRY_REQUIRED));
    }
    if(country.length() > COUNTRY_LENGTH){
      return Mono.error(new BadRequestException(COUNTRY_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateZipCode(String postalCode) {
    if(postalCode == null || postalCode.isBlank()){
      return Mono.error(new BadRequestException(POSTAL_CODE_REQUIRED));
    }
    if(!postalCode.matches(POSTAL_CODE_REGEX)){
      return Mono.error(new BadRequestException(POSTAL_CODE_FORMAT_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateAddress(String address) {
    if(address == null || address.isBlank()){
      return Mono.error(new BadRequestException(ADDRESS_REQUIRED));
    }
    if(address.length() > ADDRESS_LENGTH){
      return Mono.error(new BadRequestException(ADDRESS_LENGTH_ERROR));
    }
    return Mono.empty();
  }

  public static Mono<Void> validateState(String state) {
    if(state == null || state.isBlank()){
      return Mono.error(new BadRequestException(STATE_REQUIRED));
    }
    if(state.length() > STATE_LENGTH){
      return Mono.error(new BadRequestException(STATE_LENGTH_ERROR));
    }
    return Mono.empty();
  }
}

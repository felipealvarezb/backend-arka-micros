package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.ICountryInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.CountryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.CountryResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.ICountryRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
public class CountryController {

  private final ICountryInPort countryInPort;
  private final ICountryRestMapper countryRestMapper;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<CountryResponseDTO> createBrand(@RequestBody CountryRequestDTO countryRequestDTO){
    return countryInPort.createCountry(countryRestMapper.countryRequestToModel(countryRequestDTO))
            .map(countryRestMapper::countryModelToResponseDTO);
  }

  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<CountryResponseDTO> updateBrand(@PathVariable Long id, @RequestBody CountryRequestDTO countryRequestDTO){
    return countryInPort.updateCountry(id, countryRestMapper.countryRequestToModel(countryRequestDTO))
            .map(countryRestMapper::countryModelToResponseDTO);
  }
}

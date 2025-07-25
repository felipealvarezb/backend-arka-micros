package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.ICountryInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.CountryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.CountryResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.ICountryRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/country")
@RequiredArgsConstructor
@Tag(name = "Country", description = "Gestión de paises")
public class CountryController {

  private final ICountryInPort countryInPort;
  private final ICountryRestMapper countryRestMapper;

  @Operation(summary = "Create Country", description = "Create and save an existing Country")
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<CountryResponseDTO> createCountry(@RequestBody CountryRequestDTO countryRequestDTO){
    return countryInPort.createCountry(countryRestMapper.countryRequestToModel(countryRequestDTO))
            .map(countryRestMapper::countryModelToResponseDTO);
  }

  @Operation(summary = "Update Country", description = "Update and save an existing Country")
  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<CountryResponseDTO> updateCountry(@PathVariable Long id, @RequestBody CountryRequestDTO countryRequestDTO){
    return countryInPort.updateCountry(id, countryRestMapper.countryRequestToModel(countryRequestDTO))
            .map(countryRestMapper::countryModelToResponseDTO);
  }
}

package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.IBrandInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.BrandRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.BrandResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IBrandRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {

  private final IBrandInPort brandInPort;
  private final IBrandRestMapper brandRestMapper;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<BrandResponseDTO> createBrand(@RequestBody BrandRequestDTO brandRequestDTO){
    return brandInPort.createBrand(brandRestMapper.brandRequestToModel(brandRequestDTO))
            .map(brandRestMapper::brandModelToResponseDTO);
  }

  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<BrandResponseDTO> updateBrand(@PathVariable Long id, @RequestBody BrandRequestDTO brandRequestDTO){
    return brandInPort.updateBrand(id, brandRestMapper.brandRequestToModel(brandRequestDTO))
            .map(brandRestMapper::brandModelToResponseDTO);
  }


  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<BrandResponseDTO> getAllBrands(){
    return brandInPort.getAllBrands()
            .map(brandRestMapper::brandModelToResponseDTO);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteBrand(@PathVariable Long id) {
    return brandInPort.deleteBrand(id);
  }
}

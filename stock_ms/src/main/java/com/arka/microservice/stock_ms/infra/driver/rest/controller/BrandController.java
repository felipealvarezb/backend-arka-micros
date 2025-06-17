package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.IBrandInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.BrandRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.BrandResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IBrandRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@Tag(name = "Brand", description = "Gestión de marcas")
public class BrandController {

  private final IBrandInPort brandInPort;
  private final IBrandRestMapper brandRestMapper;

  @Operation(summary = "Create Brand", description = "Create and save an existing Brand")
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<BrandResponseDTO> createBrand(@RequestBody BrandRequestDTO brandRequestDTO){
    return brandInPort.createBrand(brandRestMapper.brandRequestToModel(brandRequestDTO))
            .map(brandRestMapper::brandModelToResponseDTO);
  }

  @Operation(summary = "Update Brand", description = "Update and save an existing Brand")
  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<BrandResponseDTO> updateBrand(@PathVariable Long id, @RequestBody BrandRequestDTO brandRequestDTO){
    return brandInPort.updateBrand(id, brandRestMapper.brandRequestToModel(brandRequestDTO))
            .map(brandRestMapper::brandModelToResponseDTO);
  }

  @Operation(summary = "List all Brands", description = "List all Brands")
  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<BrandResponseDTO> getAllBrands(){
    return brandInPort.getAllBrands()
            .map(brandRestMapper::brandModelToResponseDTO);
  }

  @Operation(summary = "Delete Brand", description = "Delete an existing Brand")
  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteBrand(@PathVariable Long id) {
    return brandInPort.deleteBrand(id);
  }
}

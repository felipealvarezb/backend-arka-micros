package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.ISupplierInPort;
import com.arka.microservice.stock_ms.domain.ports.out.ISupplierOutPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AttributeRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.SupplierRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.AttributeResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.SupplierResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.ISupplierRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
public class SupplierController {

  private final ISupplierInPort supplierInPort;
  private final ISupplierRestMapper supplierRestMapper;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<SupplierResponseDTO> createSupplier(@RequestBody SupplierRequestDTO supplierRequestDTO){
    return supplierInPort.createSupplier(supplierRestMapper.supplierRequestToModel(supplierRequestDTO))
            .map(supplierRestMapper::supplierModelToResponseDTO);
  }

  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<SupplierResponseDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierRequestDTO supplierRequestDTO){
    return supplierInPort.updateSupplier(id, supplierRestMapper.supplierRequestToModel(supplierRequestDTO))
            .map(supplierRestMapper::supplierModelToResponseDTO);
  }


  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<SupplierResponseDTO> getAllSuppliers(){
    return supplierInPort.getAllSuppliers()
            .map(supplierRestMapper::supplierModelToResponseDTO);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteSupplier(@PathVariable Long id) {
    return supplierInPort.deleteSupplier(id);
  }
}

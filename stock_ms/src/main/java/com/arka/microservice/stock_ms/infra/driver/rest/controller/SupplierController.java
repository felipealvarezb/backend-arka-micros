package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.ISupplierInPort;
import com.arka.microservice.stock_ms.domain.ports.out.ISupplierOutPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AttributeRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.SupplierRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.AttributeResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.SupplierResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.ISupplierRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/supplier")
@RequiredArgsConstructor
@Tag(name = "Supplier", description = "Gestión de proveedores")
public class SupplierController {

  private final ISupplierInPort supplierInPort;
  private final ISupplierRestMapper supplierRestMapper;

  @Operation(summary = "Create Supplier", description = "Create and save a supplier")
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<SupplierResponseDTO> createSupplier(@RequestBody SupplierRequestDTO supplierRequestDTO){
    return supplierInPort.createSupplier(supplierRestMapper.supplierRequestToModel(supplierRequestDTO))
            .map(supplierRestMapper::supplierModelToResponseDTO);
  }

  @Operation(summary = "Update Supplier", description = "Update and save a supplier")
  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<SupplierResponseDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierRequestDTO supplierRequestDTO){
    return supplierInPort.updateSupplier(id, supplierRestMapper.supplierRequestToModel(supplierRequestDTO))
            .map(supplierRestMapper::supplierModelToResponseDTO);
  }

  @Operation(summary = "Get All Suppliers", description = "Get all existing suppliers")
  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<SupplierResponseDTO> getAllSuppliers(){
    return supplierInPort.getAllSuppliers()
            .map(supplierRestMapper::supplierModelToResponseDTO);
  }

  @Operation(summary = "Delete Supplier", description = "Delete Supplier By Id")
  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteSupplier(@PathVariable Long id) {
    return supplierInPort.deleteSupplier(id);
  }
}

package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.IAttributeInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AttributeRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.AttributeResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IAttributeRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/attribute")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Gestión de atributos")
public class AttributeController {

  private final IAttributeInPort attributeInPort;
  private final IAttributeRestMapper attributeRestMapper;

  @Operation(summary = "Create Attribute", description = "Create and save an attribute")
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<AttributeResponseDTO> createAttribute(@RequestBody AttributeRequestDTO attributeRequestDTO){
    return attributeInPort.createAttribute(attributeRestMapper.attributeRequestToModel(attributeRequestDTO))
            .map(attributeRestMapper::attributeModelToResponseDTO);
  }

  @Operation(summary = "Update an Attribute", description = "Update and save an existing attribute")
  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<AttributeResponseDTO> updateAttribute(@PathVariable Long id, @RequestBody AttributeRequestDTO attributeRequestDTO){
    return attributeInPort.updateAttribute(id, attributeRestMapper.attributeRequestToModel(attributeRequestDTO))
            .map(attributeRestMapper::attributeModelToResponseDTO);
  }

  @Operation(summary = "List all Attributes", description = "List all attributes")
  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<AttributeResponseDTO> getAllAttributes(){
    return attributeInPort.getAllAttributes()
            .map(attributeRestMapper::attributeModelToResponseDTO);
  }

  @Operation(summary = "Delete Attribute", description = "Delete an existing attribute")
  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteAttribute(@PathVariable Long id) {
    return attributeInPort.deleteAttribute(id);
  }
}

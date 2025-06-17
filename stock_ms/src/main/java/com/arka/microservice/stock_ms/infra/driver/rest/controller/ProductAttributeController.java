package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.IProductAttributeInPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.IProductAttributeMapper;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AddProductAttributeRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.ProductRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.ProductAttributeResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.ProductResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IProductAttributeRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Tag(name = "Product Attribute", description = "Gestión de atributos de productos")
public class ProductAttributeController {

  private final IProductAttributeInPort productAttributeInPort;
  private final IProductAttributeRestMapper productAttributeRestMapper;

  @Operation(summary = "Add Attribute to Product", description = "Create and assign a existing attribute to a existing product")
  @PostMapping("{productId}/attribute/{attributeId}/add")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ProductAttributeResponseDTO> addAttributeToProduct(
          @PathVariable Long productId,
          @PathVariable Long attributeId,
          @RequestBody AddProductAttributeRequestDTO requestDTO
  ){
    return productAttributeInPort.addAttributeToProduct(productId, attributeId, productAttributeRestMapper.requestDtoToModel(requestDTO))
            .map(productAttributeRestMapper::modelToResponseDto);
  }

  @Operation(summary = "Remove Attribute to Product", description = "Remove an attribute from product")
  @DeleteMapping("{productId}/attribute/{attributeId}/remove")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ProductAttributeResponseDTO> removeAttributeToProduct(
          @PathVariable Long productId,
          @PathVariable Long attributeId,
          @RequestBody AddProductAttributeRequestDTO requestDTO
  ){
    return productAttributeInPort.removeAttributeToProduct(productId, attributeId, productAttributeRestMapper.requestDtoToModel(requestDTO))
            .map(productAttributeRestMapper::modelToResponseDto);
  }

  @Operation(summary = "Update an Attribute of Product", description = "Update the value of an attribute")
  @PostMapping("{productId}/attribute/{attributeId}/update")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ProductAttributeResponseDTO> updateAttributeToProduct(
          @PathVariable Long productId,
          @PathVariable Long attributeId,
          @RequestBody AddProductAttributeRequestDTO requestDTO
  ){
    return productAttributeInPort.updateAttributeToProduct(productId, attributeId, productAttributeRestMapper.requestDtoToModel(requestDTO))
            .map(productAttributeRestMapper::modelToResponseDto);
  }


  @Operation(summary = "Get Product Attributes", description = "Get the attributes of a product")
  @GetMapping("{productId}/attributes")
  @ResponseStatus(HttpStatus.OK)
  public Flux<ProductAttributeResponseDTO> getProductAttributes( @PathVariable Long productId){
    return productAttributeInPort.getProductAttributes(productId)
            .map(productAttributeRestMapper::modelToResponseDto);
  }

}

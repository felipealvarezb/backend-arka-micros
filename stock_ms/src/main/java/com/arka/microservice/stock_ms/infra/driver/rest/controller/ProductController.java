package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.IProductInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.ProductRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.ProductResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IProductRestMapper;
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
@Tag(name = "Product", description = "Gestión de productos")
public class ProductController {

  private final IProductInPort productInPort;
  private final IProductRestMapper  productRestMapper;

  @Operation(summary = "Create Product", description = "Create and save a product")
  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
    return productInPort.createProduct(productRestMapper.productRequestToModel(productRequestDTO))
            .map(productRestMapper::productModelToResponse);
  }

  @Operation(summary = "Update Product", description = "Update and save a product")
  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO){
    return productInPort.updateProduct(id, productRestMapper.productRequestToModel(productRequestDTO))
            .map(productRestMapper::productModelToResponse);
  }

  @Operation(summary = "Get all Products", description = "Get all products")
  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<ProductResponseDTO> getAllProducts(){
    return productInPort.getAllProducts()
            .map(productRestMapper::productModelToResponse);
  }

  @Operation(summary = "Get Product by Id", description = "Get product by id")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ProductResponseDTO> getProduct(@PathVariable Long id){
    return productInPort.getProduct(id)
            .map(productRestMapper::productModelToResponse);
  }

  @Operation(summary = "Delete Product", description = "Delete product by id")
  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteProduct(@PathVariable Long id) {
    return productInPort.deleteProduct(id);
  }
}

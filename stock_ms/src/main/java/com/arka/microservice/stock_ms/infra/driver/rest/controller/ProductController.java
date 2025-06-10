package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.IProductInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.ProductRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.ProductResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IProductRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

  private final IProductInPort productInPort;
  private final IProductRestMapper  productRestMapper;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO){
    return productInPort.createProduct(productRestMapper.productRequestToModel(productRequestDTO))
            .map(productRestMapper::productModelToResponse);
  }

  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO){
    return productInPort.updateProduct(id, productRestMapper.productRequestToModel(productRequestDTO))
            .map(productRestMapper::productModelToResponse);
  }


  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<ProductResponseDTO> getAllProducts(){
    return productInPort.getAllProducts()
            .map(productRestMapper::productModelToResponse);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ProductResponseDTO> getProduct(@PathVariable Long id){
    return productInPort.getProduct(id)
            .map(productRestMapper::productModelToResponse);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteProduct(@PathVariable Long id) {
    return productInPort.deleteProduct(id);
  }
}

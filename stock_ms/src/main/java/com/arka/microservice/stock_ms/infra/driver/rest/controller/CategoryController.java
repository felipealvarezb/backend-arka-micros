package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.ICategoryInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.BrandRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.CategoryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.BrandResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.CategoryResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.ICategoryRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

  private final ICategoryInPort categoryInPort;
  private final ICategoryRestMapper categoryRestMapper;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
    return categoryInPort.createCategory(categoryRestMapper.categoryRequestToModel(categoryRequestDTO))
            .map(categoryRestMapper::categoryModelToResponseDTO);
  }

  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO){
    return categoryInPort.updateCategory(id, categoryRestMapper.categoryRequestToModel(categoryRequestDTO))
            .map(categoryRestMapper::categoryModelToResponseDTO);
  }


  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<CategoryResponseDTO> getAllCategories(){
    return categoryInPort.getAllCategories()
            .map(categoryRestMapper::categoryModelToResponseDTO);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteCategory(@PathVariable Long id) {
    return categoryInPort.deleteCategory(id);
  }
}

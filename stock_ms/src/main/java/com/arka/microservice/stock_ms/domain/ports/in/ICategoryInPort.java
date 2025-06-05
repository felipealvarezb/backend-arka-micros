package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.CategoryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICategoryInPort {

  Mono<CategoryModel> createCategory(CategoryModel categoryModel);
  Mono<CategoryModel> updateCategory(Long id, CategoryModel categoryModel);
  Flux<CategoryModel> getAllCategories();
  Mono<String> deleteCategory(Long id);
}

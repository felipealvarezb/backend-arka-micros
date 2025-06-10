package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.model.CategoryModel;
import com.arka.microservice.stock_ms.domain.ports.in.ICategoryInPort;
import com.arka.microservice.stock_ms.domain.ports.out.ICategoryOutPort;
import com.arka.microservice.stock_ms.domain.util.validations.CategoryValidation;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.ICategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.stock_ms.domain.util.CategoryConstant.*;

@Component
@RequiredArgsConstructor
public class CategoryUseCaseImpl implements ICategoryInPort {

  private final ICategoryOutPort categoryOutPort;

  @Override
  public Mono<CategoryModel> createCategory(CategoryModel categoryModel) {
    return Mono.when(
                    CategoryValidation.validateName(categoryModel.getName()),
                    CategoryValidation.validateDescription(categoryModel.getDescription())
            )
            .then(categoryOutPort.findByName(categoryModel.getName())
                    .flatMap(existingCategory -> existingCategory != null
                            ? Mono.error(new RuntimeException(CATEGORY_NAME_ALREADY_EXISTS))
                            : Mono.just(categoryModel))
                    .switchIfEmpty(Mono.defer(() -> {
                      categoryModel.setName(categoryModel.getName().toLowerCase());
                      categoryModel.setCreatedAt(LocalDateTime.now());
                      categoryModel.setUpdatedAt(LocalDateTime.now());
                      return categoryOutPort.save(categoryModel);
                    }))
            );
  }


  @Override
  public Mono<CategoryModel> updateCategory(Long id, CategoryModel categoryModel) {
    return categoryOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(CATEGORY_NOT_FOUND)))
            .flatMap(existingCategory ->
                    Mono.when(
                            CategoryValidation.validateName(categoryModel.getName()),
                            CategoryValidation.validateDescription(categoryModel.getDescription())
                    ).thenReturn(existingCategory))
            .flatMap(existingCategory -> categoryOutPort.findByName(categoryModel.getName())
                    .filter(category -> !category.getId().equals(id))
                    .flatMap(category -> existingCategory != null
                            ? Mono.error(new RuntimeException(CATEGORY_NAME_ALREADY_EXISTS))
                            : Mono.just(categoryModel))
                    .switchIfEmpty(Mono.defer(() -> {
                      existingCategory.setName(categoryModel.getName().toLowerCase());
                      existingCategory.setDescription(categoryModel.getDescription());
                      existingCategory.setUpdatedAt(LocalDateTime.now());
                      return categoryOutPort.save(existingCategory);
                    }))
            );
  }

  @Override
  public Flux<CategoryModel> getAllCategories() {
    return categoryOutPort.findAll()
            .switchIfEmpty(Flux.empty());
  }

  @Override
  public Mono<String> deleteCategory(Long id) {
    return categoryOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(CATEGORY_NOT_FOUND)))
            .flatMap(category -> categoryOutPort.delete(id)
                    .thenReturn(CATEGORY_DELETED_SUCCESSFULLY));
  }
}

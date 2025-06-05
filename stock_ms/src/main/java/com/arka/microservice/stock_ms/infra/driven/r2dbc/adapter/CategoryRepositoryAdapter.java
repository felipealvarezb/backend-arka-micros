package com.arka.microservice.stock_ms.infra.driven.r2dbc.adapter;

import com.arka.microservice.stock_ms.domain.model.CategoryModel;
import com.arka.microservice.stock_ms.domain.ports.out.ICategoryOutPort;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.ICategoryMapper;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.repository.ICategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements ICategoryOutPort {

  private final ICategoryEntityRepository categoryEntityRepository;
  private final ICategoryMapper categoryMapper;

  @Override
  public Mono<CategoryModel> save(CategoryModel category) {
    return categoryEntityRepository.save(categoryMapper.modelToEntity(category))
            .map(categoryMapper::entityToModel);
  }

  @Override
  public Mono<CategoryModel> findByName(String name) {
    return categoryEntityRepository.findByName(name)
            .map(categoryMapper::entityToModel);
  }

  @Override
  public Mono<CategoryModel> findById(Long id) {
    return categoryEntityRepository.findById(id)
            .map(categoryMapper::entityToModel);
  }

  @Override
  public Flux<CategoryModel> findAll() {
    return categoryEntityRepository.findAll()
            .map(categoryMapper::entityToModel);
  }

  @Override
  public Mono<Void> delete(Long id) {
    return categoryEntityRepository.deleteById(id);
  }
}

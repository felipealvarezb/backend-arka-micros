package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.model.BrandModel;
import com.arka.microservice.stock_ms.domain.ports.in.IBrandInPort;
import com.arka.microservice.stock_ms.domain.ports.out.IBrandOutPort;
import com.arka.microservice.stock_ms.domain.util.validations.BrandValidation;
import com.arka.microservice.stock_ms.domain.util.validations.CategoryValidation;
import com.arka.microservice.stock_ms.infra.driven.r2dbc.mapper.IBrandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.stock_ms.domain.util.BrandConstant.*;

@Component
@RequiredArgsConstructor
public class BrandUseCaseImpl implements IBrandInPort {

  private final IBrandOutPort brandOutPort;

  @Override
  public Mono<BrandModel> createBrand(BrandModel brandModel) {
    return Mono.when(
            BrandValidation.validateName(brandModel.getName()),
            BrandValidation.validateDescription(brandModel.getDescription())
          ).then(brandOutPort.findByName(brandModel.getName())
                    .flatMap(existingBrand -> existingBrand != null
                            ? Mono.error(new RuntimeException(BRAND_NAME_ALREADY_EXISTS))
                            : Mono.just(brandModel))
                    .switchIfEmpty(Mono.defer(() -> {
                      brandModel.setName(brandModel.getName().toLowerCase());
                      brandModel.setCreatedAt(LocalDateTime.now());
                      brandModel.setUpdatedAt(LocalDateTime.now());
                      return brandOutPort.save(brandModel);
                    }))
          );
  }

  @Override
  public Mono<BrandModel> updateBrand(Long id, BrandModel brandModel) {
    return brandOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(BRAND_NOT_FOUND)))
            .flatMap(existingBrand ->
                    Mono.when(
                            BrandValidation.validateName(brandModel.getName()),
                            BrandValidation.validateDescription(brandModel.getDescription())
                    ).thenReturn(existingBrand))
            .flatMap(existingBrand -> brandOutPort.findByName((brandModel.getName()))
                    .filter(category -> !category.getId().equals(id))
                    .flatMap(category -> existingBrand != null
                            ? Mono.error(new RuntimeException(BRAND_NAME_ALREADY_EXISTS))
                            : Mono.just(brandModel))
                    .switchIfEmpty(Mono.defer(() -> {
                      existingBrand.setName(brandModel.getName().toLowerCase());
                      existingBrand.setDescription(brandModel.getDescription());
                      existingBrand.setUpdatedAt(LocalDateTime.now());
                      return brandOutPort.save(existingBrand);
                    }))
            );
  }

  @Override
  public Flux<BrandModel> getAllBrands() {
    return brandOutPort.findAll()
            .switchIfEmpty(Flux.empty());
  }

  @Override
  public Mono<String> deleteBrand(Long id) {
    return brandOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(BRAND_NOT_FOUND)))
            .flatMap(brand -> brandOutPort.delete(id)
                    .thenReturn(BRAND_DELETED_SUCCESSFULLY));
  }
}

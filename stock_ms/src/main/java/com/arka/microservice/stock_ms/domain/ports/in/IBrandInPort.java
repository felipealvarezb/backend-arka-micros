package com.arka.microservice.stock_ms.domain.ports.in;

import com.arka.microservice.stock_ms.domain.model.BrandModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBrandInPort {

  Mono<BrandModel> createBrand(BrandModel brandModel);
  Mono<BrandModel> updateBrand(Long id, BrandModel brandModel);
  Flux<BrandModel> getAllBrands();
  Mono<String> deleteBrand(Long id);
}

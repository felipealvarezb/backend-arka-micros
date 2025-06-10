package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.model.ProductModel;
import com.arka.microservice.stock_ms.domain.ports.in.IProductInPort;
import com.arka.microservice.stock_ms.domain.ports.out.IBrandOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.ICategoryOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.IProductOutPort;
import com.arka.microservice.stock_ms.domain.util.validations.ProductValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.arka.microservice.stock_ms.domain.util.BrandConstant.BRAND_NOT_FOUND;
import static com.arka.microservice.stock_ms.domain.util.CategoryConstant.CATEGORY_NOT_FOUND;
import static com.arka.microservice.stock_ms.domain.util.ProductConstant.PRODUCT_DELETED_SUCCESSFULLY;
import static com.arka.microservice.stock_ms.domain.util.ProductConstant.PRODUCT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ProductUseCaseImpl implements IProductInPort {

  private final IProductOutPort productOutPort;
  private final IBrandOutPort brandOutPort;
  private final ICategoryOutPort categoryOutPort;

  @Override
  public Mono<ProductModel> createProduct(ProductModel productModel) {
    return Mono.just(productModel)
            .flatMap(product -> Mono.when(
                    ProductValidation.validateName(product.getName()),
                    ProductValidation.validateDescription(product.getDescription()),
                    ProductValidation.validateImageUrl(product.getImageUrl()),
                    ProductValidation.validatePrice(product.getPrice())
                    ).thenReturn(product))
            .flatMap(product ->
                    categoryOutPort.findById(product.getCategoryId())
                            .switchIfEmpty(Mono.error(new RuntimeException(CATEGORY_NOT_FOUND)))
                            .map(category -> {
                              product.setCategoryId(category.getId());
                              return product;
                            })
            )
            .flatMap(product ->
                    brandOutPort.findById(product.getBrandId())
                            .switchIfEmpty(Mono.error(new RuntimeException(BRAND_NOT_FOUND)))
                            .map(brand -> {
                              product.setBrandId(brand.getId());
                              return product;
                            })
            ).map(product -> {
              product.setName(product.getName().toLowerCase());
              product.setSku(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
              product.setCreatedAt(LocalDateTime.now());
              product.setUpdatedAt(LocalDateTime.now());
              product.setIsActive(true);
              return product;
            })
            .flatMap(productOutPort::save);
  }

  @Override
  public Mono<ProductModel> updateProduct(Long id, ProductModel productModel) {
    return productOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(PRODUCT_NOT_FOUND)))
            .flatMap(existingProduct ->
                    Mono.when(
                            ProductValidation.validateName(productModel.getName()),
                            ProductValidation.validateDescription(productModel.getDescription()),
                            ProductValidation.validateImageUrl(productModel.getImageUrl()),
                            ProductValidation.validatePrice(productModel.getPrice())
                    ).thenReturn(existingProduct)
            )
            .flatMap(existingProduct ->
                    categoryOutPort.findById(productModel.getCategoryId())
                            .switchIfEmpty(Mono.error(new RuntimeException(CATEGORY_NOT_FOUND)))
                            .map(category -> {
                              existingProduct.setCategoryId(category.getId());
                              return existingProduct;
                            })
            )
            .flatMap(existingProduct ->
                    brandOutPort.findById(productModel.getBrandId())
                            .switchIfEmpty(Mono.error(new RuntimeException(BRAND_NOT_FOUND)))
                            .map(brand -> {
                              existingProduct.setBrandId(brand.getId());
                              return existingProduct;
                            })
            )
            .map(existingProduct -> {
              existingProduct.setName(productModel.getName().toLowerCase());
              existingProduct.setDescription(productModel.getDescription());
              existingProduct.setImageUrl(productModel.getImageUrl());
              existingProduct.setPrice(productModel.getPrice());
              existingProduct.setIsActive(productModel.getIsActive());
              existingProduct.setUpdatedAt(LocalDateTime.now());
              return existingProduct;
            })
            .flatMap(productOutPort::save);
  }

  @Override
  public Mono<ProductModel> getProduct(Long id) {
    return productOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(PRODUCT_NOT_FOUND)));
  }

  @Override
  public Flux<ProductModel> getAllProducts() {
    return productOutPort.findAll()
            .switchIfEmpty(Flux.empty());
  }

  @Override
  public Mono<String> deleteProduct(Long id) {
    return productOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(PRODUCT_NOT_FOUND)))
            .flatMap(product -> productOutPort.delete(id)
                    .thenReturn(PRODUCT_DELETED_SUCCESSFULLY));
  }
}

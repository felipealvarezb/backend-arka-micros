package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.exception.NotFoundException;
import com.arka.microservice.stock_ms.domain.model.ProductAttributeModel;
import com.arka.microservice.stock_ms.domain.ports.in.IProductAttributeInPort;
import com.arka.microservice.stock_ms.domain.ports.out.IAttributeOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.IProductAttributeOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.IProductOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.stock_ms.domain.util.AttributeConstant.ATTRIBUTE_NOT_FOUND;
import static com.arka.microservice.stock_ms.domain.util.ProductConstant.PRODUCT_ATTRIBUTE_NOT_FOUND;
import static com.arka.microservice.stock_ms.domain.util.ProductConstant.PRODUCT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ProductAttributeUseCaseImpl implements IProductAttributeInPort {

  private final IProductAttributeOutPort productAttributeOutPort;
  private final IProductOutPort productOutPort;
  private final IAttributeOutPort attributeOutPort;


  @Override
  public Mono<ProductAttributeModel> addAttributeToProduct(Long productId, Long attributeId, ProductAttributeModel productAttributeModel) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMap(product ->
                    attributeOutPort.findById(attributeId)
                            .switchIfEmpty(Mono.error(new NotFoundException(ATTRIBUTE_NOT_FOUND)))
            )
            .flatMap(attribute -> {
              productAttributeModel.setProductId(productId);
              productAttributeModel.setAttributeId(attributeId);
              productAttributeModel.setCreatedAt(LocalDateTime.now());
              productAttributeModel.setUpdatedAt(LocalDateTime.now());
              return productAttributeOutPort.save(productAttributeModel);
            });
  }

  @Override
  public Mono<ProductAttributeModel> removeAttributeToProduct(Long productId, Long attributeId, ProductAttributeModel productAttributeModel) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMap(product -> attributeOutPort.findById(attributeId)
                    .switchIfEmpty(Mono.error(new NotFoundException(ATTRIBUTE_NOT_FOUND)))
            )
            .flatMap(ignored -> productAttributeOutPort.findByProductIdAndAttributeId(productId, attributeId)
                    .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_ATTRIBUTE_NOT_FOUND)))
            )
            .flatMap(existingAssociation ->
                    productAttributeOutPort.delete(existingAssociation.getId())
                            .thenReturn(existingAssociation)
            );
  }

  @Override
  public Mono<ProductAttributeModel> updateAttributeToProduct(Long productId, Long attributeId, ProductAttributeModel productAttributeModel) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMap(product -> attributeOutPort.findById(attributeId)
                    .switchIfEmpty(Mono.error(new NotFoundException(ATTRIBUTE_NOT_FOUND)))
            )
            .flatMap(ignored -> productAttributeOutPort.findByProductIdAndAttributeId(productId, attributeId)
                    .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_ATTRIBUTE_NOT_FOUND))))
            .flatMap(existingAssociation -> {
              existingAssociation.setAttributeValue(productAttributeModel.getAttributeValue());
              existingAssociation.setUpdatedAt(LocalDateTime.now());

              return productAttributeOutPort.save(existingAssociation);
            });
  }

  @Override
  public Flux<ProductAttributeModel> getProductAttributes(Long productId) {
    return productOutPort.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND)))
            .flatMapMany(product -> productAttributeOutPort.findByProductId(productId));
  }
}

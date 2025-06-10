package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.model.AttributeModel;
import com.arka.microservice.stock_ms.domain.ports.in.IAttributeInPort;
import com.arka.microservice.stock_ms.domain.ports.out.IAttributeOutPort;
import com.arka.microservice.stock_ms.domain.util.validations.AttributeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.stock_ms.domain.util.AttributeConstant.*;

@Component
@RequiredArgsConstructor
public class AttributeUseCaseImpl implements IAttributeInPort {

  private final IAttributeOutPort  attributeOutPort;

  @Override
  public Mono<AttributeModel> createAttribute(AttributeModel attributeModel) {
    return Mono.when(
            AttributeValidation.validateName(attributeModel.getName()),
            AttributeValidation.validateDescription(attributeModel.getDescription())
           ).then(attributeOutPort.findByName(attributeModel.getName()))
              .flatMap(existingAttribute -> existingAttribute != null
                      ? Mono.error(new RuntimeException("Attribute with the same name already exists"))
                      : Mono.just(attributeModel))
              .switchIfEmpty(Mono.defer(() -> {
                        attributeModel.setName(attributeModel.getName().toLowerCase());
                        attributeModel.setCreatedAt(LocalDateTime.now());
                        attributeModel.setUpdatedAt(LocalDateTime.now());
                        return attributeOutPort.save(attributeModel);
                      })
              );
  }

  @Override
  public Mono<AttributeModel> updateAttribute(Long id, AttributeModel attributeModel) {
    return attributeOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(ATTRIBUTE_NOT_FOUND)))
            .flatMap(existingAttribute ->
                    Mono.when(
                            AttributeValidation.validateName(attributeModel.getName()),
                            AttributeValidation.validateDescription(attributeModel.getDescription())
                    ).thenReturn(existingAttribute))
            .flatMap(existingAttribute -> attributeOutPort.findByName((attributeModel.getName()))
                    .filter(attribute -> !attribute.getId().equals(id))
                    .flatMap(attribute -> existingAttribute != null
                            ? Mono.error(new RuntimeException(ATTRIBUTE_NAME_ALREADY_EXISTS))
                            : Mono.just(attributeModel))
                    .switchIfEmpty(Mono.defer(() -> {
                      existingAttribute.setName(attributeModel.getName().toLowerCase());
                      existingAttribute.setDescription(attributeModel.getDescription());
                      existingAttribute.setUpdatedAt(LocalDateTime.now());
                      return attributeOutPort.save(existingAttribute);
                    }))
            );
  }

  @Override
  public Flux<AttributeModel> getAllAttributes() {
    return attributeOutPort.findAll()
            .switchIfEmpty(Flux.empty());
  }

  @Override
  public Mono<String> deleteAttribute(Long id) {
    return attributeOutPort.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException(ATTRIBUTE_NOT_FOUND)))
            .flatMap(attribute -> attributeOutPort.delete(id)
                    .thenReturn(ATTRIBUTE_DELETED_SUCCESSFULLY));
  }
}

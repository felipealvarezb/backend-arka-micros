package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.exception.DuplicateResourceException;
import com.arka.microservice.stock_ms.domain.exception.NotFoundException;
import com.arka.microservice.stock_ms.domain.model.SupplierModel;
import com.arka.microservice.stock_ms.domain.ports.in.ISupplierInPort;
import com.arka.microservice.stock_ms.domain.ports.out.ISupplierOutPort;
import com.arka.microservice.stock_ms.domain.util.validations.SupplierValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.stock_ms.domain.util.SupplierConstant.*;

@Component
@RequiredArgsConstructor
public class SupplierUseCaseImpl implements ISupplierInPort {

  private final ISupplierOutPort supplierOutPort;

  @Override
  public Mono<SupplierModel> createSupplier(SupplierModel supplierModel) {
    return Mono.just(supplierModel)
            .flatMap(supplier -> Mono.when(
                    SupplierValidation.validateName(supplierModel.getName()),
                    SupplierValidation.validateEmail(supplierModel.getEmail()),
                    SupplierValidation.validatePhone(supplierModel.getPhone()),
                    SupplierValidation.validateAddress(supplierModel.getAddress())
            ).thenReturn(supplier))
            .flatMap(supplier -> Mono.when(
                    validateEmailDoesNotExist(supplier.getEmail())
            ).thenReturn(supplier))
            .map(supplier -> {
              supplier.setIsActive(true);
              supplier.setCreatedAt(LocalDateTime.now());
              supplier.setUpdatedAt(LocalDateTime.now());
              return supplier;
            })
            .flatMap(supplierOutPort::save);
  }

  @Override
  public Mono<SupplierModel> updateSupplier(Long id, SupplierModel supplierModel) {
    return supplierOutPort.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException(SUPPLIER_NOT_FOUND)))
            .flatMap(existingSupplier -> {
              existingSupplier.setName(supplierModel.getName());
              existingSupplier.setEmail(supplierModel.getEmail());
              existingSupplier.setPhone(supplierModel.getPhone());
              existingSupplier.setAddress(supplierModel.getAddress());
              existingSupplier.setUpdatedAt(LocalDateTime.now());
              return supplierOutPort.save(existingSupplier);
            });
  }

  @Override
  public Flux<SupplierModel> getAllSuppliers() {
    return supplierOutPort.findAll()
            .switchIfEmpty(Flux.empty());
  }

  @Override
  public Mono<String> deleteSupplier(Long id) {
    return supplierOutPort.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException(SUPPLIER_NOT_FOUND)))
            .flatMap(supplier -> supplierOutPort.delete(id)
                    .thenReturn(SUPPLIER_DELETED_SUCCESSFULLY));
  }

  private Mono<Void> validateEmailDoesNotExist(String email) {
    return supplierOutPort.findByEmail(email)
            .flatMap(user -> user != null
                    ? Mono.error(new DuplicateResourceException(SUPPLIER_EMAIL_ALREADY_EXISTS))
                    : Mono.empty()
            );
  }
}

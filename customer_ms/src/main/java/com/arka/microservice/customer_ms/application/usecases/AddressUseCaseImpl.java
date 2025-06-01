package com.arka.microservice.customer_ms.application.usecases;

import com.arka.microservice.customer_ms.domain.exception.BusinessException;
import com.arka.microservice.customer_ms.domain.exception.NotFoundException;
import com.arka.microservice.customer_ms.domain.model.AddressModel;
import com.arka.microservice.customer_ms.domain.ports.in.IAddressInPort;
import com.arka.microservice.customer_ms.domain.ports.out.IAddressOutPort;
import com.arka.microservice.customer_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.customer_ms.domain.util.AddressValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.customer_ms.domain.util.AddressConstants.ADDRESS_NOT_FOUND;
import static com.arka.microservice.customer_ms.domain.util.UserConstants.USER_NOT_FOUND;


@Component
@RequiredArgsConstructor
public class AddressUseCaseImpl implements IAddressInPort {

  private final IAddressOutPort addressOutPort;
  private final IUserOutPort userOutPort;

  @Override
  public Mono<AddressModel> addAddress(AddressModel address) {
    return getAuthenticatedEmail()
            .flatMap(email -> userOutPort.findByEmail(email))
            .switchIfEmpty(Mono.error(new NotFoundException(USER_NOT_FOUND)))
            .flatMap(user -> validateAddress(address)
                    .map(validatedAddress -> {
                      validatedAddress.setCreatedAt(LocalDateTime.now());
                      validatedAddress.setUpdatedAt(LocalDateTime.now());
                      validatedAddress.setUser(user);
                      return validatedAddress;
                    })
                    .flatMap(addressOutPort::save));
  }

  @Override
  public Mono<AddressModel> updateAddress(Long addressId, AddressModel updatedAddress) {
    return getAuthenticatedEmail()
            .flatMap(email -> userOutPort.findByEmail(email))
            .switchIfEmpty(Mono.error(new NotFoundException(USER_NOT_FOUND)))
            .flatMap(user ->
                    addressOutPort.findByIdAndUserId(addressId, user.getId())
                            .switchIfEmpty(Mono.error(new NotFoundException(ADDRESS_NOT_FOUND)))
                            .flatMap(existingAddress ->
                                    validateAddress(updatedAddress)
                                            .then(Mono.fromSupplier(() -> {
                                              existingAddress.setAddress(updatedAddress.getAddress());
                                              existingAddress.setCity(updatedAddress.getCity());
                                              existingAddress.setState(updatedAddress.getState());
                                              existingAddress.setCountry(updatedAddress.getCountry());
                                              existingAddress.setZipCode(updatedAddress.getZipCode());
                                              existingAddress.setUpdatedAt(LocalDateTime.now());
                                              return existingAddress;
                                            }))
                            )
                            .flatMap(addressOutPort::save)
            );
  }

  @Override
  public Flux<AddressModel> listUserAddresses() {
    return getAuthenticatedEmail()
            .flatMap(userOutPort::findByEmail)
            .switchIfEmpty(Mono.error(new NotFoundException(USER_NOT_FOUND)))
            .flatMapMany(user -> addressOutPort.findAllByUserId(user.getId()));
  }

  @Override
  public Mono<String> deleteAddress(Long addressId) {
    return getAuthenticatedEmail()
            .flatMap(userOutPort::findByEmail)
            .switchIfEmpty(Mono.error(new NotFoundException(USER_NOT_FOUND)))
            .flatMap(user -> addressOutPort.findByIdAndUserId(addressId, user.getId())
                    .switchIfEmpty(Mono.error(new NotFoundException(ADDRESS_NOT_FOUND)))
                    .flatMap(addressOutPort::delete)
                    .thenReturn("Address deleted successfully")
            );
  }

  private Mono<AddressModel> validateAddress(AddressModel address) {
    return Mono.when(
            AddressValidation.validateCity(address.getCity()),
            AddressValidation.validateCountry(address.getCountry()),
            AddressValidation.validateState(address.getState()),
            AddressValidation.validateZipCode(address.getZipCode()),
            AddressValidation.validateAddress(address.getAddress())
    ).thenReturn(address);
  }

  private Mono<String> getAuthenticatedEmail() {
    return ReactiveSecurityContextHolder.getContext()
            .map(context -> {
              Authentication authentication = context.getAuthentication();
              return authentication.getName();
            });
  }
}

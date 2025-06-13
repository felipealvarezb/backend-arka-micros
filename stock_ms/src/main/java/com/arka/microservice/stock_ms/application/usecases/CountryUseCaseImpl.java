package com.arka.microservice.stock_ms.application.usecases;

import com.arka.microservice.stock_ms.domain.exception.DuplicateResourceException;
import com.arka.microservice.stock_ms.domain.exception.NotFoundException;
import com.arka.microservice.stock_ms.domain.model.CountryModel;
import com.arka.microservice.stock_ms.domain.ports.in.ICountryInPort;
import com.arka.microservice.stock_ms.domain.ports.out.ICountryOutPort;
import com.arka.microservice.stock_ms.domain.ports.out.IUserOutPort;
import com.arka.microservice.stock_ms.domain.util.validations.CountryValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static com.arka.microservice.stock_ms.domain.util.CountryConstant.*;

@Component
@RequiredArgsConstructor
public class CountryUseCaseImpl implements ICountryInPort {

  private final ICountryOutPort countryOutPort;
  private final IUserOutPort userOutPort;

  @Override
  public Mono<CountryModel> createCountry(CountryModel countryModel) {
    return CountryValidation.validateName(countryModel.getName())
            .then(userOutPort.getUserAdminLogistic(countryModel.getLogisticSupervisorId())
                    .flatMap(response -> {
                      if (response.contains("No se pudo validar")) {
                        return Mono.error(new NotFoundException(LOGISTIC_USER_NOT_VALID));
                      }
                      countryModel.setCreatedAt(LocalDateTime.now());
                      countryModel.setUpdatedAt(LocalDateTime.now());
                      return countryOutPort.save(countryModel);
                    }));
  }

  @Override
  public Mono<CountryModel> updateCountry(Long id, CountryModel countryModel) {
    return countryOutPort.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException(COUNTRY_NOT_FOUND)))
            .flatMap(existingCountry ->
                    CountryValidation.validateName(countryModel.getName())
                            .then(
                                    countryOutPort.findByName(countryModel.getName().toLowerCase())
                                            .flatMap(existingWithName -> {
                                              if (!existingWithName.getId().equals(id)) {
                                                return Mono.error(new DuplicateResourceException(COUNTRY_NAME_ALREADY_EXISTS));
                                              }
                                              return Mono.empty();
                                            })
                            )
                            .then(userOutPort.getUserAdminLogistic(countryModel.getLogisticSupervisorId()))
                            .flatMap(response -> {
                              if (response.contains("No se pudo validar")) {
                                return Mono.error(new NotFoundException(LOGISTIC_USER_NOT_VALID));
                              }

                              existingCountry.setName(countryModel.getName().toLowerCase());
                              existingCountry.setLogisticSupervisorId(countryModel.getLogisticSupervisorId());
                              existingCountry.setUpdatedAt(LocalDateTime.now());

                              return countryOutPort.save(existingCountry);
                            })
            );
  }

}

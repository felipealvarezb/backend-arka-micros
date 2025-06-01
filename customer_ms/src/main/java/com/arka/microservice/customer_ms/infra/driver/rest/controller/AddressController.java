package com.arka.microservice.customer_ms.infra.driver.rest.controller;

import com.arka.microservice.customer_ms.domain.ports.in.IAddressInPort;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.AddressRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.AddressResponseDTO;
import com.arka.microservice.customer_ms.infra.driver.rest.mapper.IAddressRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

  private final IAddressInPort addressInPort;
  private final IAddressRestMapper addressRestMapper;

  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<AddressResponseDTO> addAddress(@RequestBody AddressRequest request) {
    return addressInPort.addAddress(addressRestMapper.dtoToModel(request))
            .map(addressRestMapper::modelToAddressResponseDTO);
  }

  @PutMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<AddressResponseDTO> updateAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
    return addressInPort.updateAddress(id, addressRestMapper.dtoToModel(request))
            .map(addressRestMapper::modelToAddressResponseDTO);
  }

  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<AddressResponseDTO> getAddresses() {
    return addressInPort.listUserAddresses()
            .map(addressRestMapper::modelToAddressResponseDTO);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteAddress(@PathVariable Long id) {
    return addressInPort.deleteAddress(id);
  }

}

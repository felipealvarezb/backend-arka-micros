package com.arka.microservice.customer_ms.infra.driver.rest.controller;

import com.arka.microservice.customer_ms.domain.ports.in.IAddressInPort;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.request.AddressRequest;
import com.arka.microservice.customer_ms.infra.driver.rest.dto.response.AddressResponseDTO;
import com.arka.microservice.customer_ms.infra.driver.rest.mapper.IAddressRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Gestión de direcciones")
public class AddressController {

  private final IAddressInPort addressInPort;
  private final IAddressRestMapper addressRestMapper;

  @Operation(summary = "Add Address", description = "Create and save an address")
  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<AddressResponseDTO> addAddress(@RequestBody AddressRequest request) {
    return addressInPort.addAddress(addressRestMapper.dtoToModel(request))
            .map(addressRestMapper::modelToAddressResponseDTO);
  }

  @Operation(summary = "Update Address", description = "Update and save an address")
  @PutMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<AddressResponseDTO> updateAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
    return addressInPort.updateAddress(id, addressRestMapper.dtoToModel(request))
            .map(addressRestMapper::modelToAddressResponseDTO);
  }

  @Operation(summary = "List Address", description = "List all user address")
  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<AddressResponseDTO> getAddresses() {
    return addressInPort.listUserAddresses()
            .map(addressRestMapper::modelToAddressResponseDTO);
  }

  @Operation(summary = "Delete Address", description = "Delete an address")
  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteAddress(@PathVariable Long id) {
    return addressInPort.deleteAddress(id);
  }

}

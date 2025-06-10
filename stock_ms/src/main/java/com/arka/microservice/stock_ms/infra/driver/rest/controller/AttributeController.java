package com.arka.microservice.stock_ms.infra.driver.rest.controller;

import com.arka.microservice.stock_ms.domain.ports.in.IAttributeInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AttributeRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.AttributeResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IAttributeRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/attribute")
@RequiredArgsConstructor
public class AttributeController {

  private final IAttributeInPort attributeInPort;
  private final IAttributeRestMapper attributeRestMapper;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<AttributeResponseDTO> createAttribute(@RequestBody AttributeRequestDTO attributeRequestDTO){
    return attributeInPort.createAttribute(attributeRestMapper.attributeRequestToModel(attributeRequestDTO))
            .map(attributeRestMapper::attributeModelToResponseDTO);
  }

  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<AttributeResponseDTO> updateAttribute(@PathVariable Long id, @RequestBody AttributeRequestDTO attributeRequestDTO){
    return attributeInPort.updateAttribute(id, attributeRestMapper.attributeRequestToModel(attributeRequestDTO))
            .map(attributeRestMapper::attributeModelToResponseDTO);
  }


  @GetMapping("/list")
  @ResponseStatus(HttpStatus.OK)
  public Flux<AttributeResponseDTO> getAllAttributes(){
    return attributeInPort.getAllAttributes()
            .map(attributeRestMapper::attributeModelToResponseDTO);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> deleteAttribute(@PathVariable Long id) {
    return attributeInPort.deleteAttribute(id);
  }
}

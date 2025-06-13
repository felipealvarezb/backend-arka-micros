package com.arka.microservice.stock_ms.infra.driver.rest.controller;


import com.arka.microservice.stock_ms.domain.ports.in.IInventoryInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AddProductInventoryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.StockRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.InventoryResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IInventoryRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final IInventoryInPort inventoryInPort;
  private final IInventoryRestMapper inventoryRestMapper;

  @PostMapping("/product/{productId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<InventoryResponseDTO> addInventoryToProduct(
          @PathVariable Long productId,
          @RequestBody AddProductInventoryRequestDTO requestDTO
  ){
    return inventoryInPort.addInventoryToProduct(productId, inventoryRestMapper.requestDtoToModel(requestDTO))
            .map(inventoryRestMapper::modelToResponseDto);
  }

  @PostMapping("{inventoryId}/product/{productId}/update")
  @ResponseStatus(HttpStatus.OK)
  public Mono<InventoryResponseDTO> updateInventoryForProduct(
          @PathVariable Long inventoryId,
          @PathVariable Long productId,
          @RequestBody AddProductInventoryRequestDTO requestDTO
  ){
    return inventoryInPort.updateInventoryForProduct(inventoryId, productId, inventoryRestMapper.requestDtoToModel(requestDTO))
            .map(inventoryRestMapper::modelToResponseDto);
  }

  @PutMapping("{inventoryId}/product/{productId}/remove/stock")
  @ResponseStatus(HttpStatus.OK)
  public Mono<InventoryResponseDTO> removeInventoryFromProduct(
          @PathVariable Long productId,
          @PathVariable Long inventoryId,
          @RequestBody StockRequestDTO requestDTO
  ){
    int quantityToRemove = requestDTO.getQuantity();
    return inventoryInPort.removeStockFromProduct(productId, inventoryId, quantityToRemove)
            .map(inventoryRestMapper::modelToResponseDto);
  }

  @PutMapping("{inventoryId}/product/{productId}/add/stock")
  @ResponseStatus(HttpStatus.OK)
  public Mono<InventoryResponseDTO> addInventoryToProduct(
          @PathVariable Long productId,
          @PathVariable Long inventoryId,
          @RequestBody StockRequestDTO requestDTO
  ){
    int quantityToAdd = requestDTO.getQuantity();
    return inventoryInPort.addStockToProduct(productId, inventoryId, quantityToAdd)
            .map(inventoryRestMapper::modelToResponseDto);
  }


  @GetMapping("{productId}/inventories")
  @ResponseStatus(HttpStatus.OK)
  public Flux<InventoryResponseDTO> getInventoryOfProduct(@PathVariable Long productId){
    return inventoryInPort.getInventoriesByProduct(productId)
            .map(inventoryRestMapper::modelToResponseDto);
  }

}

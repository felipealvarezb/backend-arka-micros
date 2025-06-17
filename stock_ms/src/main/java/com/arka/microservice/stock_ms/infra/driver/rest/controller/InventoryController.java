package com.arka.microservice.stock_ms.infra.driver.rest.controller;


import com.arka.microservice.stock_ms.domain.ports.in.IInventoryInPort;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.AddProductInventoryRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.request.StockRequestDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.dto.response.InventoryResponseDTO;
import com.arka.microservice.stock_ms.infra.driver.rest.mapper.IInventoryRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Gestión de inventario")
public class InventoryController {

  private final IInventoryInPort inventoryInPort;
  private final IInventoryRestMapper inventoryRestMapper;

  @Operation(summary = "Add Inventory to Product", description = "Create an inventory of product")
  @PostMapping("/product/{productId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<InventoryResponseDTO> addInventoryToProduct(
          @PathVariable Long productId,
          @RequestBody AddProductInventoryRequestDTO requestDTO
  ){
    return inventoryInPort.addInventoryToProduct(productId, inventoryRestMapper.requestDtoToModel(requestDTO))
            .map(inventoryRestMapper::modelToResponseDto);
  }

  @Operation(summary = "Update Inventory to Product", description = "Update and save an existing Inventory")
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

  @Operation(summary = "Remove Inventory from Product", description = "Remove stock from inventory")
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

  @Operation(summary = "Add Inventory from Product", description = "Add Stock to inventory")
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


  @Operation(summary = "Get Inventory of Product", description = "Get the inventory of a product")
  @GetMapping("{productId}/inventories")
  @ResponseStatus(HttpStatus.OK)
  public Flux<InventoryResponseDTO> getInventoryOfProduct(@PathVariable Long productId){
    return inventoryInPort.getInventoriesByProduct(productId)
            .map(inventoryRestMapper::modelToResponseDto);
  }

}

package com.arka.microservice.stock_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
  private Double price;
  private Boolean isActive;
  private Long categoryId;
  private Long brandId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

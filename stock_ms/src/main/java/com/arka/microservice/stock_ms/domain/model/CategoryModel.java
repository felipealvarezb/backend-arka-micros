package com.arka.microservice.stock_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
  private Long id;
  private String name;
  private String description;
  private Date createdAt;
  private Date updatedAt;
}

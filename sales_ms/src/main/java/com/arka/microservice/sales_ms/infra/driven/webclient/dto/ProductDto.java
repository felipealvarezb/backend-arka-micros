package com.arka.microservice.sales_ms.infra.driven.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String sku;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Boolean isActive;
    private Long categoryId;
    private Long brandId;
}

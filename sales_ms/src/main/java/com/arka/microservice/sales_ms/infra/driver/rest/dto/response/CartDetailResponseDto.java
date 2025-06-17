package com.arka.microservice.sales_ms.infra.driver.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailResponseDto {
  private Long id;
  private Integer quantity;
  private Long cartId;
  private Long productId;
  private Double subtotal;
}

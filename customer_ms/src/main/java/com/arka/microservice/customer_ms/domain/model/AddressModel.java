package com.arka.microservice.customer_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

  private Long id;
  private String address;
  private String city;
  private String state;
  private String country;
  private String zipCode;
  private UserModel user;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

package com.arka.microservice.customer_ms.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
  private Long id;
  private String firstName;
  private String lastName;
  private String phone;
  private String dni;
  private String email;
  private String password;
  private Boolean isActive;
  private RolModel rol;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}

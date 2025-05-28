package com.arka.microservice.customer_ms.infra.driver.security.util;

public class SecurityConstants {

  private SecurityConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String EMAIL_REQUIRED = "Email is required";
  public static final String EMAIL_FORMAT_ERROR = "Please enter a valid email address";
  public static final String PASSWORD_REQUIRED = "Password is required";
}

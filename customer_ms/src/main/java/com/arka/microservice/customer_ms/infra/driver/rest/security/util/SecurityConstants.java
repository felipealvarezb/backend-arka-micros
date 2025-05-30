package com.arka.microservice.customer_ms.infra.driver.rest.security.util;

public class SecurityConstants {

  private SecurityConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String EMAIL_REQUIRED = "Email is required";
  public static final String EMAIL_FORMAT_ERROR = "Please enter a valid email address";
  public static final String PASSWORD_REQUIRED = "Password is required";
  public static final String USER_EMAIL_NOT_FOUND = "User with email not found";
  public static final String INVALID_PASSWORD = "Invalid Password";
}

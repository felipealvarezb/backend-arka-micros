package com.arka.microservice.customer_ms.domain.util;

public class UserConstants {

  private UserConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final int FIRST_NAME_LENGTH = 50;
  public static final String FIRST_NAME_LENGTH_ERROR = "First name must not exceed 50 characters";
  public static final String FIRST_NAME_REQUIRED = "First name is required";
  public static final int PASSWORD_MIN_LENGTH = 8;
  public static final String PASSWORD_MIN_LENGTH_ERROR = "Password must be at least 8 characters";
  public static final String PASSWORD_REQUIRED = "Password is required";
  public static final String EMAIL_REQUIRED = "Email is required";
  public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
  public static final String EMAIL_FORMAT_ERROR = "Invalid email format";
  public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
  public static final String PHONE_REQUIRED = "Phone is required";
  public static final String PHONE_REGEX =  "^\\+[1-9]\\d{1,14}$";
  public static final String PHONE_FORMAT_ERROR = "Invalid Phone format";
  public static final String DNI_ALREADY_EXISTS = "DNI already exists";
  public static final String USER_ROLE_NAME = "USER";
  public static final String USER_ROLE_NOT_FOUND = "User role not found";
  public static final String USER_NOT_FOUND = "User not found";
}

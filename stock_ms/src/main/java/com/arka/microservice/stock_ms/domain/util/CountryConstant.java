package com.arka.microservice.stock_ms.domain.util;

public class CountryConstant {

  private CountryConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String COUNTRY_NAME_REQUIRED = "Country name is required";
  public static final int COUNTRY_NAME_LENGTH = 50;
  public static final String COUNTRY_NAME_LENGTH_ERROR = "Country name must not exceed 50 characters";
  public static final String COUNTRY_NOT_FOUND = "Country not found";
  public static final String COUNTRY_NAME_ALREADY_EXISTS = "Country name already exists";
  public static final String LOGISTIC_USER_NOT_VALID = "User Logistic not found";
}
package com.arka.microservice.customer_ms.infra.driver.rest.util;

public class AddressDtoConstant {

  private AddressDtoConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String CITY_REQUIRED = "City is required";
  public static final int CITY_NAME_LENGTH = 25;
  public static final String COUNTRY_REQUIRED = "Country is required";
  public static final int COUNTRY_NAME_LENGTH = 25;
  public static final String POSTAL_CODE_REQUIRED = "Postal code is required";
  public static final String POSTAL_CODE_FORMAT_ERROR =  "Postal code must be between 3 & 5-digit number";
  public static final int POSTAL_CODE_MAX_LENGTH = 5;
  public static final String POSTAL_CODE_REGEX =  "^\\d{3,5}$";
  public static final String ADDRESS_REQUIRED = "Address is required";
  public static final int ADDRESS_MAX_LENGTH = 100;
  public static final String STATE_REQUIRED = "City is required";
  public static final int STATE_NAME_LENGTH = 25;
}

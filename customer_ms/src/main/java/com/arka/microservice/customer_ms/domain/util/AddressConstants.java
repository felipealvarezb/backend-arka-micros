package com.arka.microservice.customer_ms.domain.util;

public class AddressConstants {

  private AddressConstants() {
    throw new IllegalStateException("Utility class");
  }

  public static final String CITY_REQUIRED = "City is required";
  public static final int CITY_LENGTH = 25;
  public static final String CITY_LENGTH_ERROR = "City must not exceed 50 characters";
  public static final String COUNTRY_REQUIRED = "Country is required";
  public static final int COUNTRY_LENGTH = 25;
  public static final String COUNTRY_LENGTH_ERROR = "Country must not exceed 50 characters";
  public static final String POSTAL_CODE_REQUIRED = "Postal code is required";
  public static final String POSTAL_CODE_REGEX = "^\\d{3,5}$";
  public static final String POSTAL_CODE_FORMAT_ERROR = "Postal code must be a number between 3 and 5 digits";
  public static final String ADDRESS_REQUIRED = "Address is required";
  public static final int ADDRESS_LENGTH = 100;
  public static final String ADDRESS_LENGTH_ERROR = "Address must not exceed 100 characters";
  public static final String STATE_REQUIRED = "City is required";
  public static final int STATE_LENGTH = 25;
  public static final String STATE_LENGTH_ERROR = "State must not exceed 25 characters";
  public static final String ADDRESS_NOT_FOUND = "Address not found";


}

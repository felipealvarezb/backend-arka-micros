package com.arka.microservice.customer_ms.infra.driver.rest.util;

public class UserDtoConstant {

  private UserDtoConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String FIRST_NAME_REQUIRED = "First name is required";
  public static final int FIRST_NAME_LENGTH = 50;
  public static final String LAST_NAME_REQUIRED = "Last name is required";
  public static final int LAST_NAME_LENGTH = 50;
  public static final String EMAIL_REQUIRED = "Email is required";
  public static final String EMAIL_FORMAT_ERROR =  "Invalid email format";
  public static final int EMAIL_LENGTH = 100;
  public static final String PHONE_REQUIRED = "Phone is required";
  public static final int PHONE_LENGTH = 15;
  public static final String PHONE_REGEX =  "^\\+[1-9]\\d{1,14}$";
  public static final String PHONE_FORMAT_ERROR = "Invalid Phone format, it must start with + followed by a country code";
  public static final String DNI_REQUIRED = "DNI is required";
  public static final int DNI_LENGTH = 15;
  public static final String PASSWORD_REQUIRED = "Password is required";
  public static final int PASSWORD_MIN_LENGTH = 8;

}

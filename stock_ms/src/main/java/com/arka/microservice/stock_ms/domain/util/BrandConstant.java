package com.arka.microservice.stock_ms.domain.util;

public class BrandConstant {
  private BrandConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String BRAND_NAME_REQUIRED = "Brand name is required";
  public static final int BRAND_NAME_LENGTH = 50;
  public static final String BRAND_NAME_LENGTH_ERROR = "Brand name must not exceed 50 characters";
  public static final String BRAND_NOT_FOUND = "Brand not found";
  public static final String BRAND_NAME_ALREADY_EXISTS = "Brand name already exists";
  public static final String BRAND_DESCRIPTION_REQUIRED = "Brand description is required";
  public static final int BRAND_DESCRIPTION_LENGTH = 200;
  public static final String BRAND_DESCRIPTION_LENGTH_ERROR = "Brand description must not exceed 100 characters";
  public static final String BRAND_DELETED_SUCCESSFULLY = "Brand deleted successfully";

}

package com.arka.microservice.stock_ms.domain.util;

public class ProductConstant {
  private ProductConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String PRODUCT_NOT_FOUND = "Product not found";
  public static final String PRODUCT_DELETED_SUCCESSFULLY = "Product deleted successfully";
  public static final String PRODUCT_NAME_REQUIRED = "Product name is required";
  public static final String PRODUCT_DESCRIPTION_REQUIRED = "Product description is required";
  public static final String PRODUCT_IMAGE_URL_REQUIRED = "Product image is required";
  public static final String PRODUCT_PRICE_REQUIRED = "Product price required";
  public static final int PRODUCT_NAME_MAX_LENGTH = 50;
  public static final int PRODUCT_DESCRIPTION_MAX_LENGTH = 255;
  public static final int PRODUCT_IMAGE_URL_MAX_LENGTH = 255;
  public static final String PRODUCT_NAME_LENGTH_ERROR = "Product name must be less than 50 characters";
  public static final String PRODUCT_DESCRIPTION_LENGTH_ERROR = "Product description must be less than 255 characters";
  public static final String PRODUCT_IMAGE_URL_LENGTH_ERROR = "Product image must be less than 255 characters";
  public static final double PRODUCT_PRICE_MIN_VALUE = 0;
  public static final String PRODUCT_PRICE_PRICE_ERROR = "Product price must be greater than 0";
  public static final String PRODUCT_ATTRIBUTE_NOT_FOUND = "Product does not have this attribute";
}

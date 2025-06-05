package com.arka.microservice.stock_ms.domain.util;

public class CategoryConstant {

  private CategoryConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String CATEGORY_NAME_REQUIRED = "Category name is required";
  public static final int CATEGORY_NAME_LENGTH = 50;
  public static final String CATEGORY_NAME_LENGTH_ERROR = "Category name must not exceed 50 characters";
  public static final String CATEGORY_NOT_FOUND = "Category not found";
  public static final String CATEGORY_NAME_ALREADY_EXISTS = "Category name already exists";
  public static final String CATEGORY_DESCRIPTION_REQUIRED = "Category description is required";
  public static final int CATEGORY_DESCRIPTION_LENGTH = 200;
  public static final String CATEGORY_DESCRIPTION_LENGTH_ERROR = "Category description must not exceed 100 characters";
  public static final String CATEGORY_DELETED_SUCCESSFULLY = "Category deleted successfully";
}

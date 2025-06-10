package com.arka.microservice.stock_ms.domain.util;

public class AttributeConstant {
  private AttributeConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String ATTRIBUTE_NAME_REQUIRED = "Attribute name is required";
  public static final int ATTRIBUTE_NAME_LENGTH = 50;
  public static final String ATTRIBUTE_NAME_LENGTH_ERROR = "Attribute name must not exceed 50 characters";
  public static final String ATTRIBUTE_NOT_FOUND = "Attribute not found";
  public static final String ATTRIBUTE_NAME_ALREADY_EXISTS = "Attribute name already exists";
  public static final String ATTRIBUTE_DESCRIPTION_REQUIRED = "Attribute description is required";
  public static final int ATTRIBUTE_DESCRIPTION_LENGTH = 200;
  public static final String ATTRIBUTE_DESCRIPTION_LENGTH_ERROR = "Attribute description must not exceed 100 characters";
  public static final String ATTRIBUTE_DELETED_SUCCESSFULLY = "Attribute deleted successfully";

}

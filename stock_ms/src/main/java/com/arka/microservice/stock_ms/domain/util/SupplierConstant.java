package com.arka.microservice.stock_ms.domain.util;

public class SupplierConstant {
  private SupplierConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String SUPPLIER_EMAIL_ALREADY_EXISTS = "Supplier email already exists";
  public static final String SUPPLIER_NOT_FOUND = "Supplier not found";
  public static final String SUPPLIER_DELETED_SUCCESSFULLY = "Supplier deleted successfully";
  public static final String SUPPLIER_NAME_REQUIRED = "Supplier name is required";
  public static final String SUPPLIER_ADDRESS_REQUIRED = "Supplier address is required";
  public static final String SUPPLIER_PHONE_REQUIRED = "Supplier phone is required";
  public static final String SUPPLIER_EMAIL_REQUIRED = "Supplier email is required";
  public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
  public static final String PHONE_REGEX =  "^\\+[1-9]\\d{1,14}$";
  public static final int SUPPLIER_NAME_LENGTH = 50;
  public static final int SUPPLIER_ADDRESS_LENGTH = 150;
  public static final String EMAIL_FORMAT_ERROR = "Invalid email format";
  public static final String PHONE_FORMAT_ERROR = "Invalid phone format";
  public static final String SUPPLIER_NAME_LENGTH_ERROR = "Supplier name must be less than 50 characters";
  public static final String SUPPLIER_ADDRESS_LENGTH_ERROR = "Supplier address must be less than 150 characters";
}

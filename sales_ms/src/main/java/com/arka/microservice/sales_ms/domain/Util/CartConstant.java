package com.arka.microservice.sales_ms.domain.Util;

public class CartConstant {

  private CartConstant() {
    throw new IllegalStateException("Utility class");
  }

  public final static String CART_ACTIVE = "ACTIVE";
  public final static String CART_INACTIVE = "INACTIVE";
  public final static String CART_CREATED = "Cart created successfully";
  public final static String PRODUCT_ADDED_TO_CART = "Product added to cart successfully";
  public final static String PRODUCT_REMOVED_FROM_CART = "Product removed from cart successfully";

}

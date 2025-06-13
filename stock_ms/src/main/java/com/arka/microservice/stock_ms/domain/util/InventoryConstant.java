package com.arka.microservice.stock_ms.domain.util;

public class InventoryConstant {

  private InventoryConstant() {
    throw new IllegalStateException("Utility class");
  }

  public static final String INVENTORY_NOT_FOUND = "Inventory not found";
  public static final String INVENTORY_MISMATCH = "This inventory does not belong to product";
}

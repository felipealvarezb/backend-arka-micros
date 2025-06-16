package com.arka.microservice.sales_ms.domain.exception;


import com.arka.microservice.sales_ms.domain.exception.error.CommonErrorCode;
import com.arka.microservice.sales_ms.domain.exception.error.ErrorCode;
import com.arka.microservice.sales_ms.domain.exception.error.ErrorCodeRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {
  private ErrorCode errorCode;
  private String code;
  private String message;
  private int statusCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.statusCode = errorCode.getStatusCode();
  }

  public BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.code = errorCode.getCode();
    this.message = message;
    this.statusCode = errorCode.getStatusCode();
  }

  public BusinessException(String code, String message, int statusCode) {
    super(message);
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
    // Try to find the error code in the registry
    this.errorCode = ErrorCodeRegistry.getOrDefault(code, CommonErrorCode.INTERNAL_ERROR);
  }
}

package com.arka.microservice.sales_ms.domain.exception;


import com.arka.microservice.sales_ms.domain.exception.error.CommonErrorCode;
import com.arka.microservice.sales_ms.domain.exception.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
public class DuplicateResourceException extends BusinessException {
  private static final ErrorCode DEFAULT_ERROR_CODE = CommonErrorCode.RESOURCE_ALREADY_EXISTS;
  private static final String DEFAULT_CODE = DEFAULT_ERROR_CODE.getCode();
  private static final int STATUS_CODE = HttpStatus.CONFLICT.value();

  public DuplicateResourceException(String message) {
    super(DEFAULT_ERROR_CODE, message);
  }

  public DuplicateResourceException(ErrorCode errorCode) {
    super(errorCode);
  }

  public DuplicateResourceException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public DuplicateResourceException(String code, String message) {
    super(code, message, STATUS_CODE);
  }
}

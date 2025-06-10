package com.arka.microservice.customer_ms.domain.exception;

import com.arka.microservice.customer_ms.domain.exception.error.CommonErrorCode;
import com.arka.microservice.customer_ms.domain.exception.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
public class BadCredentialException extends BusinessException{

  private static final ErrorCode DEFAULT_ERROR_CODE = CommonErrorCode.UNAUTHORIZED;
  private static final String DEFAULT_CODE = DEFAULT_ERROR_CODE.getCode();
  private static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

  public BadCredentialException(String message) {
    super(DEFAULT_ERROR_CODE, message);
  }

  public BadCredentialException(ErrorCode errorCode) {
    super(errorCode);
  }

  public BadCredentialException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }

  public BadCredentialException(String code, String message) {
    super(code, message, STATUS_CODE);
  }
}

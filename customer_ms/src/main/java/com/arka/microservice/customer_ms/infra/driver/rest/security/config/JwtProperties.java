package com.arka.microservice.customer_ms.infra.driver.rest.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
  private String secret;
  private long expirationTime;
}

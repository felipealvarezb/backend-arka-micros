package com.arka.microservice.stock_ms.infra.driver.rest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
public class JwtConfig {

  @Value("${jwt.secret}")
  private String secretKey;

  @Bean
  public ReactiveJwtDecoder jwtDecoder() {
    byte[] keyBytes;
    try {
      keyBytes = Base64.getDecoder().decode(secretKey);
    } catch (IllegalArgumentException e) {
      keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    }
    System.out.println("Longitud de la clave: " + keyBytes.length);
    SecretKey secretKeySpec = new SecretKeySpec(keyBytes,"HmacSHA256");
    return NimbusReactiveJwtDecoder.withSecretKey(secretKeySpec)
            .build();
  }

  @Bean
  public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
    ReactiveJwtAuthenticationConverter jwtConverter = new ReactiveJwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
      List<String> roles = jwt.getClaimAsStringList("role");
      if (roles == null) {
        roles = Collections.emptyList();
      }
      System.out.println("Roles extraídos del token: " + roles);
      return Flux.fromIterable(roles)
              .doOnNext(role -> System.out.println("Procesando rol: " + role))
              .map(SimpleGrantedAuthority::new)
              .collectList()
              .flatMapMany(Flux::fromIterable);
    });
    return jwtConverter;
  }
}
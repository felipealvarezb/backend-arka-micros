package com.arka.microservice.sales_ms.infra.driver.rest.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@AllArgsConstructor
@EnableReactiveMethodSecurity
public class ResourceServerConfig {

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                       ReactiveJwtDecoder jwtDecoder,
                                                       ReactiveJwtAuthenticationConverter jwtAuthenticationConverter) {

    return http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(exchange -> exchange
                    .pathMatchers(HttpMethod.POST,
                            "/api/order/**",
                            "/api/cart/**").hasAuthority("ROLE_USER")
                    .pathMatchers(HttpMethod.PUT, "/api/attribute/**",
                            "/api/order/**",
                            "/api/cart/**").hasAuthority("ROLE_ADMIN")
                    .pathMatchers(HttpMethod.DELETE, "/api/attribute/**",
                            "/api/order/**",
                            "/api/cart/**").hasAuthority("ROLE_ADMIN")
                    .pathMatchers(HttpMethod.GET, "/api/attribute/**",
                            "/api/order/**",
                            "/api/cart/**").hasAuthority("ROLE_ADMIN")
                    .pathMatchers(HttpMethod.GET, "/api/order/**",
                            "/api/cart/**")
                    .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt
                            .jwtDecoder(jwtDecoder)
                            .jwtAuthenticationConverter(jwtAuthenticationConverter)
                    )
            )
            .build();
  }
}
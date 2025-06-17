package com.arka.microservice.customer_ms.infra.driver.rest.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                    .pathMatchers("/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/webjars/**").permitAll()
                    .pathMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                    .pathMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                    .pathMatchers(HttpMethod.GET, "/api/user/profile").hasAnyAuthority("ROLE_USER")
                    .pathMatchers(HttpMethod.GET, "/api/address/**").hasAnyAuthority("ROLE_USER")
                    .pathMatchers(HttpMethod.POST, "/api/admin/**").hasAnyAuthority("ROLE_ADMIN")
                    .pathMatchers("/api/auth/**").permitAll()
                    .anyExchange().authenticated()
            )
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

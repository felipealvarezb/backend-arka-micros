package com.arka.microservice.customer_ms.infra.driver.rest.security.config;

import com.arka.microservice.customer_ms.domain.exception.UnauthorizedException;
import com.arka.microservice.customer_ms.domain.exception.SecurityException;
import com.arka.microservice.customer_ms.infra.driver.rest.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.arka.microservice.customer_ms.domain.util.SecurityConstants.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

  private static final String ERR_INVALID_TOKEN  = "ERR_INVALID_TOKEN";

  private final JwtUtil jwtUtil;
  private final ReactiveUserDetailsService userDetailsService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return resolveTokenReactive(exchange.getRequest())
            .flatMap(token -> {
              try {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractClaim(token, claims -> claims.get("role", String.class)); // 🔥 Extraemos el rol

                log.info("Usuario autenticado: {} con rol {}", username, role);

                return userDetailsService.findByUsername(username)
                        .filter(userDetails -> jwtUtil.validateToken(token, userDetails))
                        .map(userDetails -> {
                          List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role)); // 🔥 Asignamos el rol desde el token

                          return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                        })
                        .flatMap(authentication -> chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)))
                        .switchIfEmpty(Mono.error(new UnauthorizedException(ERR_INVALID_TOKEN, TOKEN_INVALID)));

              } catch (Exception e) {
                log.error("Error en autenticación: {}", e.getMessage());
                return Mono.error(new SecurityException(ERR_INVALID_TOKEN, TOKEN_INVALID));
              }
            })
            .onErrorResume(e -> {
              log.error("Authentication error: {}", e.getMessage());
              return Mono.error(e);
            })
            .switchIfEmpty(chain.filter(exchange));
  }

  private Mono<String> resolveTokenReactive(ServerHttpRequest request) {
    return Mono.justOrEmpty(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
            .filter(bearerToken -> bearerToken.startsWith(TOKEN_PREFIX))
            .map(bearerToken -> {
              String token = bearerToken.substring(7);
              log.info("Token extraído: {}", token);
              return token;
            });
  }

}

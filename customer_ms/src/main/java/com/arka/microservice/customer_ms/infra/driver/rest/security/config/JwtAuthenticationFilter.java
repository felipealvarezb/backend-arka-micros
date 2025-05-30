package com.arka.microservice.customer_ms.infra.driver.rest.security.config;

import com.arka.microservice.customer_ms.domain.exception.UnauthorizedException;
import com.arka.microservice.customer_ms.domain.exception.SecurityException;
import com.arka.microservice.customer_ms.infra.driver.rest.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.arka.microservice.customer_ms.domain.util.SecurityConstants.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

  private static final String ERR_INVALID_TOKEN  = "ERR_INVALID_TOKEN";
  private static final String ERR_EXPIRED_TOKEN  = "ERR_EXPIRED_TOKEN";

  private final JwtUtil jwtUtil;
  private final ReactiveUserDetailsService userDetailsService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return resolveTokenReactive(exchange.getRequest())
            .flatMap(token -> {
              try {
                String username = jwtUtil.extractUsername(token);
                log.info("Username: {}", username);
                return userDetailsService.findByUsername(username)
                        .filter(userDetails -> jwtUtil.validateToken(token, userDetails))
                        .map(userDetails -> new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()))
                        .flatMap(authentication -> chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)))
                        .switchIfEmpty(Mono.error(new UnauthorizedException(ERR_INVALID_TOKEN, TOKEN_INVALID)));

              } catch (Exception e) {
                if (e.getMessage().contains("expired")) {
                  log.info("Token has expired: {}", e.getMessage());
                  return Mono.error(new SecurityException(ERR_EXPIRED_TOKEN, TOKEN_EXPIRED));
                }
                log.info("Invalid token format: {}", e.getMessage());
                return Mono.error(new SecurityException(ERR_INVALID_TOKEN, TOKEN_INVALID));
              }
            })
            .onErrorResume(e -> {
              if (!(e instanceof SecurityException) && !(e instanceof UnauthorizedException)) {
                log.info("Authentication error: {}", e.getMessage());
              }
              return chain.filter(exchange);
            })
            .switchIfEmpty(chain.filter(exchange));
  }

  private Mono<String> resolveTokenReactive(ServerHttpRequest request) {
    return Mono.justOrEmpty(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
            .filter(bearerToken -> bearerToken.startsWith(TOKEN_PREFIX))
            .map(bearerToken -> bearerToken.substring(7));
  }

}

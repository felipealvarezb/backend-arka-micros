package com.arka.microservice.customer_ms.infra.driver.rest.security.util;

import com.arka.microservice.customer_ms.domain.model.UserModel;
import com.arka.microservice.customer_ms.domain.ports.out.IRolOutPort;
import com.arka.microservice.customer_ms.infra.driver.rest.security.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.arka.microservice.customer_ms.domain.util.UserConstants.USER_ROLE_NOT_FOUND;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

  private final JwtProperties jwtProperties;
  private final IRolOutPort rolOutPort;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Mono<String> generateToken(UserModel user) {
    return rolOutPort.findById(user.getRoleId())
            .switchIfEmpty(Mono.error(new RuntimeException(USER_ROLE_NOT_FOUND)))
            .map(rol -> {
              Map<String, Object> claims = new HashMap<>();
              claims.put("role", rol.getName());
              return createToken(claims, user.getEmail());
            });
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationTime()))
            .addClaims(claims)
            .signWith(getSigningKey())
            .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(getSigningKey())
              .build()
              .parseClaimsJws(token);

      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    } catch (Exception e) {
      log.error("Error validando token: {}", e.getMessage());
      return false;
    }
  }
}

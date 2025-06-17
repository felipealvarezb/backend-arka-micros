package com.arka.microservice.sales_ms.infra.driven.webclient.config;


import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Component
public class SendTokenWebClient {

  public ExchangeFilterFunction authHeaderFilter() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest ->
            ReactiveSecurityContextHolder.getContext()
                    .map(SecurityContext::getAuthentication)
                    .flatMap(authentication -> {
                      Object credentials = authentication.getCredentials();
                      String token = "";
                      if (credentials instanceof String) {
                        token = (String) credentials;
                      } else if (credentials instanceof org.springframework.security.oauth2.jwt.Jwt) {
                        token = ((org.springframework.security.oauth2.jwt.Jwt) credentials).getTokenValue();
                      }
                      final String finalToken = token;
                      if (!finalToken.isEmpty()) {
                        return Mono.just(ClientRequest.from(clientRequest)
                                .headers(headers -> headers.setBearerAuth(finalToken))
                                .build());
                      }
                      return Mono.just(clientRequest);
                    })
                    .defaultIfEmpty(clientRequest)
    );
  }
}
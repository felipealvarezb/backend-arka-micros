package com.arka.microservice.stock_ms.infra.driven.webclient.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

  @Value("${webclient.user.baseUrl}")
  private String userBaseUrl;

  private final SendTokenWebClient sendTokenWebClient;

  public WebClientConfig(SendTokenWebClient sendTokenWebClient) {
    this.sendTokenWebClient = sendTokenWebClient;
  }

  @Bean(name = "userApiClient")
  public WebClient userApiClient(WebClient.Builder builder) {
    return builder
            .baseUrl(userBaseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filter(sendTokenWebClient.authHeaderFilter())
            .build();
  }
}

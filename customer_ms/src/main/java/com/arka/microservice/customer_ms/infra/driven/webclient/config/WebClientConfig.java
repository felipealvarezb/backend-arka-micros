package com.arka.microservice.customer_ms.infra.driven.webclient.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
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

  @Value("${webclient.order.baseUrl}")
  private String orderBaseUrl;

  private final SendTokenWebClient sendTokenWebClient;

  public WebClientConfig(SendTokenWebClient sendTokenWebClient) {
    this.sendTokenWebClient = sendTokenWebClient;
  }

  @Bean(name = "cartApiClient")
  public WebClient cartApiClient(WebClient.Builder builder) {
    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected( conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            );
    return builder
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl(orderBaseUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filter(sendTokenWebClient.authHeaderFilter())
            .build();
  }
}

package com.arka.microservice.stock_ms.infra.driven.r2dbc.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PostgresSqlConnectionProperties.class)
public class PostgresSqlConnectionPool {

  public static final int INITIAL_SIZE = 12;
  public static final int MAX_SIZE = 15;
  public static final int MAX_IDLE_TIME = 30;

  @Bean
  public ConnectionPool getConnectionConfig(PostgresSqlConnectionProperties properties) {

    PostgresqlConnectionConfiguration dbConfiguration = PostgresqlConnectionConfiguration.builder()
            .host(properties.host())
            .port(properties.port())
            .database(properties.database())
            //.schema(properties.schema())
            .username(properties.username())
            .password(properties.password())
            .build();

    ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
            .connectionFactory(new io.r2dbc.postgresql.PostgresqlConnectionFactory(dbConfiguration))
            .name("api-postgres-connection-pool")
            .initialSize(INITIAL_SIZE)
            .maxSize(MAX_SIZE)
            .maxIdleTime(java.time.Duration.ofMinutes(MAX_IDLE_TIME))
            .validationQuery("SELECT 1")
            .build();

    return new ConnectionPool(poolConfiguration);
  }
}

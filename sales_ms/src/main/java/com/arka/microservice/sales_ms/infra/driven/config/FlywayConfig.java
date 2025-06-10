package com.arka.microservice.sales_ms.infra.driven.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({R2dbcProperties.class, FlywayProperties.class})
public class FlywayConfig {

  @Bean(initMethod = "migrate")
  public Flyway flyway(R2dbcProperties r2dbcProperties, FlywayProperties flywayProperties) {
    return Flyway.configure()
            .dataSource(
                    flywayProperties.getUrl(),
                    flywayProperties.getUser(),
                    flywayProperties.getPassword()
            )
            .schemas(flywayProperties.getSchemas().toArray(new String[0]))
            .locations(flywayProperties.getLocations().toArray(String[]::new))
            .load();
  }
}

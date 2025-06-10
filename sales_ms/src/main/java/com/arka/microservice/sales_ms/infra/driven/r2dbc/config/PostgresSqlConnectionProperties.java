package com.arka.microservice.sales_ms.infra.driven.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.r2dbc")
public record PostgresSqlConnectionProperties(
        String host,
        Integer port,
        String database,
        String schema,
        String username,
        String password
) {
}

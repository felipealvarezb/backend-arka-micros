package com.arka.microservice.sales_ms.infra.driver.rest.controller;

import com.arka.microservice.sales_ms.domain.ports.in.IOrderInPort;
import com.arka.microservice.sales_ms.infra.driver.rest.mapper.IOrderRestMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Gestión de ordenes")
public class OrderController {

  private final IOrderInPort orderInPort;
  private final IOrderRestMapper orderRestMapper;


}

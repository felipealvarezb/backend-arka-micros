package com.arka.microservice.sales_ms.infra.driven.r2dbc.mapper;

import com.arka.microservice.sales_ms.domain.model.PaymentModel;
import com.arka.microservice.sales_ms.infra.driven.r2dbc.entity.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPaymentMapper {

  PaymentEntity modelToEntity(PaymentModel model);

  PaymentModel entityToModel(PaymentEntity entity);
}

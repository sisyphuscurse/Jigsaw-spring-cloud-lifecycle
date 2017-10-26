package com.yiguan.coordinator.api;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransformRequest {

  private String fromProductCode;
  private String toProductCode;
  private BigDecimal amount;
}

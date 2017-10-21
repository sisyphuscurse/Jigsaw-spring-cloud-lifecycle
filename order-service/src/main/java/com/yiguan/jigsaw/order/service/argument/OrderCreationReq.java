package com.yiguan.jigsaw.order.service.argument;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderCreationReq {
  private Integer uid;
  private BigDecimal totalPrice;
}

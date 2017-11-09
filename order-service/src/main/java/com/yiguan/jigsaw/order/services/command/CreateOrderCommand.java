package com.yiguan.jigsaw.order.services.command;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderCommand {
  private Integer uid;
  private BigDecimal totalPrice;
}

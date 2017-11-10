package com.yiguan.jigsaw.order.services.command;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateOrderCommand implements OrderCommand {
  private Integer uid;
  private BigDecimal totalPrice;

  @Override
  public String getCommandName() {
    return CreateOrderCommand.class.getName();
  }
}

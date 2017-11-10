package com.yiguan.jigsaw.order.services.command;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPaidCommand implements OrderCommand {
  private Long oid;

  private String paymentId;

  private String paymentTime;

  @Override
  public String getCommandName() {
    return OrderPaidCommand.class.getName();
  }
}

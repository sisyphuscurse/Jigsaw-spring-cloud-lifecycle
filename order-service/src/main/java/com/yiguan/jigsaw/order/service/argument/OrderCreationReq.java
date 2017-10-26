package com.yiguan.jigsaw.order.service.argument;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCreationReq {
  private Integer uid;
  private BigDecimal totalPrice;
  private List<OrderItem> items;

  @Getter
  @Setter
  public static class OrderItem {
    private Integer pid;
    private String name;
    private BigDecimal price;
    private Integer amount;
  }
}

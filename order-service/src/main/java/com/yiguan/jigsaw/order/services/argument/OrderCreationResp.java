package com.yiguan.jigsaw.order.services.argument;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCreationResp {
  private Integer uid;
  private BigDecimal totalPrice;

  private List<OrderCreationReq.OrderItem> items;

  @Getter
  @Setter
  @Builder
  @EqualsAndHashCode
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OrderItem {
    private long pid;
    private String name;
    private BigDecimal price;
    private Integer amount;
  }
}

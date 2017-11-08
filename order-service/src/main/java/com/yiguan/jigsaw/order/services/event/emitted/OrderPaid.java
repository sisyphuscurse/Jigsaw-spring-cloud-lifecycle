package com.yiguan.jigsaw.order.services.event.emitted;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPaid implements OrderLifecycleEvent {
  private Integer oid;

  private String paymentId;

  private String paymentTime;
}

package com.yiguan.jigsaw.order.services.argument;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotificationReq {
  private Long oid;

  private String paymentId;

  private String paymentTime;
}

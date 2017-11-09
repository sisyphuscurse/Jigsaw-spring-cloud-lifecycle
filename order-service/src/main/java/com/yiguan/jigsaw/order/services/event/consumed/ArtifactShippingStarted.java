package com.yiguan.jigsaw.order.services.event.consumed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtifactShippingStarted implements LogisticLifecycleEvent {
  private Long oid;

  private String shippingId;

  private String shippingTime;
}

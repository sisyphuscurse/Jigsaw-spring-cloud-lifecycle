package com.yiguan.jigsaw.order.service.event.inbound;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtifactShippingStarted implements LogisticLifecycleEvent {
  private Long orderId;
}

package com.yiguan.jigsaw.order.service.event.inbound;

import lombok.Getter;

@Getter
public class ArtifactSigned implements LogisticLifecycleEvent {
  private Long orderId;
}

package com.yiguan.jigsaw.order.services.event.consumed;

import lombok.Getter;

@Getter
public class ArtifactSigned implements LogisticLifecycleEvent {
  private Long orderId;
}

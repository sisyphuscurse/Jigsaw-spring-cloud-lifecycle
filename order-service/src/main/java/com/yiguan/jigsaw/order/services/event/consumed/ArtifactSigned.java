package com.yiguan.jigsaw.order.services.event.consumed;

import com.yiguan.core.bases.ConsumedDomainEvent;
import lombok.Getter;

@Getter
public class ArtifactSigned implements ConsumedDomainEvent {
  private Long oid;
}

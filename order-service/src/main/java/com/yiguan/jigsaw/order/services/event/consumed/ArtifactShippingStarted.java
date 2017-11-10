package com.yiguan.jigsaw.order.services.event.consumed;

import com.yiguan.core.bases.ConsumedDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArtifactShippingStarted implements ConsumedDomainEvent {
  private Long oid;

  private String shippingId;

  private String shippingTime;
}

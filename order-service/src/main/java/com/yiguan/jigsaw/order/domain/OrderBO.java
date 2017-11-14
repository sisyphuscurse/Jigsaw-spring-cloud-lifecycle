package com.yiguan.jigsaw.order.domain;

import com.yiguan.core.bases.DomainObject;
import com.yiguan.jigsaw.order.services.command.OrderPaidCommand;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;

// TODO: 08/11/2017
public interface OrderBO extends DomainObject<OrderBO> {
  void notifyPaid(OrderPaidCommand orderPaidCommand);

  void notifyShippingStarted(ArtifactShippingStarted shippingStarted);

  void signature(ArtifactSigned artifactSigned);
}

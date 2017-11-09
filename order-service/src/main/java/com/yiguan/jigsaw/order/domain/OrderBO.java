package com.yiguan.jigsaw.order.domain;

import com.yiguan.core.bases.DomainObject;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;
import com.yiguan.jigsaw.order.services.event.emitted.OrderPaid;

// TODO: 08/11/2017
public interface OrderBO extends DomainObject<OrderBO> {

  OrderBO notifyPaid(OrderPaid orderPaidEvent);

  OrderBO notifyShippingStarted(ArtifactShippingStarted shippingStartedEvent);

  OrderBO sign(ArtifactSigned artifactSignedEvent);

  OrderBO acceptOrder();

  OrderBO cancelOrder();

}

package com.yiguan.jigsaw.order.domain;

import com.yiguan.core.biz.BizObject;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;
import com.yiguan.jigsaw.order.services.event.emitted.OrderPaid;


public interface OrderBO extends BizObject<OrderBO> {

  OrderBO notifyPaid(OrderPaid orderPaidEvent);

  OrderBO shippingStarted(ArtifactShippingStarted shippingStartedEvent);

  OrderBO signedByCustomer(ArtifactSigned artifactSignedEvent);

  OrderBO accept();

  OrderBO cancel();

}

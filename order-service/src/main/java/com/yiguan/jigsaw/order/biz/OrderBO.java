package com.yiguan.jigsaw.order.biz;

import com.yiguan.core.biz.BizObject;
import com.yiguan.jigsaw.order.service.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.service.event.consumed.ArtifactSigned;
import com.yiguan.jigsaw.order.service.event.emitted.OrderPaid;


public interface OrderBO extends BizObject<OrderBO> {

  OrderBO orderPaid(OrderPaid orderPaidEvent);

  OrderBO shippingStarted(ArtifactShippingStarted shippingStartedEvent);

  OrderBO signedByCustomer(ArtifactSigned artifactSignedEvent);

  OrderBO accept();

  OrderBO cancel();

}

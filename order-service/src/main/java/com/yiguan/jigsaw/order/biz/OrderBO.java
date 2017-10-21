package com.yiguan.jigsaw.order.biz;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.biz.BizObject;
import com.yiguan.jigsaw.order.service.event.inbound.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.service.event.inbound.ArtifactSigned;
import com.yiguan.jigsaw.order.service.event.outbound.OrderPaid;


public interface OrderBO extends BizObject<OrderBO> {

  @Subscribe
  OrderBO orderPaid(OrderPaid orderPaidEvent);

  @Subscribe
  OrderBO shippingStarted(ArtifactShippingStarted shippingStartedEvent);

  @Subscribe
  OrderBO signedByCustomer(ArtifactSigned artifactSignedEvent);

  OrderBO accept();

  OrderBO cancel();

}

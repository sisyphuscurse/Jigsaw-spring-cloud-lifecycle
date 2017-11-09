package com.yiguan.jigsaw.order.services;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.bases.DomainService;
import com.yiguan.jigsaw.order.domain.OrderBO;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.services.argument.OrderCreationReq;
import com.yiguan.jigsaw.order.services.argument.OrderCreationResp;
import com.yiguan.jigsaw.order.services.argument.PaymentNotificationReq;
import com.yiguan.jigsaw.order.services.argument.ShipmentNotificationReq;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;
import com.yiguan.jigsaw.order.services.event.emitted.OrderPaid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
@Service
@Transactional
public class OrderService extends DomainService {

  // TODO: 08/11/2017
  @RequestMapping(value = "orders", method = RequestMethod.POST)
  public OrderCreationResp createOrder(OrderCreationReq request) {
    final Order order = mapper.map(request, Order.class);
    return create(OrderBO.class, order)
        .save()
        .into(OrderCreationResp.class);
  }

  @RequestMapping(value = "paidOrders", method = RequestMethod.POST)
  public OrderCreationResp notifyOrderPaid(PaymentNotificationReq payment) {

    final OrderPaid orderPaidEvent = mapper.map(payment, OrderPaid.class);

    final OrderBO orderBO = load(OrderBO.class, payment.getOid());

    orderBO.notifyPaid(orderPaidEvent);

    return orderBO.into(OrderCreationResp.class);
  }


  @RequestMapping(value = "notifyShippingStarted", method = RequestMethod.POST)
  public OrderCreationResp notifyShippingStarted(ShipmentNotificationReq shipment) {
    return load(OrderBO.class, shipment.getOutTradeNo())
        .notifyPaid(new OrderPaid())
        .into(OrderCreationResp.class);
  }


  @Subscribe
  public void onArtifactShippingStarted(ArtifactShippingStarted shippingStarted) {
    load(OrderBO.class, shippingStarted.getOrderId())
        .shippingStarted(shippingStarted);
  }

  @Subscribe
  public void onArtifactSignedByCustomer(ArtifactSigned artifactSigned) {
    load(OrderBO.class, artifactSigned.getOrderId())
        .signedByCustomer(artifactSigned);
  }
}

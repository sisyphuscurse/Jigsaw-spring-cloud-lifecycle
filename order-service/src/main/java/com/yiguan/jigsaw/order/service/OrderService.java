package com.yiguan.jigsaw.order.service;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.service.BaseService;
import com.yiguan.jigsaw.order.biz.OrderBO;
import com.yiguan.jigsaw.order.service.argument.OrderCreationReq;
import com.yiguan.jigsaw.order.service.argument.OrderCreationResp;
import com.yiguan.jigsaw.order.service.argument.PaymentNotification;
import com.yiguan.jigsaw.order.service.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.service.event.consumed.ArtifactSigned;
import com.yiguan.jigsaw.order.service.event.emitted.OrderPaid;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
@Service
@Transactional
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class OrderService extends BaseService {

  @RequestMapping(value = "orders", method = RequestMethod.POST)
  public OrderCreationResp createOrder(OrderCreationReq request) {
    return create(OrderBO.class, request)
        .save()
        .into(OrderCreationResp.class);
  }

  @RequestMapping(value = "paidOrders", method = RequestMethod.POST)
  public OrderCreationResp setOrderPaid(PaymentNotification payment) {
    return load(OrderBO.class, payment.getOutTradeNo())
        .orderPaid(new OrderPaid())
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

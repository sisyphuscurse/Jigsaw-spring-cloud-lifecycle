package com.yiguan.jigsaw.order.services;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.bases.DomainService;
import com.yiguan.jigsaw.order.domain.OrderBO;
import com.yiguan.jigsaw.order.domain.OrderBean;
import com.yiguan.jigsaw.order.services.argument.OrderCreationReq;
import com.yiguan.jigsaw.order.services.argument.OrderCreationResp;
import com.yiguan.jigsaw.order.services.argument.PaymentNotificationReq;
import com.yiguan.jigsaw.order.services.command.CreateOrderCommand;
import com.yiguan.jigsaw.order.services.command.OrderPaidCommand;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;
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


  @RequestMapping(value = "orders", method = RequestMethod.POST)
  public OrderCreationResp createOrder(OrderCreationReq request) {
    final CreateOrderCommand createOrderCommand = mapper.map(request, CreateOrderCommand.class);

    final OrderBean orderBO = context.getBean(OrderBean.class, createOrderCommand);

    return orderBO.save().into(OrderCreationResp.class);
  }

  @RequestMapping(value = "notifyOrderPaid", method = RequestMethod.POST)
  public OrderCreationResp notifyOrderPaid(PaymentNotificationReq payment) {
    final OrderPaidCommand orderPaidCommand = mapper.map(payment, OrderPaidCommand.class);
    final OrderBO orderBO = context.getBean(OrderBO.class, orderPaidCommand.getOid());

    orderBO.notifyPaid(orderPaidCommand);

    return orderBO.into(OrderCreationResp.class);
  }

  @Subscribe
  public void onArtifactShippingStarted(ArtifactShippingStarted shippingStarted) {
    final OrderBO orderBO = context.getBean(OrderBO.class, shippingStarted.getOid());
    orderBO.notifyShippingStarted(shippingStarted);
  }

  @Subscribe
  public void onArtifactSignedByCustomer(ArtifactSigned artifactSigned) {
    final OrderBO orderBO = context.getBean(OrderBO.class, artifactSigned.getOid());
    orderBO.signature(artifactSigned);
  }
}

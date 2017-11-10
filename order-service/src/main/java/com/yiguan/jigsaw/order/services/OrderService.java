package com.yiguan.jigsaw.order.services;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.bases.DomainService;
import com.yiguan.jigsaw.order.domain.OrderBO;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.domain.impl.OrderBean;
import com.yiguan.jigsaw.order.services.argument.OrderCreationReq;
import com.yiguan.jigsaw.order.services.argument.OrderCreationResp;
import com.yiguan.jigsaw.order.services.argument.PaymentNotificationReq;
import com.yiguan.jigsaw.order.services.command.OrderPaidCommand;
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

  @RequestMapping(value = "orders", method = RequestMethod.POST)
  public OrderCreationResp createOrder(OrderCreationReq request) {
    final Order order = mapper.map(request, Order.class);
    return create(OrderBO.class, order)
        .save()
        .into(OrderCreationResp.class);
  }

  @RequestMapping(value = "notifyOrderPaid", method = RequestMethod.POST)
  public OrderCreationResp notifyOrderPaid(PaymentNotificationReq payment) {

    final OrderPaidCommand orderPaidCommand = mapper.map(payment, OrderPaidCommand.class);
    final OrderBean orderBO = context.getBean(OrderBean.class, orderPaidCommand.getOid());

    orderBO.executeCommand(orderPaidCommand);

    return orderBO.into(OrderCreationResp.class);
  }

  @Subscribe
  public void onArtifactShippingStarted(ArtifactShippingStarted shippingStarted) {
    final OrderBean orderBO = context.getBean(OrderBean.class, shippingStarted.getOid());
    orderBO.sendEvent(shippingStarted);
  }

  @Subscribe
  public void onArtifactSignedByCustomer(ArtifactSigned artifactSigned) {
    final OrderBean orderBO = context.getBean(OrderBean.class, artifactSigned.getOid());
    orderBO.sendEvent(artifactSigned);
  }
}

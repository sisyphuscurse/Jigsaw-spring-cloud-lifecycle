package com.yiguan.jigsaw.order.service;

import com.yiguan.core.service.BaseService;
import com.yiguan.jigsaw.order.biz.OrderBO;
import com.yiguan.jigsaw.order.service.argument.OrderCreationReq;
import com.yiguan.jigsaw.order.service.argument.OrderCreationResp;
import com.yiguan.jigsaw.order.service.argument.PaymentNotification;
import com.yiguan.jigsaw.order.service.event.outbound.OrderPaid;
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

  @RequestMapping(value = "orders", method = RequestMethod.POST, headers = "Accept=application/json")
  public OrderCreationResp createOrder(OrderCreationReq request) {
    return create(OrderBO.class, request)
        .save()
        .into(OrderCreationResp.class);
  }

  @RequestMapping(value = "paidOrders", method = RequestMethod.POST, headers = "Accept=application/json")
  public OrderCreationResp setOrderPaid(PaymentNotification payment) {
    return load(OrderBO.class, payment.getOutTradeNo())
        .orderPaid(new OrderPaid())
        .into(OrderCreationResp.class);
  }


}

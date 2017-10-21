package com.yiguan.jigsaw.order.service;

import com.yiguan.jigsaw.order.biz.BizObject;
import com.yiguan.jigsaw.order.biz.OrderBO;
import com.yiguan.jigsaw.order.service.args.OrderCreationReq;
import com.yiguan.jigsaw.order.service.args.OrderCreationResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class OrderService {

  @Autowired
  private ApplicationContext context;

  @RequestMapping(value = "orders", method = RequestMethod.POST, headers = "Accept=application/json")
  public OrderCreationResp createOrder(OrderCreationReq request) {
    return createBO(OrderBO.class, request)
        .save()
        .into(OrderCreationResp.class);
  }

  private <B extends BizObject<B>, ConstructorArgs> B createBO(Class<B> bClass, ConstructorArgs... request) {
    return context.getBean(bClass, request);
  }

}

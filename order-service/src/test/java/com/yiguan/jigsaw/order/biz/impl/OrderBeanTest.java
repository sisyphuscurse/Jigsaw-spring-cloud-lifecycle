package com.yiguan.jigsaw.order.biz.impl;

import com.yiguan.jigsaw.order.biz.OrderStatus;
import com.yiguan.jigsaw.order.biz.entity.Order;
import com.yiguan.jigsaw.order.biz.repo.OrderRepository;
import com.yiguan.jigsaw.order.service.event.emitted.OrderPaid;
import net.imadz.lifecycle.LifecycleException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderBeanTest extends BizObjectTest {
  @Test(expected = NullPointerException.class)
  public void orderPaid() throws Throwable {
    Order orderInternalState = new Order();
    orderInternalState.setId(1L);
    orderInternalState.setState(OrderStatus.Created);

    OrderBean order = createBizBean(OrderBean.class, Long.valueOf(1L), OrderRepository.class, orderInternalState);

    final OrderPaid orderPaidEvent = new OrderPaid();

    try {
      order.accept();
    } catch (LifecycleException ex) {
      throw ex.getCause();
    }

    try {
      order.orderPaid(orderPaidEvent);
    } catch (LifecycleException ex) {
      throw ex.getCause();
    }
  }

}
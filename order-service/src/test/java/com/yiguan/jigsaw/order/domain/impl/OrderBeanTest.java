package com.yiguan.jigsaw.order.domain.impl;

import com.yiguan.jigsaw.order.domain.OrderStatus;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.repositories.OrderRepository;
import com.yiguan.jigsaw.order.repositories.PaymentRepository;
import com.yiguan.jigsaw.order.repositories.ShipmentRepository;
import net.imadz.lifecycle.LifecycleException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderBeanTest extends AggregateRootTest {

  @Mock
  private OrderRepository repository;

  @Mock
  private PaymentRepository paymentRepository;

  @Mock
  private ShipmentRepository shipmentRepository;

  @Test(expected = LifecycleException.class)
  public void demo() throws Throwable {
    Order orderInternalState = new Order();
    orderInternalState.setId(1L);
    orderInternalState.setState(OrderStatus.Created);

    when(repository.findOne(1L)).thenReturn(orderInternalState);
    final OrderBean orderBean = createBizBean(OrderBean.class, 1L, repository, paymentRepository, shipmentRepository);

    try {
      orderBean.acceptOrder();
    } catch (LifecycleException ex) {
      if (null == ex.getCause()) {
        throw ex;
      } else {
        throw ex.getCause();
      }
    }
  }
}
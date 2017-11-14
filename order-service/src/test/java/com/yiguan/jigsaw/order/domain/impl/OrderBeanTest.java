package com.yiguan.jigsaw.order.domain.impl;

import com.yiguan.jigsaw.order.domain.OrderBean;
import com.yiguan.jigsaw.order.domain.OrderStatus;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.repositories.OrderRepository;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
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


  @Test(expected = LifecycleException.class)
  public void demo() throws Throwable {
    Order orderInternalState = new Order();
    orderInternalState.setId(1L);
    orderInternalState.setState(OrderStatus.Created);
    ArtifactShippingStarted artifactShippingStarted = new ArtifactShippingStarted(1L, "", "");

    when(repository.findOne(1L)).thenReturn(orderInternalState);
    final OrderBean orderBean = createBizBean(OrderBean.class, 1L, repository);

    try {
      orderBean.notifyShippingStarted(artifactShippingStarted);
    } catch (LifecycleException ex) {
      if (null == ex.getCause()) {
        throw ex;
      } else {
        throw ex.getCause();
      }
    }
  }
}
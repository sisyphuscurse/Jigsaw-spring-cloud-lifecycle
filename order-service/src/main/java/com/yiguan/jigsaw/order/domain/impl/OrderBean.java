package com.yiguan.jigsaw.order.domain.impl;

import com.yiguan.core.biz.BizObjectBase;
import com.yiguan.jigsaw.order.domain.OrderBO;
import com.yiguan.jigsaw.order.domain.OrderStatus;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.domain.fsm.OrderFSM;
import com.yiguan.jigsaw.order.domain.fsm.OrderStatusConverter;
import com.yiguan.jigsaw.order.repositories.OrderRepository;
import com.yiguan.jigsaw.order.services.argument.OrderCreationReq;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;
import com.yiguan.jigsaw.order.services.event.emitted.OrderPaid;
import net.imadz.lifecycle.annotations.Event;
import net.imadz.lifecycle.annotations.LifecycleMeta;
import net.imadz.lifecycle.annotations.StateIndicator;
import net.imadz.lifecycle.annotations.state.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@LifecycleMeta(OrderFSM.class)
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrderBean extends BizObjectBase<OrderBean, Order, Long> implements OrderBO {

  @SuppressWarnings("PMD")
  public OrderBean(OrderCreationReq order) {
    super(populateOrder(order));
  }

  @SuppressWarnings("PMD")
  private static Order populateOrder(OrderCreationReq req) {
    return new Order();
  }

  public OrderBean(Long oid) {
    super(oid);
  }

  @Autowired
  public void setRepository(OrderRepository repository) {
    super.setRepository(repository);
  }

  @StateIndicator
  @Converter(OrderStatusConverter.class)
  public OrderStatus getStatus() {
    return this.internalState.getState();
  }

  @SuppressWarnings("PMD")
  private void setStatus(OrderStatus stateName) {
    this.internalState.setState(stateName);
  }

  @Override
  @Event(value = OrderFSM.Events.OrderPaid.class)
  public OrderBO notifyPaid(OrderPaid orderPaidEvent) {
    return this;
  }

  @Override
  @Event
  public OrderBO shippingStarted(ArtifactShippingStarted shippingStartedEvent) {
    return this;
  }

  @Override
  @Event
  public OrderBO signedByCustomer(ArtifactSigned artifactSignedEvent) {
    return this;
  }

  @Override
  @Event
  public OrderBO accept() {
    return this;
  }

  @Override
  @Event
  public OrderBO cancel() {
    return this;
  }

}

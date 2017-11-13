package com.yiguan.jigsaw.order.domain;

import com.yiguan.core.bases.Aggregate;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.domain.fsm.OrderFSM;
import com.yiguan.jigsaw.order.domain.fsm.OrderStatusConverter;
import com.yiguan.jigsaw.order.services.command.CreateOrderCommand;
import com.yiguan.jigsaw.order.services.command.OrderPaidCommand;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;
import net.imadz.lifecycle.annotations.Event;
import net.imadz.lifecycle.annotations.LifecycleMeta;
import net.imadz.lifecycle.annotations.StateIndicator;
import net.imadz.lifecycle.annotations.state.Converter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@LifecycleMeta(OrderFSM.class)
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class OrderBean extends Aggregate<OrderBean, Order, Long>  implements OrderBO {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);

  @SuppressWarnings("PMD")
  public OrderBean(CreateOrderCommand orderCommand) {
    super(mapToOrder(orderCommand));
    internalState.setCreateTime(simpleDateFormat.format(new Date()));
  }

  private static Order mapToOrder(CreateOrderCommand orderCommand) {
    final Order order = mapper.map(orderCommand, Order.class);
    order.getItems().stream()
        .forEach(orderItem -> orderItem.setOrder(order));
    return order;
  }

  public OrderBean(Long oid) {
    super(oid);
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


  @Event(OrderFSM.Events.OrderPaid.class)
  public void notifyPaid(OrderPaidCommand orderPaidCommand) {
    //Payment payment = mapper.map(orderPaidCommand, Payment.class);
  }

  @Event(value = OrderFSM.Events.ShippingStarted.class)
  public void notifyShippingStarted(ArtifactShippingStarted shippingStartedEvent) {
    //Shipment shipment = mapper.map(shippingStartedEvent, Shipment.class);
  }

  @SuppressWarnings("PMD")
  @Event(value = OrderFSM.Events.SignedByCustomer.class)
  public void signeture(ArtifactSigned artifactSignedEvent) {
    // TODO: 10/11/2017
  }

  @SuppressWarnings("PMD")
  @Event(value = OrderFSM.Events.Accept.class)
  public void acceptOrder() {
    // TODO: 10/11/2017
  }

  @SuppressWarnings("PMD")
  @Event(value = OrderFSM.Events.Cancel.class)
  public void cancelOrder() {
    // TODO: 10/11/2017
  }

  @Override
  protected Order findOne(Long id) {
    final Order order = repository.findOne(id);
    return order;
  }

}

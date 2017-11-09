package com.yiguan.jigsaw.order.domain.impl;

import com.yiguan.core.bases.AggregateRoot;
import com.yiguan.jigsaw.order.domain.OrderBO;
import com.yiguan.jigsaw.order.domain.OrderStatus;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.domain.entity.Payment;
import com.yiguan.jigsaw.order.domain.entity.Shipment;
import com.yiguan.jigsaw.order.domain.fsm.OrderFSM;
import com.yiguan.jigsaw.order.domain.fsm.OrderStatusConverter;
import com.yiguan.jigsaw.order.repositories.OrderRepository;
import com.yiguan.jigsaw.order.repositories.PaymentRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

@LifecycleMeta(OrderFSM.class)
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class OrderBean extends AggregateRoot<OrderBean, Order, Long> implements OrderBO {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);

  private PaymentRepository paymentRepository;
  private OrderRepository repository;

  @Autowired
  public void setRepository(OrderRepository repository) {
    this.repository = repository;
  }

  @Autowired
  public void setPaymentRepository(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @SuppressWarnings("PMD")
  public OrderBean(Order order) {
    super(order);
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

  @Override
  @Event(value = OrderFSM.Events.OrderPaid.class)
  public OrderBO notifyPaid(OrderPaid orderPaidEvent) {
    Payment payment = map(orderPaidEvent, Payment.class);
    internalState.setPayment(payment);
    return this;
  }

  @Event(value = OrderFSM.Events.ShippingStarted.class)
  public OrderBO notifyShippingStarted(ArtifactShippingStarted shippingStartedEvent) {
    Shipment shipment = map(shippingStartedEvent, Shipment.class);
    internalState.setShipment(shipment);
    return this;
  }

  @Override
  @Event(value = OrderFSM.Events.SignedByCustomer.class)
  public OrderBO sign(ArtifactSigned artifactSignedEvent) {
    return this;
  }

  @Override
  @Event(value = OrderFSM.Events.Accept.class)
  public OrderBO acceptOrder() {
    return this;
  }

  @Override
  @Event(value = OrderFSM.Events.Cancel.class)
  public OrderBO cancelOrder() {
    return this;
  }

  // TODO: 08/11/2017 should be delete with OrderBO
  @Override
  public OrderBO save() {
    save(internalState);
    return this;
  }

  @Override
  public Order save(Order order) {
    repository.save(order);
    if (Objects.nonNull(order.getPayment())) {
      paymentRepository.save(order.getPayment());
    }
    return order;
  }

  @Override
  public Order findOne(Long id) {
    final Order order = repository.findOne(id);
    order.setPayment(paymentRepository.findByOid(order.getId()));
    return order;
  }

  @Override
  public Order onStateChanged(Order order) {
    return null;
  }
}

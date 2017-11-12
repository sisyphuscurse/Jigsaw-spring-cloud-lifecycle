package com.yiguan.jigsaw.order.domain.impl;

import com.yiguan.core.bases.Aggregate;
import com.yiguan.jigsaw.order.domain.OrderStatus;
import com.yiguan.jigsaw.order.domain.entity.Order;
import com.yiguan.jigsaw.order.domain.entity.Payment;
import com.yiguan.jigsaw.order.domain.entity.Shipment;
import com.yiguan.jigsaw.order.domain.fsm.OrderFSM;
import com.yiguan.jigsaw.order.domain.fsm.OrderStatusConverter;
import com.yiguan.jigsaw.order.repositories.OrderRepository;
import com.yiguan.jigsaw.order.repositories.PaymentRepository;
import com.yiguan.jigsaw.order.repositories.ShipmentRepository;
import com.yiguan.jigsaw.order.services.command.CreateOrderCommand;
import com.yiguan.jigsaw.order.services.command.OrderPaidCommand;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactSigned;
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
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@LifecycleMeta(OrderFSM.class)
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class OrderBean extends Aggregate<OrderBean, Order, Long> {

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);

  private OrderRepository repository;
  private PaymentRepository paymentRepository;
  private ShipmentRepository shipmentRepository;

  @Autowired
  public void setRepository(OrderRepository repository) {
    this.repository = repository;
  }

  @Autowired
  public void setPaymentRepository(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  @Autowired
  public void setShipmentRepository(ShipmentRepository shipmentRepository) {
    this.shipmentRepository = shipmentRepository;
  }

  @SuppressWarnings("PMD")
  public OrderBean(CreateOrderCommand orderCommand) {
    super(mapper.map(orderCommand, Order.class));
    internalState.setCreateTime(simpleDateFormat.format(new Date()));
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


  @Event(value = OrderFSM.Events.OrderPaid.class)
  public void notifyPaid(OrderPaidCommand orderPaidCommand) {
    Payment payment = mapper.map(orderPaidCommand, Payment.class);
    internalState.setPayment(payment);
  }

  @Event(value = OrderFSM.Events.ShippingStarted.class)
  public void notifyShippingStarted(ArtifactShippingStarted shippingStartedEvent) {
    Shipment shipment = mapper.map(shippingStartedEvent, Shipment.class);
    internalState.setShipment(shipment);
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

  public void save() {
    save(internalState);
  }

  @Override
  protected Order save(Order order) {
    repository.save(order);

    if (Objects.nonNull(order.getPayment())) {
      paymentRepository.save(order.getPayment());
    }

    if (Objects.nonNull(order.getShipment())) {
      shipmentRepository.save(order.getShipment());
    }

    this.internalState = order;

    return order;
  }

  @Override
  protected Order findOne(Long id) {
    final Order order = repository.findOne(id);
    order.setPayment(paymentRepository.findByOid(order.getId()));
    order.setShipment(shipmentRepository.findByOid(order.getId()));
    return order;
  }

}

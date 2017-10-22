package com.yiguan.jigsaw.order.service.event.outbound;

import com.yiguan.jigsaw.order.biz.OrderStatus;
import com.yiguan.jigsaw.order.biz.impl.OrderBean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderOutboundEventFactory {

  public Optional<OrderLifecycleEvent> createOutboundEvent(OrderBean order) {
    return OrderOutboundEventFactoryEnum.fromOrderStatus(order.getStatus()).map(factory -> factory.createOutboundEvent(order));
  }

  private enum OrderOutboundEventFactoryEnum {
    OrderPaid(OrderStatus.Paid) {
      @Override
      public OrderLifecycleEvent createOutboundEvent(OrderBean order) {
        return new OrderPaid();
      }
    }, OrderAccepted(OrderStatus.Accepted) {
      @Override
      public OrderLifecycleEvent createOutboundEvent(OrderBean order) {
        return new OrderAccepted();
      }
    }, OrderCancelled(OrderStatus.Cancelled) {
      @Override
      public OrderLifecycleEvent createOutboundEvent(OrderBean order) {
        return new OrderCancelled();
      }
    };

    private final OrderStatus orderStatus;

    OrderOutboundEventFactoryEnum(OrderStatus orderStatus) {
      this.orderStatus = orderStatus;
    }

    public static Optional<OrderOutboundEventFactoryEnum> fromOrderStatus(OrderStatus orderStatus) {
      for (OrderOutboundEventFactoryEnum factory : OrderOutboundEventFactoryEnum.values()) {
        if (factory.orderStatus == orderStatus) {
          return Optional.of(factory);
        }
      }
      return Optional.empty();
    }

    public abstract OrderLifecycleEvent createOutboundEvent(OrderBean order);
  }
}

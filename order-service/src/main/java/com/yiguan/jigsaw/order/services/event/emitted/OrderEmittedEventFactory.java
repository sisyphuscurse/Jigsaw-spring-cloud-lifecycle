package com.yiguan.jigsaw.order.services.event.emitted;

import com.yiguan.jigsaw.order.domain.OrderStatus;
import com.yiguan.jigsaw.order.domain.impl.OrderBean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderEmittedEventFactory {

  public Optional<OrderLifecycleEvent> createEmittedEvent(OrderBean order) {
    return OrderEmittedEventFactoryEnum.fromOrderStatus(order.getStatus()).map(factory -> factory.createEmittedEvent(order));
  }

  private enum OrderEmittedEventFactoryEnum {
    OrderPaid(OrderStatus.Paid) {
      @Override
      public OrderLifecycleEvent createEmittedEvent(OrderBean order) {
        return new OrderPaid();
      }
    }, OrderAccepted(OrderStatus.Accepted) {
      @Override
      public OrderLifecycleEvent createEmittedEvent(OrderBean order) {
        return new OrderAccepted();
      }
    }, OrderCancelled(OrderStatus.Cancelled) {
      @Override
      public OrderLifecycleEvent createEmittedEvent(OrderBean order) {
        return new OrderCancelled();
      }
    };

    private final OrderStatus orderStatus;

    OrderEmittedEventFactoryEnum(OrderStatus orderStatus) {
      this.orderStatus = orderStatus;
    }

    public static Optional<OrderEmittedEventFactoryEnum> fromOrderStatus(OrderStatus orderStatus) {
      for (OrderEmittedEventFactoryEnum factory : OrderEmittedEventFactoryEnum.values()) {
        if (factory.orderStatus == orderStatus) {
          return Optional.of(factory);
        }
      }
      return Optional.empty();
    }

    public abstract OrderLifecycleEvent createEmittedEvent(OrderBean order);
  }
}

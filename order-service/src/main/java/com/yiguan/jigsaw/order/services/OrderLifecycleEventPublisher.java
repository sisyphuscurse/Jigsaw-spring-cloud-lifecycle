package com.yiguan.jigsaw.order.services;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.messaging.AppLifecycleEvent;
import com.yiguan.core.messaging.KafkaClient;
import com.yiguan.jigsaw.order.domain.impl.OrderBean;
import com.yiguan.jigsaw.order.services.event.emitted.OrderEmittedEventFactory;
import com.yiguan.jigsaw.order.services.event.emitted.OrderLifecycleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Transactional
public class OrderLifecycleEventPublisher {


  public static final String ORDER_STATE_CHANGE_TOPIC = "orderStateChangeTopic";
  @Autowired
  private KafkaClient kafkaClient;
  @Autowired
  private OrderEmittedEventFactory factory;


  @Subscribe
  public void onOrderLifecycleEvent(AppLifecycleEvent event) {
    Optional.of(event)
        .filter(isOrderLifecycleEvent())
        .flatMap(toOutboundEvent(event))
        .map(notifyDownstream());
  }

  private Function<AppLifecycleEvent, Optional<OrderLifecycleEvent>> toOutboundEvent(AppLifecycleEvent event) {
    return theEvent -> factory.createEmittedEvent((OrderBean) event.getReactiveObject());
  }


  private Function<OrderLifecycleEvent, Void> notifyDownstream() {
    return theEvent -> {
      kafkaClient.postNotification(ORDER_STATE_CHANGE_TOPIC, theEvent);
      return null;
    };
  }

  private Predicate<AppLifecycleEvent> isOrderLifecycleEvent() {
    return theEvent -> theEvent.getReactiveObject().getClass() == OrderBean.class;
  }

}

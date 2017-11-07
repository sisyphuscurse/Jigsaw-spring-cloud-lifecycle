package com.yiguan.jigsaw.order.service;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.messaging.AppLifecycleEvent;
import com.yiguan.core.messaging.KafkaClient;
import com.yiguan.jigsaw.order.biz.impl.OrderBean;
import com.yiguan.jigsaw.order.service.event.emitted.OrderLifecycleEvent;
import com.yiguan.jigsaw.order.service.event.emitted.OrderOutboundEventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Transactional
public class OrderLifecycleEventPublisher {


  public static final String ORDER_STATE_CHANGE_TOPIC = "orderStateChangeTopic";
  @Autowired
  private KafkaClient kafkaClient;
  @Autowired
  private OrderOutboundEventFactory factory;


  @Subscribe
  public void onOrderLifecycleEvent(AppLifecycleEvent event) {
    Optional.of(event)
        .filter(isOrderLifecycleEvent())
        .flatMap(toOutboundEvent(event))
        .map(notifyDownstream());
  }

  private Function<AppLifecycleEvent, Optional<OrderLifecycleEvent>> toOutboundEvent(AppLifecycleEvent event) {
    return theEvent -> factory.createOutboundEvent((OrderBean) event.getReactiveObject());
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

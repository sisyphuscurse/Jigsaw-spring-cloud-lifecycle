package com.yiguan.jigsaw.order.service;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.messaging.AppLifecycleEvent;
import com.yiguan.core.messaging.KafkaClient;
import com.yiguan.jigsaw.order.biz.impl.OrderBean;
import com.yiguan.jigsaw.order.service.event.outbound.OrderLifecycleEvent;
import com.yiguan.jigsaw.order.service.event.outbound.OrderOutboundEventFactory;
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
      kafkaClient.postNotification(theEvent);
      return null;
    };
  }

  private Predicate<AppLifecycleEvent> isOrderLifecycleEvent() {
    return theEvent -> theEvent.getReactiveObject().getClass() == OrderBean.class;
  }

}

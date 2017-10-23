package com.yiguan.jigsaw.order.service.event;

import com.google.common.eventbus.EventBus;
import com.yiguan.jigsaw.order.service.event.inbound.ArtifactShippingStarted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class KafkaSubscriber {

  @Autowired
  private EventBus eventBus;

  public void subscribe() {
    //TODO [Andy] 添加订阅消息逻辑，请盼盼帮忙实现一下
  }

  public void onMessage(Message kafkaMessage) {
    eventBus.post(populateInboundMessage(kafkaMessage));
  }

  private Object populateInboundMessage(Message kafkaMessage) {
    kafkaMessage.messageId();
    return new ArtifactShippingStarted();
  }


  public static interface Message { String messageId(); }
}

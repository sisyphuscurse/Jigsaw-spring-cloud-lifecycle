package com.yiguan.jigsaw.order.services.subscriber;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.yiguan.core.kafka.message.IMessageHandler;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactShippingStartedSubscriber implements IMessageHandler {

  @Autowired
  private EventBus eventBus;

  public void subscribe() {
    //TODO [Andy] 添加订阅消息逻辑，请盼盼帮忙实现一下
  }

  public void onMessage(OrderMessage kafkaMessage) {
    final ArtifactShippingStarted event = populateInboundMessage(kafkaMessage);
    eventBus.post(event);
  }

  private ArtifactShippingStarted populateInboundMessage(OrderMessage kafkaMessage) {

    return JSON.parseObject(kafkaMessage.getMessage(), ArtifactShippingStarted.class);
  }

  @Override
  public boolean handle(String topic, String message) {
    final OrderMessage kafkaMessage = JSON.parseObject(message, OrderMessage.class);
    onMessage(kafkaMessage);
    return false;
  }

  @Getter
  @Setter
  public static class OrderMessage {
    private String message;
  }


}

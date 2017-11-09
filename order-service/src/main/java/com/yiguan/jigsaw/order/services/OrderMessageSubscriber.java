package com.yiguan.jigsaw.order.services;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.yiguan.core.kafka.message.IMessageHandler;
import com.yiguan.jigsaw.order.services.event.consumed.ArtifactShippingStarted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageSubscriber implements IMessageHandler {

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

  @Override
  public boolean handle(String topic, Object message) {
    onMessage((Message) JSON.parse(message.toString()));
    return false;
  }


  public static interface Message {
    String messageId();
  }
}

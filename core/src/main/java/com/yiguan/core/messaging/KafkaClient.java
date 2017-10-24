package com.yiguan.core.messaging;

import com.yiguan.core.kafka.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient {

  @Autowired
  private KafkaMessageProducer kafkaMessageProducer;

  public void postNotification(StateChangeEvent stateChangeEvent) {
    // TODO, move this class to service project?
    kafkaMessageProducer.send("stateChange", stateChangeEvent);
  }
}

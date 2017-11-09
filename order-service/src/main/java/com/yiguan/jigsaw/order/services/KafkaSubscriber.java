package com.yiguan.jigsaw.order.services;

import com.yiguan.core.kafka.KafkaMessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaSubscriber {

  public static final String ORDER_STATE_CHANGE_TOPIC = "orderStateChangeTopic";


  @Autowired
  public KafkaSubscriber(KafkaMessageConsumer kafkaMessageConsumer, OrderMessageSubscriber orderMessageSubscriber) {
    kafkaMessageConsumer.consume(ORDER_STATE_CHANGE_TOPIC, orderMessageSubscriber);

    kafkaMessageConsumer.start();
  }
}

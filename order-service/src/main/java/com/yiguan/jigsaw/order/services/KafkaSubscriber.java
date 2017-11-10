package com.yiguan.jigsaw.order.services;

import com.yiguan.core.kafka.KafkaMessageConsumer;
import com.yiguan.jigsaw.order.services.subscriber.ArtifactShippingStartedSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaSubscriber {
  @Autowired
  public KafkaSubscriber(KafkaMessageConsumer kafkaMessageConsumer,
                         ArtifactShippingStartedSubscriber artifactShippingStartedSubscriber) {
    artifactShippingStartedSubscriber.subscribe(kafkaMessageConsumer);
    kafkaMessageConsumer.start();
  }
}

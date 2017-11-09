package com.yiguan.jigsaw.order.services;

import com.yiguan.core.kafka.KafkaMessageConsumer;
import com.yiguan.jigsaw.order.services.subscriber.ArtifactShippingStartedSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaSubscriber {

  public static final String ORDER_ARTIFACT_SHIPPING_STARTED = "Order.ArtifactShippingStarted";


  @Autowired
  public KafkaSubscriber(KafkaMessageConsumer kafkaMessageConsumer, ArtifactShippingStartedSubscriber artifactShippingStartedSubscriber) {
    kafkaMessageConsumer.consume(ORDER_ARTIFACT_SHIPPING_STARTED, artifactShippingStartedSubscriber);

    kafkaMessageConsumer.start();
  }
}

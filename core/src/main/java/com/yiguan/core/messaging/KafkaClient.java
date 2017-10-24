package com.yiguan.core.messaging;

import com.yiguan.core.kafka.KafkaMessageProducer;
import com.yiguan.core.transaction.TransactionSynchronizedExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaClient {

  @Autowired
  private KafkaMessageProducer kafkaMessageProducer;
  @Autowired
  private TransactionSynchronizedExecutor executor;

  public void postNotification(final String topic, final StateChangeEvent stateChangeEvent) {
    executor.registerRunnableAfterCommit(() -> {
      kafkaMessageProducer.send(topic, stateChangeEvent);
    });

  }
}

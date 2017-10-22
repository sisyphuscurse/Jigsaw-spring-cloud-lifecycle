package com.yiguan.jigsaw.order.config;

import com.google.common.eventbus.EventBus;
import com.yiguan.core.messaging.KafkaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SynchronizeMessagingConfiguration {
  @Bean
  public EventBus getEventBus() {
    return new EventBus();
  }

  @Bean
  public KafkaClient getKafkaClient() {
    return new KafkaClient();
  }
}

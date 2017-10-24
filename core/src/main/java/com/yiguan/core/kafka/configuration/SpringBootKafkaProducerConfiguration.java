package com.yiguan.core.kafka.configuration;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.producer")
public class SpringBootKafkaProducerConfiguration {
  private List<String> initParams;

  public void setInitParams(List<String> initParams) {
    this.initParams = initParams;
  }

  public List<String> getInitParams() {
    return initParams;
  }
}

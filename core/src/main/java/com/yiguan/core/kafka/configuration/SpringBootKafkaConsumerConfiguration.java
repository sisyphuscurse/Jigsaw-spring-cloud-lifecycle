package com.yiguan.core.kafka.configuration;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.consumer")
public class SpringBootKafkaConsumerConfiguration {
  private List<String> initParams;
  private int threadNum;

  public void setInitParams(List<String> initParams) {
    this.initParams = initParams;
  }

  public void setThreadNum(int threadNum) {
    this.threadNum = threadNum;
  }

  public List<String> getInitParams() {
    return initParams;
  }

  public int getThreadNum() {
    return threadNum;
  }
}

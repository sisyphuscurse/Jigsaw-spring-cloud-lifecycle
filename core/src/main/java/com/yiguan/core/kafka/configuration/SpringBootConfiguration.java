package com.yiguan.core.kafka.configuration;

import com.yiguan.core.kafka.KafkaMessageConsumer;
import com.yiguan.core.kafka.KafkaMessageProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({SpringBootKafkaProducerConfiguration.class, SpringBootKafkaConsumerConfiguration.class})
public class SpringBootConfiguration {
  @Autowired
  private SpringBootKafkaProducerConfiguration springBootKafkaProducerConfiguration;

  @Autowired
  private SpringBootKafkaConsumerConfiguration springBootKafkaConsumerConfiguration;

  @Bean
  public KafkaMessageProducer kafkaMessageProducer() {
    return CollectionUtils.isEmpty(springBootKafkaProducerConfiguration.getInitParams()) ? null : new KafkaMessageProducer(new KafkaProducer<String, String>(
            constructParams(springBootKafkaProducerConfiguration.getInitParams())));
  }

  @Bean
  public KafkaMessageConsumer kafkaMessageConsumer() {
    return CollectionUtils.isEmpty(springBootKafkaConsumerConfiguration.getInitParams()) ? null : new KafkaMessageConsumer(
            constructParams(springBootKafkaConsumerConfiguration.getInitParams()),
            springBootKafkaConsumerConfiguration.getThreadNum());
  }

  private Map<String, Object> constructParams(List<String> initParams) {
    Map<String, Object> paramMap = new HashMap<>();
    initParams.forEach(p -> {
      String[] arr = p.split("=");
      if (arr.length == 2) {
        paramMap.put(arr[0], arr[1]);
      }
    });
    return paramMap;
  }
}

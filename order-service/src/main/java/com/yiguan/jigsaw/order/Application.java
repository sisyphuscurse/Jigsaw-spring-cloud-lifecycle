package com.yiguan.jigsaw.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.yiguan"}) //Duplicated with EnableAutoConfiguration
@EnableCircuitBreaker
@EnableFeignClients
@EnableSwagger2
@EnableJpaRepositories(basePackages = "com.yiguan.jigsaw.order.repositories")
public class Application {

  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);

  }
}
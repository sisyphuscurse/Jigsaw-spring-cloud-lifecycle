package com.yiguan.coordinator.api;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class Coordinators {

  @RequestMapping(value = "transform", method = RequestMethod.POST)
  public TransformResponse transform(TransformRequest request) {
    return create(OrderBO.class, request)
        .save()
        .into(OrderCreationResp.class);
  }
}

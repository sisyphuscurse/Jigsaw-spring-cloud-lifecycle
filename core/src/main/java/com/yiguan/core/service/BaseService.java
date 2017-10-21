package com.yiguan.core.service;

import com.yiguan.core.biz.BizObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseService {
  @Autowired
  private ApplicationContext context;

  protected <B extends BizObject<B>> B createBO(Class<B> bClass, Object... request) {
    return context.getBean(bClass, request);
  }
}

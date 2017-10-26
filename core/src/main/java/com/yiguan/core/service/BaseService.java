package com.yiguan.core.service;

import com.yiguan.core.biz.BizObject;
import com.yiguan.core.transaction.Transactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseService {
  @Autowired
  protected ApplicationContext context;


  protected <RES> Transactor<RES> createTransactor(Class<RES> resClass) {
    return (Transactor<RES>) context.getBean(Transactor.class);
  }

  protected <B extends BizObject<B>> B create(Class<B> bClass, Object... request) {
    return context.getBean(bClass, request);
  }

  protected <B extends BizObject<B>> B load(Class<B> bClass, Object... request) {
    return create(bClass, request);
  }
}

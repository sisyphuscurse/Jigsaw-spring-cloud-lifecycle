package com.yiguan.core.bases;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class DomainService {

  @Autowired
  private ApplicationContext context;

  protected static final ModelMapper mapper = new ModelMapper();

  protected <B extends DomainObject<B>> B create(Class<B> bClass, Object... request) {
    return context.getBean(bClass, request);
  }

  protected <B extends DomainObject<B>> B load(Class<B> bClass, Object... request) {
    return create(bClass, request);
  }
}

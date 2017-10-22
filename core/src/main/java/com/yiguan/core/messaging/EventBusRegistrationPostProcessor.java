package com.yiguan.core.messaging;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class EventBusRegistrationPostProcessor implements BeanPostProcessor {

  @Autowired
  private EventBus eventBus;
  private Object preInitializedBean;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return this.preInitializedBean = bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    final Method[] methods;
    if (isProxied(bean)) {
      methods = this.preInitializedBean.getClass().getMethods();
    } else {
      methods = bean.getClass().getMethods();
    }
    methodsLoop:
    for (Method method : methods) {
      for (Annotation annotation : method.getAnnotations()) {
        if (annotation.annotationType().equals(Subscribe.class)) {
          //eventBus.register(isProxied(bean) ? preInitializedBean : bean);
          eventBus.register(bean);
          // the call to register() will register any method on this
          // object's class that has been annotated with @Subscribe
          // with the event bus, so we can leave now...
          break methodsLoop;
        }
      }
    }
    return bean;
  }


  private boolean isProxied(Object bean) {
    return bean instanceof java.lang.reflect.Proxy || isSingletonProxy(bean);
  }

  private boolean isSingletonProxy(Object bean) {
    try {
      bean.getClass().getDeclaredMethod("isProxyTargetClass");
      return true;
    } catch (NoSuchMethodException e) {
      return false;
    }
  }
}
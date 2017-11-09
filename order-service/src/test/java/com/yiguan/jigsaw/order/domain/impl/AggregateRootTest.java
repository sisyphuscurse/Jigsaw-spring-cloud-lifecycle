package com.yiguan.jigsaw.order.domain.impl;

import com.yiguan.core.bases.AggregateRoot;
import com.yiguan.core.persistence.Keyed;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AggregateRootTest {

  protected <B extends AggregateRoot<B, E, K>, E extends Keyed<K>, K extends Serializable> B createBizBean(Class<B> tClass, K key, CrudRepository... repositories) {
    try {
      final AggregateRoot<B, E, K> object = tClass.getConstructor(key.getClass()).newInstance(key);

      injectRepositories(tClass, object, repositories);

      final Method initialize = AggregateRoot.class.getDeclaredMethod("initialize");
      initialize.setAccessible(true);
      initialize.invoke(object);
      initialize.setAccessible(false);
      return (B) object;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private <B extends AggregateRoot<B, E, K>, E extends Keyed<K>, K extends Serializable> void injectRepositories(Class<B> tClass, AggregateRoot<B, E, K> object, CrudRepository[] repositories) throws IllegalAccessException {
    for (CrudRepository repo : repositories) {
      for (Field field : tClass.getDeclaredFields()) {
        if (field.getType().isAssignableFrom(repo.getClass())) {
          field.setAccessible(true);
          field.set(object, repo);
          field.setAccessible(false);
        }
      }
    }
  }
}

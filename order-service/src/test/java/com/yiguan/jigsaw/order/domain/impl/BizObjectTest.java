package com.yiguan.jigsaw.order.domain.impl;

import com.yiguan.core.bases.AggregateRoot;
import com.yiguan.core.persistence.Keyed;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class BizObjectTest {
  protected <B extends AggregateRoot<B, E, K>, E extends Keyed<K>, K extends Serializable> B createBizBean(Class<B> tClass, K key, CrudRepository<E, K> repository ) {
    try {
      final AggregateRoot<B, E, K> object = tClass.getConstructor(key.getClass()).newInstance(key);
      //object.setRepository(repository);
      final Method initialize = AggregateRoot.class.getDeclaredMethod("initialize");
      initialize.setAccessible(true);
      initialize.invoke(object);
      initialize.setAccessible(false);
      return (B) object;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}

package com.yiguan.jigsaw.order.biz.impl;

import com.yiguan.core.biz.BizObjectBase;
import com.yiguan.core.persistence.Keyed;
import com.yiguan.jigsaw.order.biz.entity.Order;
import com.yiguan.jigsaw.order.biz.repo.OrderRepository;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.lang.reflect.Method;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BizObjectTest {
  protected <B extends BizObjectBase<B, E, K>, E extends Keyed<K>, K extends Serializable> B createBizBean(Class<B> tClass, K key,
                                                                                                             Class<? extends CrudRepository<E, K>> repositoryClass, E internalState) {
    try {
      final BizObjectBase<B, E, K> object = tClass.getConstructor(key.getClass()).newInstance(key);
      final CrudRepository<E, K> repository = mock(repositoryClass);
      object.setRepository(repository);
      when(repository.findOne(key)).thenReturn(internalState);
      final Method initialize = BizObjectBase.class.getDeclaredMethod("initialize");
      initialize.setAccessible(true);
      initialize.invoke(object);
      initialize.setAccessible(false);
      return (B) object;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}

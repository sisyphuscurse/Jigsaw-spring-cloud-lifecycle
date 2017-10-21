package com.yiguan.jigsaw.order.biz.impl;

import com.yiguan.jigsaw.order.biz.entity.Keyed;
import net.imadz.lifecycle.LifecycleContext;
import net.imadz.lifecycle.annotations.callback.PostStateChange;
import org.modelmapper.ModelMapper;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;

public class BizObjectBase<B extends BizObjectBase<B, E, K>, E extends Keyed<K>, K extends Serializable> {
  private static final ModelMapper mapper = new ModelMapper();
  protected CrudRepository<E, K> repository;
  protected E internalState;
  private Optional<K> key;

  protected BizObjectBase(E internalState) {
    this.internalState = internalState;
    this.key = Optional.empty();
  }

  protected BizObjectBase(K key) {
    this.key = Optional.of(key);
  }

  @PostConstruct
  protected void initialize() {
    if (null == internalState) {
      internalState = repository.findOne(key.get());
    }
  }

  public <T> T into(Class<T> targetType) {
    return mapper.map(internalState, targetType);
  }

  @PostStateChange
  public void onStateChange(LifecycleContext<B, ?> context) {
    internalSave(context.getTarget().internalState);
  }

  public B save() {
    internalSave(internalState);
    return (B) this;
  }

  private void internalSave(E entity) {
    repository.save(entity);
    key = Optional.of(key.orElse(internalState.getKey()));
  }

}
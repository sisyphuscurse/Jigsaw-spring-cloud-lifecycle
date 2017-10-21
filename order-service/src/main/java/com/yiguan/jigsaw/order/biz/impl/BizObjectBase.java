package com.yiguan.jigsaw.order.biz.impl;

import net.imadz.lifecycle.LifecycleContext;
import net.imadz.lifecycle.annotations.callback.PostStateChange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;

public class BizObjectBase<B extends BizObjectBase<B, E, K>, E, K extends Serializable> {
  private static final ModelMapper mapper = new ModelMapper();
  protected CrudRepository<E, K> repository;
  protected E internalState;
  private Optional<K> key;

  protected BizObjectBase(E internalState) {
    this.internalState = internalState;
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

  protected B withInternalState(E entity) {
    internalState = entity;
    return (B) this;
  }

  public <T> T into(Class<T> targetType) {
    return mapper.map(getInternalState(), targetType);
  }

  private E getInternalState() {
    return internalState;
  }


  @PostStateChange
  public void onStateChange(LifecycleContext<B, ?> context) {
    repository.save(context.getTarget().internalState);
  }

  public B save() {
    repository.save(internalState);
    return (B) this;
  }

}

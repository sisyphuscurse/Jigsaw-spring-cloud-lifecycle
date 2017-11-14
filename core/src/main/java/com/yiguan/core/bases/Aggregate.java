package com.yiguan.core.bases;

import com.google.common.eventbus.EventBus;
import com.yiguan.core.messaging.AppLifecycleEvent;
import com.yiguan.core.persistence.Keyed;
import net.imadz.bcel.intercept.DefaultStateMachineRegistry;
import net.imadz.lifecycle.LifecycleContext;
import net.imadz.lifecycle.annotations.LifecycleMeta;
import net.imadz.lifecycle.annotations.ReactiveObject;
import net.imadz.lifecycle.annotations.callback.PostStateChange;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Aggregate<B extends Aggregate<B, E, K>, E extends Keyed<K>, K extends Serializable>  {
  private static final ExecutorService lifecycleEventHanlderThreadPool = Executors.newFixedThreadPool(4);
  protected static final ModelMapper mapper = new ModelMapper();
  @Autowired
  private EventBus eventBus;

  protected Aggregate(E internalState) {
    this.internalState = internalState;
    this.key = Optional.empty();
    this.initializeInitialState();
  }

  protected Aggregate(K key) {
    this.key = Optional.of(key);
  }

  public <T> T into(Class<T> targetType) {
    return mapper.map(internalState, targetType);
  }

  @PostStateChange
  public void onStateChange(LifecycleContext<B, ?> context) {
    internalSave(this.internalState);
  }

  @PostStateChange(priority = 20)
  public void notifyLifecycleEventObservers(LifecycleContext<B, ?> context) {
    lifecycleEventHanlderThreadPool.submit(() -> {
      final AppLifecycleEvent event = new AppLifecycleEvent(context);
      eventBus.post(event);
    });
  }

  private void initializeInitialState() {
    if (!hasStateIndicator()) {
      return;
    }
    DefaultStateMachineRegistry.getInstance().setInitialState(this);
  }

  protected boolean hasStateIndicator() {
    return hasMultipleLifecycle() || hasSingleLifecycle();
  }

  private boolean hasMultipleLifecycle() {
    return null != getClass().getAnnotation(ReactiveObject.class);
  }

  private boolean hasSingleLifecycle() {
    return null != getClass().getAnnotation(LifecycleMeta.class);
  }


  protected Optional<K> key;

  protected E internalState;

  @Autowired
  public void setRepository(CrudRepository<E, K> repository) {
    this.repository = repository;
  }

  protected CrudRepository<E, K> repository;

  @PostConstruct
  private void initialize() {
    if (null == internalState) {
      internalState = findOne(key.get());
    }
  }

  public B save() {
    internalSave(this.internalState);
    postSave();
    return (B) this;
  }

  protected void postSave() {
  }

  protected E findOne(K k){
    return repository.findOne(k);
  }

  protected void internalSave(E entity) {
    repository.save(entity);
    key = Optional.of(key.orElse(internalState.getKey()));
  }

}
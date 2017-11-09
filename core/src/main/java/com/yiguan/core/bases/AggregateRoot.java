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

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AggregateRoot<B extends AggregateRoot<B, E, K>, E extends Keyed<K>, K extends Serializable> {
  private static final ExecutorService lifecycleEventHanlderThreadPool = Executors.newFixedThreadPool(4);
  protected static final ModelMapper mapper = new ModelMapper();
  protected E internalState;
  private Optional<K> key;
  @Autowired
  private EventBus eventBus;

  protected AggregateRoot(E internalState) {
    this.internalState = internalState;
    this.key = Optional.empty();
    this.initializeInitialState();
  }

  protected AggregateRoot(K key) {
    this.key = Optional.of(key);
  }

  @PostConstruct
  protected void initialize() {
    if (null == internalState) {
      internalState = findOne(key.get());
    }
  }

  public <T> T into(Class<T> targetType) {
    return mapper.map(internalState, targetType);
  }

  public <T> T map(Object source, Class<T> targetType) {
    return mapper.map(source, targetType);
  }

  @PostStateChange
  public void onStateChange(LifecycleContext<B, ?> context) {
    internalSave(context.getTarget().internalState);
  }

  @PostStateChange(priority = 20)
  public void notifyLifecycleEventObservers(LifecycleContext<B, ?> context) {
    lifecycleEventHanlderThreadPool.submit(() -> {
      eventBus.post(new AppLifecycleEvent(context));
    });
  }

  public abstract E save(E e);
  public abstract E findOne(K k);
  public abstract E onStateChanged(E e);

  private void internalSave(E entity) {
    save(entity);
    key = Optional.of(key.orElse(internalState.getKey()));
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
}
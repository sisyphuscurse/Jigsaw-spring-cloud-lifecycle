package com.yiguan.core.messaging;

import net.imadz.lifecycle.LifecycleContext;
import net.imadz.lifecycle.LifecycleEvent;
import net.imadz.lifecycle.annotations.Event;
import net.imadz.lifecycle.meta.type.EventMetadata;
import net.imadz.utils.Null;

public class AppLifecycleEvent implements LifecycleEvent {
  private final LifecycleContext<?, ?> context;

  public AppLifecycleEvent(LifecycleContext<?, ?> context) {
    this.context = context;
  }

  @Override
  public Object getReactiveObject() {
    return context.getTarget();
  }

  @Override
  public String fromState() {
    return context.getFromStateName();
  }

  @Override
  public String toState() {
    return context.getToStateName();
  }

  @Override
  public String event() {
    final Class<?> eventClass = context.getEventMethod().getDeclaredAnnotation(Event.class).value();
    if (Null.class == eventClass) {
      return context.getEventMethod().getName();
    }
    return eventClass.getSimpleName();
  }

  @Override
  public EventMetadata.EventTypeEnum eventType() {
    return EventMetadata.EventTypeEnum.Common;
  }

  @Override
  public long startTime() {
    return 0;
  }

  @Override
  public long endTime() {
    return 0;
  }
}

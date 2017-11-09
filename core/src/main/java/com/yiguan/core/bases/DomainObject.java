package com.yiguan.core.bases;

public interface DomainObject<B extends DomainObject<B>> {
  B save();

  <T> T into(Class<T> orderCreationRespClass);
}

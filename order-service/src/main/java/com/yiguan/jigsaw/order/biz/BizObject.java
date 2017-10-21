package com.yiguan.jigsaw.order.biz;

public interface BizObject<B extends BizObject<B>> {
  B save();

  <T> T into(Class<T> orderCreationRespClass);
}

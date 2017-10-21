package com.yiguan.jigsaw.order.biz.entity;

import java.io.Serializable;

public interface Keyed<K extends Serializable> {
  K getKey();
}

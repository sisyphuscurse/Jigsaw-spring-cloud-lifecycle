package com.yiguan.core.persistence;

import java.io.Serializable;

public interface Keyed<K extends Serializable> {
  K getKey();
}

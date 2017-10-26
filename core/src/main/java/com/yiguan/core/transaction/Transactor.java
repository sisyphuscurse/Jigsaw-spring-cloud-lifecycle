package com.yiguan.core.transaction;

import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

@Transactional
public class Transactor<RES> {
  public RES execute(Callable<RES> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}

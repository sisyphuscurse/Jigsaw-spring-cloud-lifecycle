package com.yiguan.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Slf4j
@EnableAutoConfiguration
public class Useless {
  public static void main(String[] args) {
    log.debug("Just for passing maven compile spring boot parent project.");
  }
}

package com.yiguan.coordinator.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class TransformResponse {
  private final Optional<String> errorCode;
  private final Optional<String> errorMessage;
  private final Optional<TransformResult> result;

  public TransformResponse(final String errorCode, final String errorMessage) {
    this.errorCode = Optional.of(errorCode);
    this.errorMessage = Optional.of(errorMessage);
    this.result = Optional.empty();
  }

  public TransformResponse(TransformResult result) {
    this.result = Optional.of(result);
    this.errorCode = Optional.empty();
    this.errorMessage = Optional.empty();
  }
}

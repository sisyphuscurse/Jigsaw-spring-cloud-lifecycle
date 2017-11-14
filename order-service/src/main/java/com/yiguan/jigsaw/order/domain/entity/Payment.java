package com.yiguan.jigsaw.order.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Payment {

  @Column(nullable = false, name = "payment_id")
  private String paymentId;

  @Column(nullable = false, name = "payment_time")
  private String paymentTime;
}

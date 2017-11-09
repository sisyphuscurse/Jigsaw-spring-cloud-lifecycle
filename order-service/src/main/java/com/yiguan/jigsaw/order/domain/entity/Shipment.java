package com.yiguan.jigsaw.order.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Entity
@Table(name = "shipments")
public class Shipment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long oid;

  @Column(nullable = false, name = "shipping_id")
  private String shippingId;

  @Column(nullable = false, name = "shipping_time")
  private String shippingTime;

  @Column(nullable = false, name = "received_time")
  private String receivedTime;
}

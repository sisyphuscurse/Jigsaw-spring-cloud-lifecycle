package com.yiguan.jigsaw.order.domain.entity;

import com.yiguan.core.persistence.Keyed;
import com.yiguan.jigsaw.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order implements Keyed<Long> {
  @Id
  @GeneratedValue
  private Long id;

  @Version
  private Long version;

  @Column(nullable = false)
  private Integer uid;

  @Column(nullable = false, name = "total_price")
  private BigDecimal totalPrice;

  @Column(nullable = false, name = "create_time")
  private String createTime;

  @Enumerated(EnumType.STRING)
  private OrderStatus state;

  @Column(nullable = false)
  private String confirmTime;

  @Override
  public Long getKey() {
    return id;
  }


  @Transient
  private Payment payment;

  @Transient
  private Shipment shipment;
}

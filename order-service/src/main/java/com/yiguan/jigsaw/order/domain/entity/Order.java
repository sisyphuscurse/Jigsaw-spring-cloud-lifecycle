package com.yiguan.jigsaw.order.domain.entity;

import com.yiguan.core.persistence.Keyed;
import com.yiguan.jigsaw.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.List;

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

  @Column
  private String confirmTime;

  @Column(name = "payment_id")
  private String paymentId;

  @Column(name = "payment_time")
  private String paymentTime;

  @Column(name = "shipping_id")
  private String shippingId;

  @Column(name = "shipping_time")
  private String shippingTime;

  @Column(name = "received_time")
  private String receivedTime;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> items;

  @Override
  public Long getKey() {
    return id;
  }
}

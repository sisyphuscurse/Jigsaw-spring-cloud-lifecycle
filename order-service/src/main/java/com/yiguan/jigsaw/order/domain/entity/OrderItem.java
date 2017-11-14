package com.yiguan.jigsaw.order.domain.entity;

import com.yiguan.core.persistence.Keyed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
public class OrderItem implements Keyed<Long> {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private Long pid;

  private BigDecimal price;

  private Integer amount;

  @ManyToOne
  @JoinColumn(name = "oid")
  private Order order;

  @Override
  public Long getKey() {
    return id;
  }
}

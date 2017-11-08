package com.yiguan.jigsaw.order.repositories;

import com.yiguan.jigsaw.order.domain.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}

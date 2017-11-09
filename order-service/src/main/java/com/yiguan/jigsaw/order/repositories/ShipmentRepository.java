package com.yiguan.jigsaw.order.repositories;

import com.yiguan.jigsaw.order.domain.entity.Shipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ShipmentRepository extends CrudRepository<Shipment, Integer> {
  Shipment findByOid(Long oid);

}

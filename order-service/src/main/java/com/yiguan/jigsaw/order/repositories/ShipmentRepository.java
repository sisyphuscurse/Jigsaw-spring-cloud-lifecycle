package com.yiguan.jigsaw.order.repositories;

import com.yiguan.jigsaw.order.domain.entity.Shipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
  Shipment findByOid(Long oid);

}

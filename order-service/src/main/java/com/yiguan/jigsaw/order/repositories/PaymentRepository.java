package com.yiguan.jigsaw.order.repositories;


import com.yiguan.jigsaw.order.domain.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PaymentRepository extends CrudRepository<Payment, Long> {

  Payment findByOid(Long oid);

}

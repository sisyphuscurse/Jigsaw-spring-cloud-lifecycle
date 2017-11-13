package com.yiguan.jigsaw.order.domain;

import com.yiguan.core.bases.Persistable;
import com.yiguan.jigsaw.order.domain.entity.OrderItem;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class OrderItemBean extends Persistable<OrderItemBean, OrderItem, Long> {

}

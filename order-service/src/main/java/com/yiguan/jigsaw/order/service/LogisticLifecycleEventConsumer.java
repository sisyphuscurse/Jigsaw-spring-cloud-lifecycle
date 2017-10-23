package com.yiguan.jigsaw.order.service;

import com.google.common.eventbus.Subscribe;
import com.yiguan.core.service.BaseService;
import com.yiguan.jigsaw.order.biz.OrderBO;
import com.yiguan.jigsaw.order.service.event.inbound.ArtifactShippingStarted;
import com.yiguan.jigsaw.order.service.event.inbound.ArtifactSigned;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogisticLifecycleEventConsumer extends BaseService {

  @Subscribe
  public void onArtifactShippingStarted(ArtifactShippingStarted shippingStarted) {
    load(OrderBO.class, shippingStarted.getOrderId())
        .shippingStarted(shippingStarted);
  }

  @Subscribe
  public void onArtifactSignedByCustomer(ArtifactSigned artifactSigned) {
    load(OrderBO.class, artifactSigned.getOrderId())
        .signedByCustomer(artifactSigned);
  }


}

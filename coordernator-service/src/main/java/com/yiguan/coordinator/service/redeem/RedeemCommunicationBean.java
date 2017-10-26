package com.yiguan.coordinator.service.redeem;

import com.yiguan.coordinator.service.redeem.fsm.RedeemFSM;
import com.yiguan.coordinator.service.redeem.fsm.RedeemFSM.Relations.TransformerTx;
import com.yiguan.coordinator.service.transform.TransformService;
import net.imadz.lifecycle.annotations.LifecycleMeta;
import net.imadz.lifecycle.annotations.relation.Relation;

@LifecycleMeta(RedeemFSM.class)
public class RedeemCommunicationBean extends BaseCommunicationBean implements RedeemCommunication {

  private final TransformService transformer;

  public RedeemCommunicationBean(TransformService transformService) {
    this.transformer = transformService;
  }

  @Override
  public void begin() {
  }

  @Override
  public void fail(Exception ex) {
  }

  @Override
  public void onSuccess() {
  }

  @Relation(TransformerTx.class)
  public TransformService getTransformer() {
    return transformer;
  }
}

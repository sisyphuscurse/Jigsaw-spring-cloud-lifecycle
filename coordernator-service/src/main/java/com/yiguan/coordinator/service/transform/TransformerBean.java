package com.yiguan.coordinator.service.transform;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class TransformerBean implements Transformer {

  @Override
  public void totalQuotaLockFailed() {
  }

  //Action
  @Override
  public void startRedeemProcess() {
  }

  //Event from Redeem Process
  @Override
  public void redeemProcessSucceeded() {
  }

  //Event from Redeem Process
  @Override
  public void redeemProcessFailed() {
  }

  //Event from Redeem Process
  @Override
  public void redeemStatusUndetermined() {
  }

  //Action
  @Override
  public void startPurchaseProcess() {
  }

  //Event from Purchase Process
  @Override
  public void purchaseProcessSucceeded() {
  }

  //Event from Purchase Process
  @Override
  public void purchaseProcessFailed() {
  }

  //Event from Purchase Process
  @Override
  public void purchaseStatusUndetermined() {
  }
}

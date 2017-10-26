package com.yiguan.coordinator.service.transform;

import com.yiguan.coordinator.service.redeem.RedeemCommunicationBean;
import net.imadz.lifecycle.LifecycleContext;

public interface Transformer {
  String totalQuotaLockFailed();

  //Action
  void startRedeemProcess();

  //Event from Redeem Process
  void redeemProcessSucceeded(LifecycleContext<RedeemCommunicationBean, String> context);

  //Event from Redeem Process
  void redeemProcessFailed(LifecycleContext<RedeemCommunicationBean, String> context);

  //Event from Redeem Process
  void redeemStatusUndetermined(LifecycleContext<RedeemCommunicationBean, String> context);

  //Action
  void startPurchaseProcess();

  //Event from Purchase Process
  void purchaseProcessSucceeded();

  //Event from Purchase Process
  void purchaseProcessFailed();

  //Event from Purchase Process
  void purchaseStatusUndetermined();
}

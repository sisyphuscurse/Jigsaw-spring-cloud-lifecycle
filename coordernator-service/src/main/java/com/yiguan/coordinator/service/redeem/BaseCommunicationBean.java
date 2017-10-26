package com.yiguan.coordinator.service.redeem;

import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM;
import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM.Events.Begin;
import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM.Events.Fail;
import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM.Events.Succeed;
import net.imadz.lifecycle.annotations.Event;
import net.imadz.lifecycle.annotations.LifecycleMeta;

@LifecycleMeta(TransactionFSM.class)
public abstract class BaseCommunicationBean<RESULT> {

  public void perform() {
    try {
      begin();
      onSuccess();
    } catch (Exception ex) {
      fail(ex);
    }
  }

  @Event(Begin.class)
  public abstract void begin();

  @Event(Fail.class)
  public abstract void fail(Exception ex);

  @Event(Succeed.class)
  public abstract void onSuccess();
}

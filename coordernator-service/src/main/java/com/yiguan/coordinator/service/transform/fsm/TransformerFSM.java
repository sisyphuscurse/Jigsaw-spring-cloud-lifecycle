package com.yiguan.coordinator.service.transform.fsm;

/*
    validateRequest(uid, transPassword, paymentType, fundSubEAccNo, productName, amount)
      .flatMap[TransformContext](verifyTransPassword)
      .flatMap[TransformContext](fetchAccountId)
      .map[TransformContext](toTransformContext(historyDAO))

      State: New
      ---

    //可以同步完成不需要recover的操作，可以不记录状态
    #################################################################
    # State: LockingTotalPurchaseQuota
    # ---
    # Behavior: .flatMap[TransformContext](lockTotalPurchaseQuota)
    #
    #
    # State: LockTotalPurchaseQuotaSuccess | LockTotalPurchaseQuotaFailed
    # ---
    #################################################################

      State: RedeemExecuting
      ---
      Behavior: .flatMap[TransformContext](redeem(historyDAO))


      State: RedeemSuccessful | RedeemFailed | RedeemToBeConfirmed
      ---
          Before Exit RedeemSuccessful Log and Start PostProcesses:
          1. 账单
          2. 短信通知任务

      ---
      State: PurchaseProcessing
      ---
      Behavior: .flatMap[TransformContext](purchase(historyDAO))

      State: PurchaseSuccessful | PurchaseFailed | PurchaseToBeConfirmed
      ---
          Before Exit PurchaseSuccessful State, Log and Start Processes:
          1. 账单通知
          2. 短信通知

      .recoverWith[TransformContext](processFailure(OperationType.New))
 */

import net.imadz.lifecycle.annotations.EventSet;
import net.imadz.lifecycle.annotations.StateMachine;
import net.imadz.lifecycle.annotations.StateSet;
import net.imadz.lifecycle.annotations.Transition;
import net.imadz.lifecycle.annotations.Transitions;
import net.imadz.lifecycle.annotations.state.Final;
import net.imadz.lifecycle.annotations.state.Initial;

@StateMachine
public interface TransformerFSM {

  @StateSet
  static interface States {

    @Initial
    @Transitions({
        @Transition(event = Events.StartRedeemProcess.class, value = RedeemProcessing.class),
        @Transition(event = Events.TotalQuotaLockFailed.class, value = Failed.class)
    })
    static class New {}

    @Transitions({
        @Transition(event = Events.RedeemProcessSucceeded.class, value = RedeemSuccessful.class),
        @Transition(event = Events.RedeemProcessFailed.class, value = RedeemFailed.class),
        @Transition(event = Events.RedeemStatusUndetermined.class, value = RedeemToBeConfirmed.class)
    })
    static class RedeemProcessing {}

    @Transition(event = Events.StartPurchaseProcess.class, value = PurchaseProcessing.class)
    static class RedeemSuccessful {}

    @Transitions({
        @Transition(event = Events.RedeemProcessSucceeded.class, value = RedeemSuccessful.class),
        @Transition(event = Events.RedeemProcessFailed.class, value = RedeemFailed.class)
    })
    static class RedeemToBeConfirmed {}

    @Transitions({
        @Transition(event = Events.PurchaseProcessSucceeded.class, value = PurchaseSuccessful.class),
        @Transition(event = Events.PurchaseProcessFailed.class, value = PurchaseFailed.class),
        @Transition(event = Events.PurchaseStatusUndetermined.class, value = PurchaseToBeConfirmed.class)
    })
    static class PurchaseProcessing {}

    @Transitions({
        @Transition(event = Events.PurchaseProcessSucceeded.class, value = PurchaseSuccessful.class),
        @Transition(event = Events.PurchaseProcessFailed.class, value = PurchaseFailed.class)
    })
    static class PurchaseToBeConfirmed {}

    @Final
    static class PurchaseSuccessful {}

    @Final
    static class PurchaseFailed {}

    @Final
    static class RedeemFailed {}

    @Final
    static class Failed {}
  }

  @EventSet
  static interface Events {

    static class TotalQuotaLockFailed {}

    //Action
    static class StartRedeemProcess {}

    //Event from Redeem Process
    static class RedeemProcessSucceeded {}

    //Event from Redeem Process
    static class RedeemProcessFailed {}

    //Event from Redeem Process
    static class RedeemStatusUndetermined {}

    //Action
    static class StartPurchaseProcess {}

    //Event from Purchase Process
    static class PurchaseProcessSucceeded {}

    //Event from Purchase Process
    static class PurchaseProcessFailed {}

    //Event from Purchase Process
    static class PurchaseStatusUndetermined {}


  }

}

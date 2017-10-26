package com.yiguan.coordinator.service.transform;

import com.yiguan.coordinator.service.redeem.RedeemCommunicationBean;
import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM.States.Failed;
import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM.States.Processing;
import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM.States.Succeeded;
import com.yiguan.coordinator.service.transform.fsm.TransformerFSM;
import com.yiguan.coordinator.service.transform.fsm.TransformerFSM.Events.RedeemProcessSucceeded;
import com.yiguan.core.service.BaseService;
import net.imadz.lifecycle.LifecycleContext;
import net.imadz.lifecycle.annotations.Event;
import net.imadz.lifecycle.annotations.LifecycleMeta;
import net.imadz.lifecycle.annotations.callback.PostStateChange;
import org.springframework.stereotype.Service;


@Service
@LifecycleMeta(TransformerFSM.class)
public class TransformService extends BaseService implements Transformer {

  private Transformer bean = context.getBean(Transformer.class);

  private RedeemCommunicationBean redeemCommunicationBean;

  @Event(TransformerFSM.Events.TotalQuotaLockFailed.class)
  public String totalQuotaLockFailed() {
    return createTransactor(String.class).execute(() -> {
      return "";
    });
  }

  //Action
  @Event(TransformerFSM.Events.StartRedeemProcess.class)
  public void startRedeemProcess() {
    //create RedeemProcess
    //TODO 可以考虑按照BO形式实现BizObject
    redeemCommunicationBean = createBean(RedeemCommunicationBean.class, this);
    //save RedeemProcess
  }

  @PostStateChange(to = TransformerFSM.States.RedeemProcessing.class)
  public void onRedeemProcessing(LifecycleContext<TransformService, String> context) {
    redeemCommunicationBean.perform();
  }

  //Event from Redeem Process
  @Event(RedeemProcessSucceeded.class)
  @PostStateChange(to = Succeeded.class, mappedBy = "transformer", observableClass = RedeemCommunicationBean.class)
  public void redeemProcessSucceeded(LifecycleContext<RedeemCommunicationBean, String> context) {
    createTransactor(Void.class).execute(() -> {
          return null;
        }
    );
  }

  //Event from Redeem Process
  @Event(TransformerFSM.Events.RedeemProcessFailed.class)
  @PostStateChange(to = Failed.class, mappedBy = "transformer", observableClass = RedeemCommunicationBean.class)
  public void redeemProcessFailed(LifecycleContext<RedeemCommunicationBean, String> context) {
    createTransactor(Void.class).execute(() -> {
          return null;
        }
    );
  }

  //Event from Redeem Process
  @Event(TransformerFSM.Events.RedeemStatusUndetermined.class)
  @PostStateChange(from = Processing.class, to = Processing.class, mappedBy = "transformer",
      observableClass = RedeemCommunicationBean.class)
  public void redeemStatusUndetermined(LifecycleContext<RedeemCommunicationBean, String> context) {
    createTransactor(Void.class).execute(() -> {
          return null;
        }
    );
  }

  //Action
  @Event(TransformerFSM.Events.StartPurchaseProcess.class)
  public void startPurchaseProcess() {
  }

  //Event from Purchase Process
  @Event(TransformerFSM.Events.PurchaseProcessSucceeded.class)
  public void purchaseProcessSucceeded() {
  }

  //Event from Purchase Process
  @Event(TransformerFSM.Events.PurchaseProcessFailed.class)
  public void purchaseProcessFailed() {
  }

  //Event from Purchase Process
  @Event(TransformerFSM.Events.PurchaseStatusUndetermined.class)
  public void purchaseStatusUndetermined() {
  }


  public <BEAN> BEAN createBean(Class<BEAN> beanClass, Object... args) {
    return null;
  }

}

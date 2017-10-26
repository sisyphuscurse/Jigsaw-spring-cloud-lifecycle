package com.yiguan.coordinator.service.purchase.fsm;

import com.yiguan.coordinator.service.transaction.fsm.TransactionFSM;
import com.yiguan.coordinator.service.transform.fsm.TransformerFSM;
import net.imadz.lifecycle.annotations.StateMachine;
import net.imadz.lifecycle.annotations.relation.RelateTo;
import net.imadz.lifecycle.annotations.relation.RelationSet;

@StateMachine
public interface PurchaseFSM extends TransactionFSM {

  @RelationSet
  static interface Relations {

    @RelateTo(TransformerFSM.class)
    static class TransformerTx {}
  }
}
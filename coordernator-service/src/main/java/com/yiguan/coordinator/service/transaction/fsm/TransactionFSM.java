package com.yiguan.coordinator.service.transaction.fsm;

import net.imadz.lifecycle.annotations.EventSet;
import net.imadz.lifecycle.annotations.StateMachine;
import net.imadz.lifecycle.annotations.StateSet;
import net.imadz.lifecycle.annotations.Transition;
import net.imadz.lifecycle.annotations.Transitions;
import net.imadz.lifecycle.annotations.state.Final;
import net.imadz.lifecycle.annotations.state.Initial;

@StateMachine
public interface TransactionFSM {

  @StateSet
  static interface States {

    @Initial
    @Transition(event = Events.Begin.class, value = Processing.class)
    static class Persisted {}

    @Transitions({
        @Transition(event = Events.Succeed.class, value = Succeeded.class),
        @Transition(event = Events.Fail.class, value = Failed.class)
    })
    static class Processing {}

    @Final
    static class Succeeded {}

    @Final
    static class Failed {}
  }

  @EventSet
  static interface Events {

    static class Begin {}

    static class Succeed {}

    static class Fail {}
  }
}

package com.yiguan.jigsaw.order.domain.fsm;

import net.imadz.lifecycle.annotations.EventSet;
import net.imadz.lifecycle.annotations.StateMachine;
import net.imadz.lifecycle.annotations.StateSet;
import net.imadz.lifecycle.annotations.Transition;
import net.imadz.lifecycle.annotations.Transitions;
import net.imadz.lifecycle.annotations.state.Final;
import net.imadz.lifecycle.annotations.state.Initial;

@StateMachine
public interface OrderFSM {
  @StateSet
  static interface States {

    @Initial
    @Transitions({
        @Transition(event = Events.OrderPaid.class, value = Paid.class),
        @Transition(event = Events.Cancel.class, value = Cancelled.class)
    })
    static class Created {}

    @Transition(event = Events.ShippingStarted.class, value = InDelivery.class)
    static class Paid {}

    @Transition(event = Events.SignedByCustomer.class, value = Received.class)
    static class InDelivery {}

    @Transition(event = Events.Accept.class, value = Accepted.class)
    static class Received {}

    @Final
    static class Accepted {}

    @Final
    static class Cancelled {}
  }

  @EventSet
  static interface Events {

    static class OrderPaid {}

    static class ShippingStarted {}

    static class SignedByCustomer {}

    static class Accept {}

    static class Cancel {}
  }
}

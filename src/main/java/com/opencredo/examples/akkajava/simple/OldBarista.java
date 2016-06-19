package com.opencredo.examples.akkajava.simple;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import com.opencredo.examples.akkajava.Coffee;
import com.opencredo.examples.akkajava.Order;

public class OldBarista extends UntypedActor {
    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private final CoffeeMachine coffeeMachine;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Order) {
            Order order = (Order) message;
            log.info("Received an order for {}", order);

            Coffee coffee = coffeeMachine.prepare(order.type);
            log.info("Your {} is ready", coffee);

            getSender().tell(coffee, getSelf());
        } else {
            unhandled(message);
        }
    }

    public static Props props() {
        return Props.create(new Creator<OldBarista>() {
            private static final long serialVersionUID = 1L;

            @Override
            public OldBarista create() throws Exception {
                return new OldBarista();
            }
        });
    }

    private OldBarista() {
        coffeeMachine = new CoffeeMachine();
        log.info("The coffee machine is ready");
    }
}

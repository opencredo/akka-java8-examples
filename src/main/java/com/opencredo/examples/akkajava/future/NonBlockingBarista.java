package com.opencredo.examples.akkajava.future;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import com.opencredo.examples.akkajava.Order;

import static akka.japi.pf.ReceiveBuilder.match;
import static akka.pattern.PatternsCS.pipe;

public class NonBlockingBarista extends AbstractLoggingActor {


    public static Props props() {
        return Props.create(NonBlockingBarista.class, () -> new NonBlockingBarista());
    }

    private NonBlockingBarista() {
        final SlowCoffeeMachine coffeeMachine = new SlowCoffeeMachine();
        log().info("The coffee machine is ready");

        receive(
                match(Order.class, order -> {
                    final String customerName = sender().path().elements().last();
                    log().info("Yes {}, I'm preparing your {}", customerName, order);

                    pipe(coffeeMachine.prepare(order.type)
                            .thenApply(coffee -> {
                                log().info("{}, your {} is ready", customerName, coffee);
                                return coffee;
                            }), context().dispatcher())
                            .to(sender(), self());


                })
                .matchAny(this::unhandled)
                .build()
        );
    }


}

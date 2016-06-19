package com.opencredo.examples.akkajava.simple;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import com.opencredo.examples.akkajava.Coffee;
import com.opencredo.examples.akkajava.Order;

import static akka.japi.pf.ReceiveBuilder.match;

public class Barista extends AbstractLoggingActor {

    public static Props props() {
        return Props.create(Barista.class, () -> new Barista());
    }

    private Barista() {
        final CoffeeMachine coffeeMachine = new CoffeeMachine();
        log().info("The coffee machine is ready");

        receive(
                match(Order.class, order -> {

                    log().info("Yes {}, I'm preparing your {}", customerName(), order);

                    final Coffee coffee = coffeeMachine.prepare(order.type);
                    log().info("{}, your {} is ready", customerName(), coffee);

                    sender().tell(coffee, self());
                })
                .matchAny(this::unhandled)
                .build()
        );
    }


    public String customerName() {
        return sender().path().elements().last();
    }
}

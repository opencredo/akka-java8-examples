package com.opencredo.examples.akkajava.become;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.opencredo.examples.akkajava.Coffee;
import com.opencredo.examples.akkajava.Order;
import com.opencredo.examples.akkajava.simple.CoffeeMachine;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.concurrent.TimeUnit;

import static akka.japi.pf.ReceiveBuilder.match;

public class DiligentBarista extends AbstractLoggingActor {

    public static Props props() {
        return Props.create(DiligentBarista.class, () -> new DiligentBarista());
    }


    @Override
    public void preStart() throws Exception {
        ActorSystem system = context().system();
        system.scheduler().scheduleOnce(Duration.create(7, TimeUnit.SECONDS),
                self(),
                new CoffeeMachineReady(),
                system.dispatcher(),
                self());
    }

    private PartialFunction<Object, BoxedUnit> readyToPrepareCoffee;

    private DiligentBarista() {
        final CoffeeMachine coffeeMachine = new CoffeeMachine();
        log().info("Turning on the coffee machine");

        receive(
                match(Order.class, order -> {
                    log().info("Sorry {}, the machine is still warming up", customerName());
                    sender().tell(new PleaseWait(), self());
                })
                        .match(CoffeeMachineReady.class, s -> {
                            log().info("The machine is now ready");
                            context().become(readyToPrepareCoffee);
                        })
                        .matchAny(this::unhandled)
                        .build());

        readyToPrepareCoffee =
                match(Order.class, order -> {
                    log().info("Yes {}, I'm preparing your {}", customerName(), order);
                    final Coffee coffee = coffeeMachine.prepare(order.type);
                    log().info("{}, your {} is ready", customerName(), coffee);
                    sender().tell(coffee, self());
                })
                        .matchAny(this::unhandled)
                        .build();
    }

    public String customerName() {
        return sender().path().elements().last();
    }

    public static class PleaseWait {
    }

    public static class CoffeeMachineReady {
    }
}

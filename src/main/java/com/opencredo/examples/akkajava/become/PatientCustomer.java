package com.opencredo.examples.akkajava.become;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.opencredo.examples.akkajava.Coffee;
import com.opencredo.examples.akkajava.Order;
import scala.concurrent.duration.Duration;

import static com.opencredo.examples.akkajava.become.DiligentBarista.PleaseWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static akka.japi.pf.ReceiveBuilder.match;

public class PatientCustomer extends AbstractLoggingActor {

    private final ActorRef barista;
    private final Order order;


    @Override
    public void preStart() throws Exception {
        orderCoffee();
    }

    private PatientCustomer(final ActorRef barista) {
        this.barista = barista;
        order = decideOrder();

        receive(
                match(Coffee.class, coffee -> {
                    log().info("Enjoying my {}", coffee);
                    goodbye();
                })
                        .match(PleaseWait.class, s -> {
                            log().info("OK, I'll retry in a while...");
                            ActorSystem system = context().system();
                            system.scheduler().scheduleOnce(Duration.create(2, TimeUnit.SECONDS),
                                    self(),
                                    new LetsRetry(),
                                    system.dispatcher(),
                                    self());
                        })
                        .match(LetsRetry.class, s -> {
                            orderCoffee();
                        })
                        .matchAny(this::unhandled)
                        .build()
        );
    }

    private void orderCoffee() {
        log().info("{}, please", order);
        barista.tell(order, self());
    }

    private void goodbye() {
        log().info("Goodbye");
        context().stop(self());
    }

    public static Props props(final ActorRef barista) {
        return Props.create(PatientCustomer.class, () -> new PatientCustomer(barista));
    }

    public static class LetsRetry {
    }

    private Order decideOrder() {
        // A considered decision...
        return Order.of(Coffee.types[new Random().nextInt(Coffee.types.length)]);
    }

}

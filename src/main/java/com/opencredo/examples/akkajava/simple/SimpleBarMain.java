package com.opencredo.examples.akkajava.simple;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.opencredo.examples.akkajava.Customer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class SimpleBarMain {

    public static void main(String[] args) throws Exception {
        final Config config = ConfigFactory.load();
        final ActorSystem system = ActorSystem.create("SimpleBarSystem", config);
        try {
            // Create a Barista
            ActorRef barista = system.actorOf(Barista.props(), "Barista");
//            ActorRef barista = system.actorOf(OldBarista.props(), "Old_Barista");

            // Spawn customers
            system.actorOf(Customer.props(barista), "Alice");
            system.actorOf(Customer.props(barista), "Bob");
            system.actorOf(Customer.props(barista), "Charlie");

        } catch (Exception e) {
            system.terminate();
            throw e;
        }
    }
}

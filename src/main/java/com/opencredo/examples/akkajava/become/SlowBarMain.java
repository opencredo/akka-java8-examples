package com.opencredo.examples.akkajava.become;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class SlowBarMain {

    public static void main(String[] args) throws Exception {
        final Config config = ConfigFactory.load();
        final ActorSystem system = ActorSystem.create("SimpleBarSystem", config);
        try {
            // Create a Barista
            ActorRef barista = system.actorOf(DiligentBarista.props(), "Diligent_Barista");

            // Spawn customers
            system.actorOf(PatientCustomer.props(barista), "Alice");
            system.actorOf(PatientCustomer.props(barista), "Bob");
            system.actorOf(PatientCustomer.props(barista), "Charlie");

        } catch (Exception e) {
            system.terminate();
            throw e;
        }
    }
}

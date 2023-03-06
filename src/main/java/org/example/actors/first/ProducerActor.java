package org.example.actors.first;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;


import java.time.Duration;


public class ProducerActor extends AbstractBehavior<String> {

    private final ActorRef<String> consumerActor;

    public ProducerActor(ActorContext<String> context) {
        super(context);
        this.consumerActor =
                getContext().spawn(ConsumerActor.create(), "consumer-actor");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("start", this::start)
                .onMessageEquals("qwerty", this::sendToConsumer)
                .build();
    }

    private Behavior<String> sendToConsumer() {
        consumerActor.tell("message for consumer");
        System.out.println("We send message for consumer");
        getContext().getLog().debug("We send message for consumer");
        getContext().scheduleOnce(Duration.ofMillis(10000), getContext().getSelf(), "qwerty");
        return Behaviors.same();
    }

    private Behavior<String> start() {
        System.out.println("Method start running..");
        getContext().getLog().debug("Method start running..");
        getContext().scheduleOnce(Duration.ofMillis(10000), getContext().getSelf(), "qwerty");
        return Behaviors.same();
    }

    public static Behavior<String> create() {
        return Behaviors.setup(ProducerActor::new);
    }

}

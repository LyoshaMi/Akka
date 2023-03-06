package org.example.actors.first;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.example.actors.first.models.MessageRequest;

public class OperatorActor extends AbstractBehavior<MessageRequest> {
    public OperatorActor(ActorContext<MessageRequest> context) {
        super(context);
    }

    @Override
    public Receive<MessageRequest> createReceive() {
        return newReceiveBuilder()
                .onMessage(MessageRequest.class, this::printIt)
                .build();

    }

    static Behavior<MessageRequest> create() {
        return Behaviors.setup(OperatorActor::new);
    }

    private Behavior<MessageRequest> printIt(MessageRequest msg) {
        System.out.println(getContext().getSystem().printTree());
        System.out.println("Second: " + msg.getMessage());
        return this;
    }
}

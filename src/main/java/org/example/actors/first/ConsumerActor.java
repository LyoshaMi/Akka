package org.example.actors.first;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.example.actors.first.models.MessageRequest;


import java.util.UUID;

public class ConsumerActor extends AbstractBehavior<String> {


    static Behavior<String> create() {
        return Behaviors.setup(ConsumerActor::new);
    }

    public ConsumerActor(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("message for consumer", this::sendMsgToOperator)
                .build();
    }

    private Behavior<String> sendMsgToOperator() {

        System.out.println("send msg to operator method work");
        getContext().getLog().debug("send msg to operator method work");

        // У каждого актора уникальное имя
        ActorRef<MessageRequest> firstRef = getContext().spawn(OperatorActor.create(), "operator-actor"+ UUID.randomUUID());
        firstRef.tell(new MessageRequest("message for operator"));
        return Behaviors.same();
    }
//
//    private Behavior<OperatorCommand> sendMsgToOperator(MessageRequest msg) {
//        ActorRef<String> firstRef = getContext().spawn(OperatorActor.create(), "first-actor");
//
//        System.out.println("First: " + firstRef);
//        firstRef.tell("printit");
//        return Behaviors.same();
//    }



}

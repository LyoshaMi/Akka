package org.example.actors.second;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.UUID;

public class Cat extends AbstractBehavior<CatCommand> {
    private final ActorRef<HouseCommand> houseCommandActorRef;
    public Cat(ActorContext<CatCommand> context, ActorRef<HouseCommand> houseCommandActorRef) {
        super(context);
        this.houseCommandActorRef = houseCommandActorRef;
    }

    @Override
    public Receive<CatCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(EatMouseCommand.class, this::thanksMethod)
                .onMessage(CatPostCommand.class, this::request)
                .build();
    }

    private Behavior<CatCommand> request(CatPostCommand catPostCommand) {
        System.out.println("POST METHOD IN CAT");
        catPostCommand.getReplyTo().tell(new HttpResponseCommand(catPostCommand.getBody()));
        return Behaviors.same();
    }


    private Behavior<CatCommand> thanksMethod(EatMouseCommand eatMouseCommand) {
        System.out.println("We catch message from CatHouse");
//        houseCommandActorRef.tell(new ThankYouCommand("thanks dad love u"));
        eatMouseCommand.getHouseCommandActorRef().tell(new ThankYouCommand("thanks dad love u"));
        System.out.println("we send thank message to CatHouse from Cat");
        return Behaviors.same();
    }

    static Behavior<CatCommand> create(ActorRef<HouseCommand> houseCommandActorRef) {

        return Behaviors.setup(context -> new Cat(context, houseCommandActorRef));
    }
}

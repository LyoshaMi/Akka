package org.example.actors.second;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.time.Duration;
import java.util.UUID;

public class CatHouse extends AbstractBehavior<HouseCommand> {
    public CatHouse(ActorContext<HouseCommand> context) {
        super(context);
        this.catCommandActorRef = getContext().spawn(Cat.create(getContext().getSelf()), "cat" + UUID.randomUUID());
    }
    private final ActorRef<CatCommand> catCommandActorRef;
    @Override
    public Receive<HouseCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(HouseCommandToCat.class, this::sendMsgToCat)
                .onMessage(ThankYouCommand.class, this::loveYouCat)
                .onMessage(RequestHttpCommand.class, this::requestHttpCommand)
                .build();

    }

    private Behavior<HouseCommand> requestHttpCommand(RequestHttpCommand command) {
        ActorRef<CatCommand> catCommand = getContext().spawn(Cat.create(getContext().getSelf()), "cat" + UUID.randomUUID());
        catCommand.tell(new CatPostCommand(command.getReplyTo(), command.getBody()));
        return Behaviors.same();
    }


    private  Behavior<HouseCommand> loveYouCat(ThankYouCommand thankYouCommand) {
        System.out.println("We catch thank message from Cat");
        return Behaviors.same();
    }

    private  Behavior<HouseCommand> sendMsgToCat(HouseCommandToCat houseCommandToCat) {
        ActorRef<CatCommand> catRef = getContext().spawn(Cat.create(getContext().getSelf()), "cat" + UUID.randomUUID());
        catRef.tell(new EatMouseCommand(getContext().getSelf(),"this message for Cat actor"));
        System.out.println("we send msg for Cat actor from CatHouse");
        getContext().scheduleOnce(Duration.ofMillis(5000), getContext().getSelf(), new HouseCommandToCat());
        return Behaviors.same();
    }

    public static Behavior<HouseCommand> create() {
        return Behaviors.setup(CatHouse::new);
    }
}

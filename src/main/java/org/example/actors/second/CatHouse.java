package org.example.actors.second;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class CatHouse extends AbstractBehavior<HouseCommand> {
    public CatHouse(ActorContext<HouseCommand> context) {
        super(context);
        this.catCommandActorRef = getContext().spawn(Cat.create(getContext().getSelf()), "cat" + UUID.randomUUID());
    }
    private final ActorRef<CatCommand> catCommandActorRef;
    @Override
    public Receive<HouseCommand> createReceive() {
        return newReceiveBuilder()
//                .onMessage(HouseInit.class, this::createMsgToCat)
                .onMessage(HouseCommandToCat.class, this::sendMsgToCat)
                .onMessage(ThankYouCommand.class, this::loveYouCat)
                .onMessage(HealthCheckCommand.class, this::sendHealthCheckToCat)
                .build();

    }

    private Behavior<HouseCommand> sendHealthCheckToCat(HealthCheckCommand healthCheckCommand) {

        return Behaviors.same();
    }


    private CompletionStage<HealthCheckCommand> healthCheck() {
        ActorRef<CatCommand> catRef = getContext().spawn(Cat.create(getContext().getSelf()), "cat" + UUID.randomUUID());
        ActorRef<HouseCommand> houseCommandActorRef = getContext().getSelf();
        CompletionStage<HealthCheckCommand> askPattern = AskPattern.ask(
                catRef,
                (replyTo) -> new CatHealth(houseCommandActorRef),
                Duration.ofSeconds(3),
                getContext().getSystem().scheduler()
        );
        return askPattern;
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




//    private Behavior<HouseCommand> createMsgToCat(HouseInit houseInit) {
//
//        getContext().scheduleOnce(Duration.ofMillis(10000), getContext().getSelf(), );
//        return Behaviors.same();
//    }

    public static Behavior<HouseCommand> create() {
        return Behaviors.setup(CatHouse::new);
    }
}

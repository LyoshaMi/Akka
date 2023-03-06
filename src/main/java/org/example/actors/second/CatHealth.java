package org.example.actors.second;

import akka.actor.typed.ActorRef;

public class CatHealth implements CatCommand{
    private ActorRef<HouseCommand> houseCommandActorRef;

    public CatHealth(ActorRef<HouseCommand> houseCommandActorRef) {
        this.houseCommandActorRef = houseCommandActorRef;
    }

    public ActorRef<HouseCommand> getHouseCommandActorRef() {
        return houseCommandActorRef;
    }

    public void setHouseCommandActorRef(ActorRef<HouseCommand> houseCommandActorRef) {
        this.houseCommandActorRef = houseCommandActorRef;
    }
}

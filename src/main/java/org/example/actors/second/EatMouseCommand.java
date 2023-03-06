package org.example.actors.second;

import akka.actor.typed.ActorRef;

import java.util.UUID;

public class EatMouseCommand implements CatCommand{

    private ActorRef<HouseCommand> houseCommandActorRef;
    private String command;

    public EatMouseCommand(ActorRef<HouseCommand> houseCommandActorRef, String command) {
        this.houseCommandActorRef = houseCommandActorRef;
        this.command = command;
    }

    public ActorRef<HouseCommand> getHouseCommandActorRef() {
        return houseCommandActorRef;
    }

    public void setHouseCommandActorRef(ActorRef<HouseCommand> houseCommandActorRef) {
        this.houseCommandActorRef = houseCommandActorRef;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

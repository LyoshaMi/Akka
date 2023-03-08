package org.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import org.example.actors.second.CatHouse;
import org.example.actors.second.Controller;
import org.example.actors.second.HouseCommand;
import org.example.actors.second.HouseCommandToCat;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import static akka.http.javadsl.server.Directives.*;


public class Main {
    public static void main(String[] args) {
        ActorRef<HouseCommand> catHouse = ActorSystem.create(CatHouse.create(), "cathouse-actor");
        ActorRef<HouseCommand> controller = ActorSystem.create(Controller.create(catHouse), "controller-actor");
    }

}
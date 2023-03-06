package org.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.server.Route;
import org.example.actors.first.ProducerActor;
import org.example.actors.second.CatHouse;
import org.example.actors.second.HouseCommand;
import org.example.actors.second.HouseCommandToCat;

import java.util.concurrent.CompletionStage;
import static akka.http.javadsl.server.Directives.*;


public class Main {
    public static void main(String[] args) {
//        ActorRef<String> producer = ActorSystem.create(ProducerActor.create(), "producer-actor");
//        producer.tell("start");
        ActorRef<HouseCommand> producer = ActorSystem.create(CatHouse.create(), "cathouse-actor");
        producer.tell(new HouseCommandToCat());
        Main main = new Main();

        ActorSystem<Void> routesSystem = ActorSystem.create(Behaviors.empty(), "routes");
        final Http http = Http.get(routesSystem);
        CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8080)
                .bind(main.healthRoute());




    }

    private Route healthRoute() {
        return concat(
                path("health", () ->
                        get(() ->
                                complete("<h1>Say hello to akka-http</h1>"))));
    }
}
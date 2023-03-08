package org.example.actors.second;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.Directives;
import akka.http.javadsl.server.Route;
import akka.http.javadsl.unmarshalling.Unmarshaller;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.complete;

public class Controller extends AbstractBehavior<HouseCommand> {
    private final ActorRef<HouseCommand> catHouse;
    public Controller(ActorContext<HouseCommand> context, ActorRef<HouseCommand> catHouse) {
        super(context);
        this.catHouse = catHouse;
        ActorSystem<Void> routesSystem = ActorSystem.create(Behaviors.empty(), "routes");
        final Http http = Http.get(routesSystem);
        CompletionStage<ServerBinding> binding = http.newServerAt("localhost", 8080)
                .bind(getRoute())
                .thenApply(bind -> bind.addToCoordinatedShutdown(Duration.ofSeconds(10), routesSystem));
        ;
    }

    @Override
    public Receive<HouseCommand> createReceive() {
        return newReceiveBuilder().build();
    }

    private Route getRoute() {
        return concat(
                path("health", () ->
                        get(() ->
                                complete("Cat is alive!"))),
                path("feedCat", () ->
                        post(() -> entity(Unmarshaller.entityToString(), input ->
                                extractRequestContext(requestContext -> {
                                    System.out.println("Request entity: " + input);
                                    CompletionStage<HttpResponseCommand> ask = AskPattern.ask(
                                            catHouse,
                                            (replyTo) -> new RequestHttpCommand(replyTo, input),
                                            Duration.ofSeconds(3),
                                            getContext().getSystem().scheduler()
                                    );
                                    CompletionStage<HttpResponse> response = ask.handle((res, ex) ->
                                            res != null ? HttpResponse.create().withEntity(res.getBody()).withStatus(StatusCodes.OK)
                                                    : handle(ex.getMessage()));
                                    return Directives.completeWithFuture(response);
                                })
                        ))
                )
        );
    }

    private HttpResponse handle(String message) {
        return HttpResponse.create().withEntity(message).withStatus(StatusCodes.BAD_REQUEST);
    }

    public static Behavior<HouseCommand> create(ActorRef<HouseCommand> catHouse) {
        return Behaviors.setup(context -> new Controller(context, catHouse));
    }
}

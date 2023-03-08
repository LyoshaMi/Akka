package org.example.actors.second;

public class TestActor {
//    private final ActorRef<HouseCommand> catHouse;
//    public Controller(ActorContext<HouseCommand> context, ActorRef<HouseCommand> catHouse) {
//        super(context);
//        this.catHouse = catHouse;
//        final Http http =... localhost, 8080;
//        http.bind(getRoute());
//    }
//
//    @Override
//    public Receive<HouseCommand> createReceive() {
//        return newReceiveBuilder()
//                .build();
//    }
//
//    public Router getRoute() {
//        concat(
//                path("health", () -> get()),
//                path("feedCat", (requestFeed) -> {
//                    CompletionStage<HttpResponseCommand> ask = AskPattern.ask(
//                            catHouse,
//                            (replyTo) -> new RequestHttpCommand(replyTo, requestFeed),
//                            timeout,
//                            scheduler
//                    );
//                    CompletionStage<HttpResponse> response = ask.handle((res, ex) -> res != null ? res.getResponse : ex);
//                    return Directives.completeWithFuture(response);
//                })
//        )
//    }
//
//
//    //    CatHouse
//    public Receive<HouseCommand> createReceive() {
//        return newReceiveBuilder()
//                .onMessage(InitCommand.class, this::init)
//                .onMessage(InitCommand.class, this::init)
//                .onMessage(RequestHttpCommand.class, this::requestHttpCommand)
//                .build();
//    }
//
//    public Behavior<HouseCommand> requestHttpCommand(RequestHttpCommand command) {
//        cat = ...
//        cat.tell(new ЛюбаяКоманда(..., command.getReplyTo()...))
//    }
//
//    //    Cat
//    public Receive<HouseCommand> createReceive() {
//        return newReceiveBuilder()
//                .onMessage(ЛюбаяКоманда.class, this::request)
//                .build();
//    }
//
//    public Behavior<HouseCommand> request(ЛюбаяКоманда command) {
//        System.out.println();
//        command.getReplyTo().tell(new HttpResponseCommand());
//    }
}
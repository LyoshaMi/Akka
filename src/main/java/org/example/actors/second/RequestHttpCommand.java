package org.example.actors.second;

import akka.actor.typed.ActorRef;

public class RequestHttpCommand implements HouseCommand{

    private String body;
    private ActorRef<HttpResponseCommand> replyTo;

    public RequestHttpCommand(ActorRef<HttpResponseCommand> replyTo, String body) {
        this.replyTo = replyTo;
        this.body = body;
    }

    public ActorRef<HttpResponseCommand> getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(ActorRef<HttpResponseCommand> replyTo) {
        this.replyTo = replyTo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

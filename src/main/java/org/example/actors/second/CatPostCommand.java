package org.example.actors.second;

import akka.actor.typed.ActorRef;

public class CatPostCommand implements CatCommand{

    private ActorRef<HttpResponseCommand> replyTo;
    private String body;

    public CatPostCommand(ActorRef<HttpResponseCommand> replyTo, String body) {
        this.replyTo = replyTo;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ActorRef<HttpResponseCommand> getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(ActorRef<HttpResponseCommand> replyTo) {
        this.replyTo = replyTo;
    }
}

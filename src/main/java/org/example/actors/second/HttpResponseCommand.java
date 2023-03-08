package org.example.actors.second;

public class HttpResponseCommand implements HouseCommand{
    public String body;

    public HttpResponseCommand(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

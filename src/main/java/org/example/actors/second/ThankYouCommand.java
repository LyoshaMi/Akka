package org.example.actors.second;

public class ThankYouCommand implements HouseCommand{
    private String thanks;

    public ThankYouCommand(String thanks) {
        this.thanks = thanks;
    }
}

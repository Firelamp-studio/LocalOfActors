package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import Game.DrinkCard;

public class Owner extends Actor {

    private int receipts;

    public Owner() {
        receipts = 0;
        setSprite("owner.png", 0.4);
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        setRotation(180);
    }

    @Override
    protected void tick(long deltaTime) {

    }

    @ActionCallable(name = "pay-and-get-card")
    public DrinkCard giveCard() {
        receipts += 10;
        return new DrinkCard();
    }
}

package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import Game.DrinkCard;

public class CashDesk extends Actor {

    private int receipts;

    public CashDesk() {
        receipts = 0;
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

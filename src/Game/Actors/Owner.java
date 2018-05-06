package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.Vector;
import Game.DrinkCard;

public class Owner extends Person {
    private Vector startPosiotion;
    private int receipts;

    public Owner() {
        receipts = 0;
        setSprite("owner.png", 0.4);
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        setRotation(180);
        startPosiotion = new Vector(getLocation());
    }

    @Override
    protected void tick(long deltaTime) {

    }

    @ActionCallable(name = "pay-and-get-card")
    public DrinkCard giveCard() {
        receipts += 10;
        return new DrinkCard();
    }

    @ActionCallable(name = "refill-barrel")
    public void refillBarrel(Barrel barrel) {
        moveTo(barrel.getLocation().add(new Vector(0, 80)), "move-to-barrel", barrel);
    }

    @ActionCallable(name = "move-to-barrel")
    public void moveToBarell(Barrel barrel) {
        barrel.refill();
        moveTo(startPosiotion);
    }
}

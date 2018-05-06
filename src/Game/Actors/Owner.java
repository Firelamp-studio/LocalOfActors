package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
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
    public void refillBarrel(Barrel barrel, Barman barman) {
        System.out.println();
        moveTo(new Vector(200, 100), "move-to-storage", barrel, barman);
    }

    @ActionCallable(name = "move-to-storage")
    public void moveToStorage(Barrel barrel, Barman barman) {
        new TimerAction(2000, this, "start-move-to-barrel").execute(barrel, barman);
    }

    @ActionCallable(name = "start-move-to-barrel")
    public void startMoveToBarrel(Barrel barrel, Barman barman) {
        moveTo(barrel.getLocation().add(new Vector(0, 80)), "move-to-barrel", barrel, barman);
    }

    @ActionCallable(name = "move-to-barrel")
    public void moveToBarell(Barrel barrel, Barman barman) {
        new TimerAction(2000, this, "start-refill-barrel").execute(barrel, barman);
    }

    @ActionCallable(name = "start-refill-barrel")
    public void startRefillBarell(Barrel barrel, Barman barman) {
        barrel.refill();
        moveTo(startPosiotion, "end-refill");
        actionCall(barman, "redo-spill-request", barrel);
    }

    @ActionCallable(name = "end-refill")
    public void endRefill() {
        setRotation(180);
    }

}

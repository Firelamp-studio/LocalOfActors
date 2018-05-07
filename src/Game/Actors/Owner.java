package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.DrinkCard;

public class Owner extends Person {
    private Vector startPosiotion;
    private int receipts;
    public boolean isRefillingBarrel;

    public Owner() {
        isRefillingBarrel = false;
        receipts = 0;
        tickEnabled = true;
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
        if(!isRefillingBarrel && getNumOfNotifyActions("refill-barrel") > 0 || getNumOfNotifyActions("pay-and-get-card") > 0){
            System.out.println("BARRRELLLLLLLLLLLLLLL: " + getNumOfNotifyActions("refill-barrel"));
            System.out.println("PAYYYYYYYYYY: " + getNumOfNotifyActions("refill-barrel"));
            if(getNumOfNotifyActions("refill-barrel") > 0){
                notifyNextAction("refill-barrel");
                isRefillingBarrel = true;
            } else {
                notifyAllActions("pay-and-get-card");
            }
        }
    }

    @ActionCallable(name = "pay-and-get-card")
    public void giveCard(Customer customer) {
        if(!isRefillingBarrel){
            receipts += 10;
            actionCall(customer, "pay-and-get-card", new DrinkCard());
        } else {
            actionCallOnNotify(this, "pay-and-get-card", customer);
        }
    }

    @ActionCallable(name = "refill-barrel")
    public void refillBarrel(Barrel barrel, Barman barman) {
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
        setRotation(0);
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
        isRefillingBarrel = false;
    }

}

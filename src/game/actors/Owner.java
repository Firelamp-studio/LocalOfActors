package game.actors;

import api.Pawn;
import api.annotations.ActionCallable;
import api.annotations.ActionResponse;
import api.utility.TimerAction;
import api.utility.Vector;
import game.DrinkCard;
import game.maps.BarMap;

public class Owner extends Pawn {
    private CashDesk cashDesk;
    private Vector startPosiotion;
    private int receipts;
    private BarMap map;
    public boolean isRefillingBarrel;

    public Owner(CashDesk cashDesk) {
        this.cashDesk = cashDesk;
        isRefillingBarrel = false;
        receipts = 0;
        tickEnabled = true;
        setSprite("owner.png", 0.4);
    }

    public int getRecessed() {
        return receipts;
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        setRotation(180);
        startPosiotion = new Vector(getLocation());

        if (getMap() instanceof BarMap){
            map = (BarMap) getMap();
        }
    }

    @Override
    protected void tick(long deltaTime) {
        if(!isRefillingBarrel && cashDesk.isModifyEnabled() && !cashDesk.getWaitingCustomers().isEmpty()){
            cashDesk.setModifyEnabled(false);
            actionCallResponse(cashDesk, "dequeue-customer", "entry-cashdesk-line-and-movement");
        }
        if(!isRefillingBarrel && getNumOfNotifyActions() > 0){
            notifyNextAction();
            isRefillingBarrel = true;
        }
    }

    @ActionResponse(name = "dequeue-customer")
    public void dequeueResponse(Customer customer){
        if(customer != null){
            actionCall(customer, "pay-and-get-card", new DrinkCard());
            receipts += 10;
            customer.moveTo(cashDesk.getLocation().add(new Vector(0, 30)), "arrived-to-cashdesk");
        }
    }

    @ActionCallable(name = "refill-barrel")
    public void refillBarrel(Barrel barrel, Barman barman) {
        moveTo(new Vector(200, 100), "move-to-storage", barrel, barman);
    }

    @ActionCallable(name = "move-to-storage")
    public void moveToStorage(Barrel barrel, Barman barman) {
        new TimerAction(200 * map.getGameSpeed(), this, "start-move-to-barrel").execute(barrel, barman);
    }

    @ActionCallable(name = "start-move-to-barrel")
    public void startMoveToBarrel(Barrel barrel, Barman barman) {
        moveTo(barrel.getLocation().add(new Vector(0, 80)), "move-to-barrel", barrel, barman);
    }

    @ActionCallable(name = "move-to-barrel")
    public void moveToBarell(Barrel barrel, Barman barman) {
        setRotation(0);
        new TimerAction(200 * map.getGameSpeed(), this, "end-refill-barrel").execute(barrel, barman);
    }

    @ActionCallable(name = "end-refill-barrel")
    public void startRefillBarell(Barrel barrel, Barman barman) {
        barrel.refill();
        moveTo(startPosiotion, "arrived-to-cashdesk");
        actionCall(barman, "request-spilling-to-barrel", barrel);
    }

    @ActionCallable(name = "arrived-to-cashdesk")
    public void endRefill() {
        setRotation(180);
        isRefillingBarrel = false;
    }
}

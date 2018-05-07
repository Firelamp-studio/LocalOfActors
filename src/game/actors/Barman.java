package game.actors;

import api.annotations.*;
import api.utility.Rotator;
import api.utility.TimerAction;
import api.utility.Vector;
import game.maps.BarMap;

public class Barman extends Person {
    private boolean free;
    private Vector startPosition;
    private Barrel redWineBarrel;
    private Barrel whiteWineBarrel;
    private Owner owner;
    private Customer customerToServe;
    private long spillDelay;

    public Barman(Barrel redWineBarrel, Barrel whiteWineBarrel, Owner owner) {
        free = true;
        setSprite("barman.png", 0.4);

        this.owner = owner;
        this.redWineBarrel = redWineBarrel;
        this.whiteWineBarrel = whiteWineBarrel;
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        setRotation(180);
        startPosition = new Vector(getLocation());

        long delay = 500;
        if (getMap() instanceof BarMap){
            delay = ((BarMap)getMap()).getGameSpeed() * 250;
        }
        spillDelay = delay;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Vector getStartPosition() {
        return startPosition;
    }

    @ActionCallable(name = "request-spilling-to-barrel")
    public void requestSpillingToBarrel(Barrel barrel){
            setRotation(Rotator.rotationLookingTo(getLocation(), barrel.getLocation()));
            actionCallOnNotify(barrel,"start-spilling-wine", this);
    }

    @ActionCallable(name = "order-wine")
    public void orderWine(boolean wantRedWine, Customer customer){
        customerToServe = customer;

        if(wantRedWine){
            requestSpillingToBarrel(redWineBarrel);
        } else {
            requestSpillingToBarrel(whiteWineBarrel);
        }
    }

    @ActionCallable(name = "spill-wine")
    public void spillWine(Barrel barrel){
        setRotation(0);
        new TimerAction(spillDelay, barrel, "barman-end-spilling").execute(this);
    }

    @ActionCallable(name = "give-wine-to-customer")
    public void giveWineToCustomer(boolean isRedWine){
        free = true;
        setRotation(180);
        if(customerToServe.getDrinkCard().hasComsumation()){
            customerToServe.getDrinkCard().useConsumation(isRedWine);
            actionCall(customerToServe, "receive-wine-glass");
        } else {
            actionCall(customerToServe, "exit");
        }

    }



    @ActionCallable(name = "arrived-to-owner")
    public void arrivedToOwner(Barrel barrel){
        actionCallOnNotify(owner, "refill-barrel", barrel, this);
        moveTo(startPosition, "come-back-and-wait-owner", barrel);
    }

    @ActionCallable(name = "come-back-and-wait-owner")
    public void comeBackAndWaitOwner(Barrel barrel){
        setRotation(Rotator.rotationLookingTo(getLocation(), barrel.getLocation()));
    }
}

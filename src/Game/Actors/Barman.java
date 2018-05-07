package Game.Actors;

import API.Annotations.*;
import API.Utility.Rotator;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.Maps.BarMap;

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

    @ActionCallable(name = "order-wine")
    public void orderWine(boolean wantRedWine, Customer customer){
        customerToServe = customer;

        if(wantRedWine){
            setRotation(Rotator.rotationLookingTo(getLocation(), redWineBarrel.getLocation()));
            actionCallOnNotify(redWineBarrel,"start-spilling-wine", this);
        } else {
            setRotation(Rotator.rotationLookingTo(getLocation(), whiteWineBarrel.getLocation()));
            actionCallOnNotify(whiteWineBarrel,"start-spilling-wine", this);
        }
    }

    @ActionCallable(name = "spill-wine")
    public void spillWine(Barrel barrel){
        setRotation(0);
        new TimerAction(spillDelay, barrel, "barman-end-spilling").execute(this);
    }

    @ActionCallable(name = "give-wine-to-customer")
    public void giveWineToCustomer(){
        free = true;
        setRotation(180);
        actionCall(customerToServe, "receive-wine-glass");
    }
}

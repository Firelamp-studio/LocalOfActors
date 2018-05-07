package Game.Actors;

import API.Annotations.*;
import API.Utility.Rotator;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.Maps.BarMap;

public class Barman extends Person {
    private Vector startPosition;
    private Owner owner;
    private Customer customer;
    private boolean free;
    private Barrel redWineBarrel;
    private Barrel whiteWineBarrel;
    private int wineGlass;

    public Barman(Barrel redWineBarrel, Barrel whiteWineBarrel, Owner owner) {
        free = true;
        this.redWineBarrel = redWineBarrel;
        this.whiteWineBarrel = whiteWineBarrel;
        this.owner = owner;
        wineGlass = 0;
        setSprite("barman.png", 0.4);
        startPosition = new Vector();
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        setRotation(180);
        startPosition = new Vector(getLocation());
    }

    @ActionCallable(name = "order-wine")
    public void orderWine(boolean bIsRedWine, Customer customer) {
        this.customer = customer;
        if (customer.getDrinkCard().hasComsumation()) {
            if (bIsRedWine){
                setRotation(Rotator.rotationLookingTo(getLocation(), redWineBarrel.getLocation()));
                actionCallOnNotify(redWineBarrel,"request-spill", this);
            } else {
                setRotation(Rotator.rotationLookingTo(getLocation(), whiteWineBarrel.getLocation()));
                actionCallOnNotify(whiteWineBarrel,"request-spill", this);
            }
        } else {
            customer.moveTo(Customer.getWaitingAreaVector(), "choose-what-to-do");
            free = true;
        }
    }

    @ActionCallable(name = "can-spill")
    public void canSpill(Barrel barrel) {
        moveTo(barrel.getLocation().add(new Vector(0, 80)), "arrived-on-barrel", barrel);
    }

    @ActionCallable(name = "arrived-on-barrel")
    public void arrivedOnBarrel(Barrel barrel) {
        setRotation(0);

        actionCall(barrel, "get-wine-glass", this);
    }

    @ActionCallable(name = "get-wine-glass")
    public void getWineGlass(Barrel barrel) {
        moveTo(startPosition, "give-wine-glass", 250);
    }

    @ActionCallable(name = "request-new-barrel")
    public void requestNewBarrel(Barrel barrel) {
        moveTo(owner.getLocation().add(new Vector(40,0)), "move-to-owner", barrel);
    }

    @ActionCallable(name = "move-to-owner")
    public void moveToOwner(Barrel barrel) {
        actionCallOnNotify(owner, "refill-barrel", barrel, this);
        moveTo(startPosition);
    }

    @ActionCallable(name = "redo-spill-request")
    public void redoSpillRequest(Barrel barrel) {
        setRotation(Rotator.rotationLookingTo(getLocation(), barrel.getLocation()));
        actionCallOnNotify(barrel,"request-spill", this);
    }

    //moveTo(owner.getLocation().add(new Vector(40,0)), "move-to-owner", );

    @ActionCallable(name = "give-wine-glass")
    public void giveWineGlass(int wineGlass) {
        setRotation(180);
        actionCall(customer, "receive-wine-glass", wineGlass);
        free = true;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    /*@ActionResponse(name = "consuma_vino")
    public void consumaVino(double quantita) {

        vino += quantita;

        System.out.println("Barman: Ho ottenuto " + quantita + " litri, quindi adesso ne ho " + vino);
    }


    @BindableEvent(name = "vino_finito")
    public void vinoFinito() {
        System.out.println("Barman: Ci è stato appena detto che è finito il vino");
    }

     */
}

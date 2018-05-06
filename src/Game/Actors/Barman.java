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
    private boolean fillRedWhine;
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

    public void orderWine(boolean bIsRedWine, Customer customer) {
        this.customer = customer;
        if (customer.getDrinkCard().hasComsumation()) {
            fillRedWhine = bIsRedWine;
            if (bIsRedWine){
                setRotation(Rotator.rotationLookingTo(getLocation(), redWineBarrel.getLocation()));
                actionCall(redWineBarrel,"request-spill", this);
            } else {
                setRotation(Rotator.rotationLookingTo(getLocation(), whiteWineBarrel.getLocation()));
                actionCall(whiteWineBarrel,"request-spill", this);
            }
        } else {
            customer.moveTo(Customer.getWaitingAreaVector(), "choose-what-to-do");
        }
    }

    @ActionCallable(name = "can-spill")
    public void canSpill() {
        if (fillRedWhine)
            moveTo(redWineBarrel.getLocation().add(new Vector(0, 80)), "arrived-on-barrel", redWineBarrel);
        else
            moveTo(whiteWineBarrel.getLocation().add(new Vector(0, 80)), "arrived-on-barrel", whiteWineBarrel);
    }

    @ActionCallable(name = "arrived-on-barrel")
    public void arrivedOnBarrel(Barrel barrel) {
        setRotation(0);

        long delay = 500;
        if (getMap() instanceof BarMap){
            delay = ((BarMap)getMap()).getGameSpeed() * 250;
        }

        new TimerAction(delay,this,"spill").execute(barrel);
    }

    @ActionCallable(name = "spill")
    public void spill(Barrel barrel) {
        actionCallResponse(barrel, "get-wine-glass", this);
    }

    @ActionResponse(name = "get-wine-glass")
    public void getWineGlass(int wineGlass) {
        moveTo(startPosition, "give-wine-glass", wineGlass);
    }

    @ActionCallable(name = "request-new-barrel")
    public void requestNewBarrel(Barrel barrel) {
        moveTo(owner, "move-to-owner", barrel);
    }

    @ActionCallable(name = "move-to-owner")
    public void moveToOwner(Barrel barrel) {
        actionCall(owner, "refill-barrel", barrel);
        moveTo(startPosition,"");
    }

    //moveTo(owner.getLocation().add(new Vector(40,0)), "move-to-owner", );

    @ActionCallable(name = "give-wine-glass")
    public void giveWineGlass(int wineGlass) {
        setRotation(180);
        actionCall(customer, "recive-wine-glass", wineGlass);
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

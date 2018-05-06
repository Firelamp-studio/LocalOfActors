package Game.Actors;

import API.Annotations.*;
import API.Utility.Rotator;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Barman extends Person {
    private Vector startPosition;
    private boolean free;
    private boolean fillRedWhine;
    private Barrel redWineBarrel;
    private Barrel witheWineBarrel;
    private int wineGlass;

    public Barman(Barrel redWineBarrel, Barrel witheWineBarrel) {
        free = true;
        this.redWineBarrel = redWineBarrel;
        this.witheWineBarrel = witheWineBarrel;
        wineGlass = 0;
        setSprite("barman.png", 0.4);
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        setRotation(180);
        startPosition = getLocation();
    }

    @Override
    protected void tick(long deltaTime) {

    }


    public void orderWine(boolean bIsRedWine, Customer customer) {
        if (customer.getDrinkCard().hasComsumation()) {
            fillRedWhine = bIsRedWine;
            if (bIsRedWine)
                actionCall(redWineBarrel,"request-spill", this);
            else
                actionCall(witheWineBarrel,"request-spill", this);
        } else {
            customer.moveTo(Customer.getWaitingAreaVector(), "choose-what-to-do");
        }
    }

    @ActionCallable(name = "can-spill")
    public void canSpill() {
        if (fillRedWhine)
            moveTo(redWineBarrel.getLocation().add(new Vector(0, 80)), "arrived-on-barrel", redWineBarrel);
        else
            moveTo(witheWineBarrel.getLocation().add(new Vector(0, 80)), "arrived-on-barrel", witheWineBarrel);
    }

    @ActionCallable(name = "arrived-on-barrel")
    public void arrivedOnBarrel(Barrel barrel) {
        setRotation(Rotator.rotationLookingTo(getLocation(), barrel.getLocation()));
        new TimerAction(2000,this,"spill").execute(barrel);
    }

    @ActionCallable(name = "spill")
    public void spill(Barrel barrel) {
        actionCallResponse(barrel, "get-wine-glass");
    }

    @ActionResponse(name = "get-wine-glass")
    public void getWineGlass(int wineGlass) {
        moveTo(startPosition, "molla-il-bichciere");
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

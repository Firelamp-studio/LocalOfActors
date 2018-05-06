package Game.Actors;

import API.Annotations.*;
import Game.DrinkCard;

public class Barman extends Person {
    private boolean free;
    private Barrel redWineBarrel;
    private Barrel witheWineBarrel;
    private float wineGlass;

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
    }

    @Override
    protected void tick(long deltaTime) {

    }

    public void orderWine(boolean bIsRedWine, DrinkCard drinkCard) {
        if (drinkCard.useComsumation(bIsRedWine)) {
            if (bIsRedWine) {
                moveTo(redWineBarrel, "arrived-on-barrel");
            } else {
                moveTo(witheWineBarrel, "arrived-on-barrel");
            }
        }
    }

    @ActionCallable

    public boolean isFree() {
        return free;
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

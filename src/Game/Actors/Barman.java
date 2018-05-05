package Game.Actors;

import API.Annotations.*;
import Game.DrinkCard;

public class Barman extends Person {

    private Barrel redWineBarrel;
    private Barrel witheWineBarrel;

    public Barman(Barrel redWineBarrel, Barrel witheWineBarrel) {
        this.redWineBarrel = redWineBarrel;
        this.witheWineBarrel = witheWineBarrel;
    }

    @Override
    protected void tick(long deltaTime) {

    }

    @ActionResponse(name = "order-wine")
    public void consumaVino(boolean bIsRedWine, DrinkCard drinkCard) {
        try {
            if (bIsRedWine) {
                actionCall(redWineBarrel, "take-wine");
            } else {
                actionCall(witheWineBarrel, "take-wine");
            }
            drinkCard.useComsumation(bIsRedWine);
        } catch (Exception e) {
            // TODO: handle exception
        }

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

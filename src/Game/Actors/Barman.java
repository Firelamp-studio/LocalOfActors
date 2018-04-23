package Game.Actors;

import API.Annotations.*;

public class Barman extends SceneCharacter {
    double vino;

    public Barman() {
        vino = 0;
    }

    public void consumaDaBarile(Barrel barrel, double litri) {
        System.out.println("Barman: Attuelmente ho " + vino + " litri di vino");
        System.out.println("Barman: Inizio procedura consumazione...");
        actionCallResponse(barrel, "consuma_vino", litri);
    }


    @ActionResponse(name = "consuma_vino")
    public void consumaVino(double quantita) {

        vino += quantita;

        System.out.println("Barman: Ho ottenuto " + quantita + " litri, quindi adesso ne ho " + vino);
    }


    @BindableEvent(name = "vino_finito")
    public void vinoFinito() {
        System.out.println("Barman: Ci è stato appena detto che è finito il vino");
    }



    @Override
    protected void tick() {

    }
}

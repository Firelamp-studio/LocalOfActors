package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Annotations.AsyncMethod;

public class Barrel extends Actor {
    double vino;

    public Barrel(){
        vino = 1000;
    }

    @AsyncMethod
    public void allAvvio(){
        System.out.println("AVVIATO IL THREAD ID BARREL");
    }

    @ActionCallable(name = "consuma_vino")
    public double consumaVino(double quantita){
        System.out.println("Barrel: Attualmente possiedo " + vino + " litri di vino");

        if(vino >= quantita){
            System.out.println("Barrel: Consumazione di " + quantita + " litri...");
            vino -= quantita;
            System.out.println("Barrel: Sono rimasti " + vino + " litri");

            if(vino <= 0){
                actionCall("dispatch_vino_finito");
                System.out.println("Barrel: Vino finito");
            }
            return quantita;
        }


        return 0;
    }


    @ActionCallable(name = "dispatch_vino_finito")
    public void dispatchVinoFinito(){
        dispatchEvent("vino_finito");
    }


    @Override
    protected void tick() {

    }
}

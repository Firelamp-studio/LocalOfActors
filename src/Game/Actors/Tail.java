package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;

public abstract class Tail extends Actor {
    TimerAction timerAction;

    int numPersoneEntrate;

    public Tail(){
        numPersoneEntrate = 0;
    }

    public void faiEntrareQualcunoOgniTanto(long delay){
        timerAction = new TimerAction(true, delay, this, "entra-dopo-ritardo", delay);
        timerAction.execute();
    }

    @ActionCallable(name = "entra-dopo-ritardo")
    public void entraDopoRitardo(long delay){
        numPersoneEntrate++;
        System.out.println("Sta entrando qualcuno dopo " + (delay/1000.0)*numPersoneEntrate + " secondi!!!");
        if(numPersoneEntrate >= 5){
            timerAction.kill();
        }
    }

}

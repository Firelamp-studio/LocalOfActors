package Game.Actors;

import java.util.Random;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;

public class EntryDoor extends Actor {

    /*private TimerAction timerAction;*/
    private int numPeopleInside;
    private LocalTail localTail;

    public EntryDoor() {
        numPeopleInside = 0;
    }

    @Override
    protected void tick(long deltaTime) {
        if (numPeopleInside < 30) {
            actionCall(localTail, "let-person-entry");
        }
    }
    /*
    public void faiEntrareQualcunoOgniTanto(int maxWaitTimeMS){
    	long delay = new Random().nextInt(maxWaitTimeMS) + 1;
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
    }*/
}

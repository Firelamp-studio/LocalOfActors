package Game.Actors;

import java.util.Random;

import API.Annotations.ActionCallable;
import API.Utility.TimerAction;

public class LocalTail extends Tail {
	TimerAction timerSpawn;
	
	public LocalTail(int maxPeopleInQueue, int numPeopleAtStart, int minSpawnTime, int maxSpawnTime) {
		if (numPeopleAtStart > maxPeopleInQueue) {
			throw new IllegalArgumentException("Number of peopel at start is higher than max people in queue");
		}
		for (int i = 0; i < (new Random().nextInt(numPeopleAtStart) + 1); i++) {
			addToTail(new Customer());
		}
		
		long delay = new Random().nextInt((maxSpawnTime - minSpawnTime) + 1) + minSpawnTime;
	    timerSpawn = new TimerAction(false, delay, this, "new-person-in-queue", delay);
	    timerSpawn.execute();
	}
	
	@ActionCallable(name = "new-person-in-queue")
	public void entraDopoRitardo(long delay){
		//TODO
		/*if(numPersoneEntrate >= 5){
			timerAction.kill();
		}*/
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

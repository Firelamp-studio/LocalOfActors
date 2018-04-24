package API;

import API.Utility.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Map extends Actor{
    private Vector mapSize;
    private HashMap<Integer, Vector> spawnPoints;

    public Map(Vector mapSize){
        spawnPoints = new HashMap<>();
    	this.mapSize = mapSize;
    	getViewArea().setSize(mapSize.toDimension());
        getViewArea().setPreferredSize(mapSize.toDimension());
    }
    
    

    public Dimension getMapSize() {
		return getViewArea().getSize();
	}



	public void addSpawnPoint(Vector spawnPoint, int index){
        spawnPoints.put(index, spawnPoint);
    }
	
	public void addElement(Element element, Vector location){
        element.getViewArea().setLocation(location.x, location.y);

        getViewArea().add(element.getViewArea());
    }
	
	public void addActor(Actor actor, Vector location){
		actor.getViewArea().setLocation(location.x, location.y);

        bindActorForEvents(actor);
        actionCall(actor, "actor-spawned");

        getViewArea().add(actor.getViewArea());
    }

    public void spawnPawn(Pawn pawn, int spawnIndex){
        Vector spawnPoint = spawnPoints.get(spawnIndex);
        pawn.getViewArea().setLocation(spawnPoint.x, spawnPoint.y);

        bindActorForEvents(pawn);
        actionCall(pawn, "actor-spawned");

        getViewArea().add(pawn.getViewArea());
    }
}

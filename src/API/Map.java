package API;

import API.Utility.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Map extends Actor{
    private Dimension mapSize;
    private HashMap<Integer, Vector> spawnPoints;

    public Map(Dimension mapSize){
        this.mapSize = mapSize;
        spawnPoints = new HashMap<>();
        getViewArea().setPreferredSize(mapSize);
        
        this.mapSize = mapSize;
    }
    
    

    public Dimension getMapSize() {
		return mapSize;
	}



	public void addSpawnPoint(Vector spawnPoint, int index){
        spawnPoints.put(index, spawnPoint);
    }

    public void spawnPawn(Actor actor, int spawnIndex){
        Vector spawnPoint = spawnPoints.get(spawnIndex);
        actor.getViewArea().setLocation(spawnPoint.x, spawnPoint.y);

        bindActorForEvents(actor);
        actionCall(actor, "actor-spawned");

        getViewArea().add(actor.getViewArea());
    }
}

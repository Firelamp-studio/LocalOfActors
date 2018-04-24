package API;

import API.Utility.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Map extends Actor{
    private JPanel mapView;
    private Vector mapSize;
    private HashMap<Integer, Vector> spawnPoints;

    public Map(Vector mapSize){
        this.mapSize = mapSize;
        spawnPoints = new HashMap<>();

        mapView = new JPanel();
        mapView.setBackground(new Color(34567));
        this.mapSize = mapSize;
        mapView.setSize(mapSize.x, mapSize.y);
    }

    public void addSpawnPoint(Vector spawnPoint, int index){
        spawnPoints.put(index, spawnPoint);
    }

    public JPanel getMapView(){
        return mapView;
    }

    public void spawnActor(Actor actor, int spawnIndex){
        Vector spawnPoint = spawnPoints.get(spawnIndex);
        actor.getViewArea().setLocation(spawnPoint.x, spawnPoint.y);

        bindActorForEvents(actor);
        actionCall(actor, "actor-spawned");

        mapView.add(actor.getViewArea());
    }
}

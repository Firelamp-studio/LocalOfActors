package API;

import API.Utility.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Map {
	private JLayeredPane viewArea;
    private Vector mapSize;

    public Map(Vector mapSize){
    	this.mapSize = mapSize;
    	viewArea = new JLayeredPane();
    	viewArea.setLayout(null);
    	
    	viewArea.setOpaque(true);
    	viewArea.setSize(mapSize.toDimension());
    	viewArea.setPreferredSize(mapSize.toDimension());
    }
    
    

    public JLayeredPane getViewArea() {
		return viewArea;
	}



	public Dimension getMapSize() {
		return viewArea.getSize();
	}
    
    public void updateActorLocation(Vector location) {
    	
    }
	
	public void addElement(Element element, Vector location, int zindex){
		if(element.getSprite() != null) {
			Dimension preferredSize =  element.getSprite().getPreferredSize();
	        element.getSprite().setBounds(location.x - preferredSize.width/2, location.y - preferredSize.height/2, preferredSize.width, preferredSize.height);
	        element.setLocation(location);
	        viewArea.add(element.getSprite());
	        viewArea.setLayer(element.getSprite(), zindex);
		}
		element.setMap(this);
    }
	
	public void addElement(Element element, Vector location) {
		addElement(element, location, 0);
	}
	
	public void addActor(Actor actor, Vector location){
        addElement(actor, location, 10);
        actor.beginPlay();
    }
	
	
}

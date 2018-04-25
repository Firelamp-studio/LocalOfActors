package API;

import API.Utility.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Map {
	private JPanel viewArea;
    private Vector mapSize;

    public Map(Vector mapSize){
    	this.mapSize = mapSize;
    	viewArea = new JPanel(null);
    	
    	viewArea.setOpaque(true);
    	viewArea.setSize(mapSize.toDimension());
    	viewArea.setPreferredSize(mapSize.toDimension());
    }
    
    

    public JPanel getViewArea() {
		return viewArea;
	}



	public Dimension getMapSize() {
		return viewArea.getSize();
	}
    
    public void updateActorLocation(Vector location) {
    	
    }
	
	public void addElement(Element element, Vector location){
		if(element.getSprite() != null) {
			Dimension preferredSize =  element.getSprite().getPreferredSize();
	        element.getSprite().setBounds(location.x - preferredSize.width/2, location.y - preferredSize.height/2, preferredSize.width, preferredSize.height);
	        element.setLocation(location);
	        viewArea.add(element.getSprite());
		}
    }
	
	public void addActor(Actor actor, Vector location){
        addElement(actor, location);
        actor.beginPlay();
    }
	
	
}

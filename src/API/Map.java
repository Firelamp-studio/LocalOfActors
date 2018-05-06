package API;

import API.Utility.Vector;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Map {
	private JLayeredPane viewArea;
    private Vector mapSize;

    public Map(){
    	this.mapSize = new Vector();
    	viewArea = new JLayeredPane();
    	viewArea.setLayout(null);
    	
    	viewArea.setOpaque(true);
    }
    
    public void setMapSize(Dimension size) {
    	viewArea.setPreferredSize(size);
    	viewArea.setSize(size);
    }

    public JLayeredPane getViewArea() {
		return viewArea;
	}

    public Vector getMapCenter() {
    	return new Vector(getMapSize().width / 2, getMapSize().height /2);
    }

	public Dimension getMapSize() {
		return viewArea.getSize();
	}
    
    public void updateActorLocation(Vector location) {
    	
    }
    
    public void addComponent(JComponent component, Vector location, int zindex){
		if(component != null) {
			Dimension preferredSize =  component.getPreferredSize();
			component.setBounds(location.x - preferredSize.width/2, location.y - preferredSize.height/2, preferredSize.width, preferredSize.height);
	        viewArea.add(component);
	        viewArea.setLayer(component, zindex);
		}
    }
	
	public void addElement(Element element, Vector location, int zindex){
		if(element.getSprite() != null) {
			addComponent(element.getSprite(), location, zindex);
	        element.setLocation(location);
		}
		element.setMap(this);
    }
	
	public void addElement(Element element, Vector location) {
		addElement(element, location, 0);
	}

	public void addActor(Actor actor, Vector location){
		addActor(actor, location, 1);
	}

	public void addActor(Actor actor, Vector location, int zindex){
		addElement(actor, location, zindex);
		actor.beginPlay();
	}
	
	
}

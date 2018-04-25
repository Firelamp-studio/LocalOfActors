package API;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import API.Components.Sprite;
import API.Utility.Rotator;
import API.Utility.Vector;

public class Element {
    private Vector location;
    private Sprite sprite;
    private Map map;
	
	public Element() {
		sprite = null;
		location = new Vector();
	}

	// View methods
    public void setLocation(Vector location){
    	setLocation(location.x, location.y);
    }
    
    public void setLocation(int x, int y){
    	
    	this.location.x = x;
    	this.location.y = y;
        
        if(sprite != null)
        	sprite.setLocation(location.x - sprite.getWidth()/2, location.y - sprite.getHeight()/2);
    }

    public Vector getLocation(){
        return this.location;
    }

    // View methods
    public void setRotation(float degrees){
        sprite.rotate(degrees);
    }

    public Vector getActorRotation(){
        return new Vector();
    }

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
    
    
}

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
	
	public void Element(String filename, double scale) {
		setSprite(filename, scale);
		location = new Vector();
	}
	
	public void Element(String filename) {
		setSprite(filename);
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
    	if(sprite != null)
    		sprite.rotate(degrees);
    }

    public float getRotation(){
    	if(sprite != null)
    		return sprite.getRotation();
    	
    	return 0;
    }

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(String filename, double scale) {
		this.sprite = new Sprite(filename, scale);
	}
	
	public void setSprite(String filename) {
		this.sprite = new Sprite(filename);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
    
}

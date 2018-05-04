package API;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

import API.Components.Sprite;
import API.Utility.Rotator;
import API.Utility.Vector;

public class Element implements MouseListener {
    private Vector location;
    private Sprite sprite;
    private HashMap<JComponent, Vector> syncedComps;
    private Map map;
	
	public Element() {
		syncedComps = new HashMap<>();
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

    public void setLocation(Vector location){
    	setLocation(location.x, location.y);
    }
    
    public void updateSyncedComponentsLocation(Vector location){
    	updateSyncedComponentsLocation(location.x, location.y);
    }
    
    private void updateSyncedComponentsLocation(int x, int y) {
    	syncedComps.forEach((comp, loc) -> {
    		comp.setLocation(loc.toPoint());
    		comp.setLocation(location.x - comp.getWidth()/2 + comp.getX(), location.y - comp.getHeight()/2 + comp.getY());
    	});
    }
    
    public void setLocation(int x, int y){
    	updateSyncedComponentsLocation(x, y);
    	
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
		sprite.addMouseListener(this);
	}
	
	public void setSprite(String filename) {
		this.sprite = new Sprite(filename);
		sprite.addMouseListener(this);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
    
	public void addRelativeComponent(JComponent component, Vector relative, int zindex) {
		relative.y *= -1;
		syncedComps.put(component, relative);
		map.addComponent(component, location.add(relative), zindex);
	}
	
	public void addRelativeComponent(JComponent component, Vector relative) {
		addRelativeComponent(component, relative, 10);
	}
	
	public void addRelativeComponent(JComponent component, int zindex) {
		addRelativeComponent(component, new Vector(), zindex);
	}
	
	public void addRelativeComponent(JComponent component) {
		addRelativeComponent(component, new Vector(), 10);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}

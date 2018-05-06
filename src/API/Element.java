package API;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JComponent;

import API.Components.Sprite;
import API.Utility.Vector;

public class Element implements MouseListener {
    private Vector location;
    private Sprite sprite;
    private HashMap<JComponent, Vector> syncedComps;
    private HashMap<Element, Vector> syncedElements;
    private Map map;

	public Element(String filename, double scale) {
		syncedComps = new HashMap<>();
		syncedElements = new HashMap<>();
		sprite = new Sprite(filename, scale);
		location = new Vector();
	}

	public void Element(String filename) {
		syncedComps = new HashMap<>();
		syncedElements = new HashMap<>();
		sprite = new Sprite(filename);
		location = new Vector();
	}

	public Element() {
		syncedComps = new HashMap<>();
		syncedElements = new HashMap<>();
		sprite = new Sprite(null);
		location = new Vector();
	}

    public void setLocation(Vector location){
    	setLocation(location.x, location.y);
    }
    
    private void updateSyncedComponentsLocation(int x, int y) {
    	syncedComps.forEach((comp, loc) -> {
    		comp.setLocation(loc.toPoint());
    		comp.setLocation(location.x - comp.getWidth()/2 + comp.getX(), location.y - comp.getHeight()/2 + comp.getY());
    	});
    }
    
    private void updateSyncedElementLocation(int x, int y) {
    	syncedElements.forEach((elem, loc) -> {
    		elem.setLocation(location.x + loc.x, location.y + loc.y);
    	});
    }
    
    public void setLocation(int x, int y){
    	updateSyncedComponentsLocation(x, y);
    	updateSyncedElementLocation(x, y);
    	
    	this.location.x = x;
    	this.location.y = y;
        
        if(sprite != null)
        	sprite.setLocation(location.x - sprite.getWidth()/2, location.y - sprite.getHeight()/2);
    }

    public Vector getLocation(){
        return this.location;
    }

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
		attachRelativeComponent(component, relative);
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
	
	public void attachRelativeComponent(JComponent component, Vector relative) {
		relative.y *= -1;
		syncedComps.put(component, relative);
	}
	
	public void attachRelativeComponent(JComponent component) {
		attachRelativeComponent(component, new Vector());
	}
	
	public void detachRelativeComponent(JComponent component) {
		syncedComps.remove(component);
	}
	
	public void attachElement(Element element, Vector relative) {
		relative.y *= -1;
		syncedElements.put(element, relative);
	}
	
	public void detachElement(Element element) {
		syncedElements.remove(element);
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

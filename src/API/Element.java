package API;

import javax.swing.JPanel;

import API.Utility.Rotator;
import API.Utility.Vector;

public class Element {
	private JPanel viewArea;
    private Vector location;
    private Rotator rotator;
    private Map map;
	
	public Element() {
		viewArea = new JPanel();
	}

	public JPanel getViewArea() {
        return viewArea;
    }
    
    
    public void setViewArea(JPanel viewArea) {
		this.viewArea = viewArea;
	}

	// View methods
    public void setActorLocation(Vector location){
        this.location = location;
        this.viewArea.setLocation(location.x, location.y);
    }

    public Vector getLocation(){
        return this.location;
    }

    // View methods
    public void setRotation(Rotator rotator){
        this.rotator = rotator;

    }

    public Vector getActorRotation(){
        return new Vector();
    }
}

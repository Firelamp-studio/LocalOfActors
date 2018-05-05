package Game.Actors;

import java.awt.event.MouseEvent;

import API.Element;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.gui.CustomerInfo;

public class Owner extends Person {
	Element lamp;
	CustomerInfo customer;
	
	public Owner(Element lamp) {
		setSprite("man.png", 0.5);
		this.lamp = lamp;
		
		new TimerAction(3000, this, "detach-info").execute();
	}
	
	@ActionCallable(name = "detach-info")
	public void detachInfoBox() {
		detachRelativeComponent(customer);
	}
	
	@Override
	protected void beginPlay() {
		super.beginPlay();
		customer = new CustomerInfo(new Vector(100));
		addRelativeComponent(customer, new Vector(0, 80));
		moveTo(lamp);
	}

    @Override
    protected void tick(long deltaTime) {
    	System.out.println(deltaTime);
    }

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		if(customer.isVisible()) {
			customer.setVisible(false);
		}
		else {
			customer.setVisible(true);
		}
	}
    
}

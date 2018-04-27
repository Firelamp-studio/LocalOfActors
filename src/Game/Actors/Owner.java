package Game.Actors;

import API.Element;
import API.Utility.Vector;

public class Owner extends Person {
	Element lamp;
	
	public Owner(Element lamp) {
		setSprite("man.png", 0.5);
		this.lamp = lamp;
	}
	
	@Override
	protected void beginPlay() {
		super.beginPlay();
		
		moveTo(lamp);
	}

    @Override
    protected void tick(long deltaTime) {
    	System.out.println(deltaTime);
    }
}

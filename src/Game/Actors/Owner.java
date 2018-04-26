package Game.Actors;

import API.Utility.Vector;

public class Owner extends Person {
	
	public Owner() {
		setSprite("man.png", 0.5);
	}
	
	@Override
	protected void beginPlay() {
		super.beginPlay();
		
		moveTo(new Vector(100, 200));
	}

    @Override
    protected void tick(long deltaTime) {
    	System.out.println(deltaTime);
    }
}

package Game.Actors;

public class Owner extends Person {
	
	public Owner() {
		setSprite("man.png");
	}

    @Override
    protected void tick(long deltaTime) {
    	System.out.println(deltaTime);
    }
}

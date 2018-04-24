package Game.Actors;

public class Owner extends Person {

    @Override
    protected void tick(long deltaTime) {
    	System.out.println(deltaTime);
    }
}

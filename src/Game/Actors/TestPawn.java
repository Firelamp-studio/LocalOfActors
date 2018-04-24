package Game.Actors;

import API.Pawn;
import API.Components.Sprite;

public class TestPawn extends Pawn {
	private Sprite sprite;
	
	public TestPawn() {
		sprite = new Sprite("test.png");
		getViewArea().add(sprite);
	}
}

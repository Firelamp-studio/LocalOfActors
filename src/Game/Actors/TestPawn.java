package Game.Actors;

import API.Pawn;
import API.Annotations.ActionCallable;
import API.Components.Sprite;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class TestPawn extends Pawn {
	
	public TestPawn() {
		setSprite(new Sprite("test.png"));
	}
	
	@Override
	protected void beginPlay() {
		moveTo(new Vector(500, 200), "");
	}
}

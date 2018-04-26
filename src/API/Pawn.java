package API;

import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Pawn extends Actor {
	private TimerAction moveTimer;
	private int moveSteps;
	private int addSteps;
	
	public final void moveTo(Vector location, String actionCaller) {
		moveSteps = 0;
		addSteps = 0;
		
		Vector diff = getLocation().difference(location);
		Vector dist = getLocation().distance(location);
		
		if(diff.equals(new Vector())) {
			return;
		}
		
		int xIncr = diff.x > 0 ? -1 : 1;
		int yIncr = diff.y > 0 ? -1 : 1;
		
		char longAxis;
		int longLength, shortLength, moveModule;
		if(dist.x > dist.y) {
			longAxis = 'x';
			longLength = dist.x;
			shortLength = dist.y;
		} else {
			longAxis = 'y';
			longLength = dist.y;
			shortLength = dist.x;
		}
		
		if(shortLength != 0) {
			
			moveModule = longLength / shortLength;
			addSteps = longLength % shortLength;
			
		} else {
			moveModule = -1;
		}
	
		moveTimer = new TimerAction(true, 10, this, "pawn-walking-loop", actionCaller, location, longAxis, longLength, moveModule, xIncr, yIncr);
		moveTimer.execute();
	}
	
	public final void moveTo(Element element, String actionCaller) {
		moveTo(element.getLocation(), actionCaller);
	}
	
	public final void moveTo(Vector location) {
		moveTo(location, null);
	}
	
	public final void moveTo(Element element) {
		moveTo(element, null);
	}
	
	@ActionCallable(name = "pawn-walking-loop")
	public void pawnWalkingLoop(String actionCaller, Vector location, char longAxis, int longLength, int moveModule, int xIncr, int yIncr) {
		
		moveSteps++;
		
		Vector pawnLoc = getLocation();
		
		float degrees = (float) Math.toDegrees((Math.atan2(pawnLoc.y - location.y, pawnLoc.x - location.x) - Math.PI / 2));
		setRotation(degrees);
		
		
		if(moveSteps >= longLength) {
			moveTimer.kill();
			
			if(actionCaller != null)
				actionCall(actionCaller);
		}
		
		if(longAxis == 'x') {
			int shortShift;
			if(moveModule > 0) {
				shortShift = (pawnLoc.x % moveModule) == 0 ? yIncr : 0;

				if(addSteps > 0) {
					addSteps--;
					shortShift += yIncr;
					System.out.println(addSteps);
				}
			} else {
				shortShift = 0;
			}
				
			setLocation(pawnLoc.x + xIncr, pawnLoc.y + shortShift);
			
		} else {
			int shortShift;
			
			if(moveModule > 0) {
				shortShift = (pawnLoc.y % moveModule) == 0 ? xIncr : 0;
				if(addSteps > 0) {
					addSteps--;
					shortShift += xIncr;
				}
			} else {
				shortShift = 0;
			}
			
			setLocation(pawnLoc.x + shortShift, pawnLoc.y + yIncr);
			
		}
	}
}

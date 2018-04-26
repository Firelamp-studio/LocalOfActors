package API;

import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Pawn extends Actor {
	private TimerAction moveTimer;
	private int xSteps;
	private int ySteps;
	
	public final void moveTo(Vector location, String actionCaller) {
		
		Vector diff = getLocation().difference(location);
		Vector dist = getLocation().distance(location);
		
		if(diff.equals(new Vector())) {
			return;
		}
		
		int xIncr = diff.x > 0 ? -1 : 1;
		int yIncr = diff.y > 0 ? -1 : 1;
	
		int longLength = dist.x > dist.y ? dist.x : dist.y;
		int shortLength = dist.x < dist.y ? dist.x : dist.y;
	
		int shortModule;
		
		if(shortLength != 0) {
			shortModule = longLength / shortLength;
		} else {
			shortModule = -1;
		}
		
		int xModule =  dist.x < dist.y ? shortModule : 1;
		int yModule =  dist.x > dist.y ? shortModule : 1;
	
		xSteps = dist.x;
		ySteps = dist.y;
		
		moveTimer = new TimerAction(true, 10, this, "pawn-walking-loop", actionCaller, location, xModule, yModule, xIncr, yIncr);
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
	public void pawnWalkingLoop(String actionCaller, Vector location, int xModule, int yModule, int xIncr, int yIncr) {
		
		xSteps--;
		ySteps--;
		
		Vector pawnLoc = getLocation();
		
		float degrees = (float) Math.toDegrees((Math.atan2(pawnLoc.y - location.y, pawnLoc.x - location.x) - Math.PI / 2));
		setRotation(degrees);
		
		
		if(xSteps < 0 && ySteps < 0) {
			moveTimer.kill();
			
			if(actionCaller != null)
				actionCall(actionCaller);
		}
	
		int xShift, yShift;
		
		if(yModule > 0) {
			yShift = (pawnLoc.y % yModule) == 0 ? yIncr : 0;
		} else {
			yShift = 0;
		}
		
		if(xModule > 0) {
			xShift = (pawnLoc.x % xModule) == 0 ? xIncr : 0;
		} else {
			xShift = 0;
		}
			
		setLocation(pawnLoc.x + xShift, pawnLoc.y + yShift);
			
	
	}
}

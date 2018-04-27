package API;

import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Pawn extends Actor {
	private TimerAction moveTimer;
	private int xSteps;
	private int ySteps;
	private int yAddSteps;
	private int xAddSteps;
	
	public final void moveTo(Vector location, String actionCaller) {
		xAddSteps = 0;
		yAddSteps = 0;
		
		Vector diff = getLocation().difference(location);
		Vector dist = getLocation().distance(location);
		
		if(diff.equals(new Vector())) {
			return;
		}
		
		int xIncr = diff.x > 0 ? -1 : 1;
		int yIncr = diff.y > 0 ? -1 : 1;
	
		int longLength = dist.x > dist.y ? dist.x : dist.y;
		int shortLength = dist.x < dist.y ? dist.x : dist.y;
	
		int shortModule, rest = 0;
		
		xSteps = dist.x;
		ySteps = dist.y;
		
		if(shortLength != 0) {
			shortModule = longLength / shortLength;
			rest = longLength % shortLength;
			
			if(rest > 0) {
				
				if(dist.x > dist.y) {
					if(xSteps > 0) {
						xAddSteps = rest;
						xSteps -= xAddSteps;
					} else {
						yAddSteps = rest;
						ySteps -= yAddSteps;
					}
				} else {
					if(ySteps > 0) {
						yAddSteps = rest;
						ySteps -= yAddSteps;
					} else {
						xAddSteps = rest;
						xSteps -= xAddSteps;
					}
				}
			}
			
		} else {
			shortModule = -1;
		}
		
		int xModule =  dist.x < dist.y ? shortModule : 1;
		int yModule =  dist.x > dist.y ? shortModule : 1;
		
		
		
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
		
		
		if(xSteps <= 0 && ySteps <= 0) {
			moveTimer.kill();
			
			if(actionCaller != null)
				actionCall(actionCaller);
			
			return;
		}
	
		int xShift, yShift;
		
		if(xModule > 0) {
			xShift = (xSteps % xModule) == 0 ? xIncr : 0;
			
			
			if(xAddSteps > 0) {
				xAddSteps--;
				xShift *= 2;
			}
		
		} else {
			xShift = 0;
		}
		
		if(yModule > 0) {
			yShift = (ySteps % yModule) == 0 ? yIncr : 0;
			
			if(yAddSteps > 0) {
				yAddSteps--;
				yShift *= 2;
			}

		} else {
			yShift = 0;
		}
		
		Vector nextLoc = new Vector(pawnLoc.x + xShift, pawnLoc.y + yShift);
		setLocation(nextLoc);
			
		float degrees = (float) Math.toDegrees((Math.atan2(pawnLoc.y - location.y, pawnLoc.x - location.x) - Math.PI / 2));
		setRotation(degrees);
	
	}
}

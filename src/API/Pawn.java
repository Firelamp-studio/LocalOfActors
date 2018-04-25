package API;

import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Pawn extends Actor {
	TimerAction walkTimer;
	private int walkSteps;
	
	public final void moveTo(Vector location, String actionCaller) {
		walkSteps = 0;
		
		walkTimer = new TimerAction(true, 10, this, "pawn-walking-loop", actionCaller, location);
		walkTimer.execute();
	}
	
	public final void moveTo(Element element, String actionCaller) {
		moveTo(element.getLocation(), actionCaller);
	}
	
	@ActionCallable(name = "pawn-walking-loop")
	public void pawnWalkingLoop(String actionCaller, Vector location) {
		
		walkSteps++;
		
		
		Vector pawnLoc = getLocation();
		
		float angle = (float) (Math.atan2(pawnLoc.y - location.y, pawnLoc.x - location.x) - Math.PI / 2);
		setRotation(angle);
		
		
		Vector diff = getLocation().difference(location);
		
		int xIncr, yIncr;
		xIncr = diff.x > 0 ? 1 : -1;
		yIncr = diff.y > 0 ? 1 : -1;
		
		int trasl, walkModul;
		
		if(diff.x > diff.y) {
			
			walkModul = diff.x / diff.y;
			
			trasl = pawnLoc.x % walkModul == 0 ? yIncr : 0;
			
			setLocation(pawnLoc.x+xIncr, pawnLoc.y + trasl);
			
			if(walkSteps >= diff.x) {
				walkTimer.kill();
				actionCall(actionCaller);
			}
			
		} else {
			
			walkModul = diff.y / diff.x;
			
			trasl = pawnLoc.y % walkModul == 0 ? xIncr : 0;
			
			setLocation(pawnLoc.x + trasl, pawnLoc.y+yIncr);
			
			if(walkSteps >= diff.y) {
				walkTimer.kill();
				actionCall(actionCaller);
			}
			
		}
	}
}

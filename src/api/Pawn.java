package api;

import api.annotations.ActionCallable;
import api.utility.Rotator;
import api.utility.TimerAction;
import api.utility.Vector;
import game.maps.BarMap;

public abstract class Pawn extends Actor {
	private TimerAction moveTimer;
	private int xSteps;
	private int ySteps;
	private int walkingSteps;
	private Object[] argsAfterMoveTo;

	@Override
	protected void beginPlay() {
		super.beginPlay();

		long delay = 4;
		if(getAreaMap() instanceof BarMap)
			delay = ((BarMap) getAreaMap()).getGameSpeed();

		moveTimer = new TimerAction(true, delay, this, "pawn-walking-loop");
	}

	public final void moveTo(Vector location, String actionCaller, Object... args) {
		walkingSteps = 0;
		Vector diff = getLocation().difference(location);
		Vector dist = getLocation().distance(location);

		if(diff.equals(new Vector())) {
			return;
		}

		argsAfterMoveTo = args;

		int xIncr = diff.x > 0 ? -1 : 1;
		int yIncr = diff.y > 0 ? -1 : 1;

		int longLength = dist.x > dist.y ? dist.x : dist.y;
		int shortLength = dist.x < dist.y ? dist.x : dist.y;

		int shortModule;

		xSteps = dist.x;
		ySteps = dist.y;

		if(shortLength != 0) {
			shortModule = longLength / shortLength;

		} else {
			shortModule = -1;
		}

		int xModule =  dist.x < dist.y ? shortModule : 1;
		int yModule =  dist.x > dist.y ? shortModule : 1;


		moveTimer.execute(actionCaller, location, xModule, yModule, xIncr, yIncr);
	}
	
	public final void moveTo(Element element, String actionCaller, Object... args) {
		moveTo(element.getLocation(), actionCaller, args);
	}
	
	public final void moveTo(Vector location) {
		moveTo(location, null);
	}
	
	public final void moveTo(Element element) {
		moveTo(element, null);
	}

	@ActionCallable(name = "pawn-walking-loop")
	public void pawnWalkingLoop(String actionCaller, Vector location, int xModule, int yModule, int xIncr, int yIncr) {
		if(moveTimer == null || !moveTimer.isAlive() || moveTimer.getKillRequested())
			return;

		walkingSteps++;
		
		Vector pawnLoc = getLocation();
	
		int xShift = 0;
		int yShift = 0;
		
		if(xModule > 0) {
				
			if(xSteps > 0 && (walkingSteps % xModule == 0)) {
				xShift = xIncr;
				xSteps--;
			}
		}
		
		if(yModule > 0) {
			
			if(ySteps > 0 && (walkingSteps % yModule == 0)) {
				yShift = yIncr;
				ySteps--;
			}
		}
		
		Vector nextLoc = new Vector(pawnLoc.x + xShift, pawnLoc.y + yShift);
		setLocation(nextLoc);
			
		
		if(xSteps <= 0 && ySteps <= 0) {
			moveTimer.kill();

			if(actionCaller != null && !actionCaller.isEmpty())
			    actionCall(this, actionCaller, argsAfterMoveTo);
			
			return;
		}

		/*
		float degrees;
		if(xSteps > 0 && ySteps > 0){
			degrees = 90 + (float)Math.toDegrees((Math.atan2(yModule, xModule) - Math.PI / 2));
		} else {
			degrees = (float) Math.toDegrees((Math.atan2(pawnLoc.y - location.y, pawnLoc.x - location.x) - Math.PI / 2));
		}*/
		
		setRotation(Rotator.rotationLookingTo(pawnLoc, location));
	
	}
}

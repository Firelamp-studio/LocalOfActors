package Game.Actors;

import java.util.Random;

import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.DrinkCard;
import Game.gui.CustomerInfo;

public class Customer extends Person {
	private TimerAction timerChooseAction;
	private DrinkCard drinkCard;
	private CashDesk cashDesk;
	
	public Customer() {
		if(Math.random() > 0.5) {
			setSprite("man.png", 0.3);
		} else {
			setSprite("woman.png", 0.3);
		}
	}
	
	@Override
	protected void beginPlay() {
		super.beginPlay();
		
		//actionCallResponse(/*TODO get localTail from map*/, "get-in-line-for-entry", this);
	}

    @Override
    protected void tick(long deltaTime) {
    	
    }
    
	@ActionResponse(name = "get-in-line-for-entry")
	public void getInLineForEntry(Vector vector) {
		moveTo(vector);
	}

	@ActionCallable(name = "entry-into-local")
	public void moveToCashdesk() {
		moveTo(this, "arrived-to-cashdesk");
	}
	
	@ActionCallable(name = "arrived-to-cashdesk")
	public void payAndGetCard() {
		actionCallResponse(cashDesk, "pay-and-get-card", this);
	}
	
	@ActionResponse(name = "pay-and-get-card")
	public void onCardRecived(DrinkCard drinkCard) {
		this.drinkCard = drinkCard;
		/*long delay = new Random().nextInt(maxWaitTimeMS) + 1;
		timerChooseAction = new TimerAction(true, delay, this, "choose-what-to-do");
		timerChooseAction.execute();*/
		//timerChooseAction.
	}
	
	@ActionCallable(name = "choose-what-to-do")
	public void chooseWahtToDo() {
		
	}
	
    
}

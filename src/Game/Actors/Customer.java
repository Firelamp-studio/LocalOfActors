package Game.Actors;

import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
import API.Utility.Vector;
import Game.DrinkCard;

public class Customer extends Person {
	private DrinkCard drinkCard;
	private CashDesk cashDesk;
	
	public Customer() {
		if(Math.random() > 0.5) {
			setSprite("man.png");
		} else {
			setSprite("woman.png");
		}
	}
	
	@Override
	protected void beginPlay() {
		super.beginPlay();
		
		moveTo(new Vector());
	}

    @Override
    protected void tick(long deltaTime) {

    }
	
	@ActionCallable(name = "entry-into-local")
	public void moveToCashdesk() {
		moveTo(cashDesk, "arrived-to-cashdesk");
	}
	
	@ActionCallable(name = "arrived-to-cashdesk")
	public void payAndGetCard() {
		actionCallResponse(cashDesk, "pay-and-get-card", this);
	}
	
	@ActionResponse(name = "pay-and-get-card")
	public void onCardRecived() {
		//TODO Fai il cazzo che vuoi dentro il locale
	}
	
    public void setDrinkCard(DrinkCard drinkCard) {
    	this.drinkCard = drinkCard;
    }
    
}

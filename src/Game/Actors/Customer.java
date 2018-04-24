package Game.Actors;

import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
import Game.DrinkCard;

public class Customer extends Person {
	private DrinkCard drinkCard;
	private CashDesk cashDesk;
	
	public Customer() {
		
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

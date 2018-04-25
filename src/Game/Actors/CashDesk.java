package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;

public class CashDesk extends Actor {

    @Override
    protected void tick(long deltaTime) {

    }
    
    @ActionCallable(name = "pay-and-get-card")
    public void giveCard(Customer customer) {
    	//TODO prenditi i soldi
    }
}

package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Counter extends Actor {
	private CounterTail counterTail;
	private Barman freeBarman;
	
	public Counter(CounterTail counterTail) {
		this.counterTail = counterTail;
		setSprite("counter.png");
		tickEnabled = true;
	}

    @Override
    protected void tick(long deltaTime) {
        if (counterTail.isModifyEnabled() /*c'è un barman libero*/){
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            counterTail.setModifyEnabled(false);
            Customer customer = counterTail.dequeueCustomer("entry-counter-line-and-movement");
            //gestire i barman;
            //System.out.println("DENTRO CI SONO " + numPeopleInside + " PERSONE");
            customer.moveTo(new Vector(0, 60), "arrived-to-barman");
            new TimerAction(1200, this, "close-door").execute();
        }
    }
    /*
    @ActionCallable(name = "barman-is-free")
    public void serveCustomer(Barman barman) {
    	freeBarman = barman;
    	actionCall(counterTail, "go-to-barman");
    }
    
    @ActionCallable(name = "get-free-barman")
    public Barman getFreeBarman() {
    	return freeBarman;
    }*/
}

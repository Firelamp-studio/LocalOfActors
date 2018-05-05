package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;

public class Counter extends Actor {
	private CounterTail counterTail;
	private Barman freeBarman;
	
	public Counter(CounterTail counterTail) {
		this.counterTail = counterTail;
	}

    @Override
    protected void tick(long deltaTime) {

    }
    
    @ActionCallable(name = "barman-is-free")
    public void serveCustomer(Barman barman) {
    	freeBarman = barman;
    	actionCall(counterTail, "go-to-barman");
    }
    
    @ActionCallable(name = "get-free-barman")
    public Barman getFreeBarman() {
    	return freeBarman;
    }
}

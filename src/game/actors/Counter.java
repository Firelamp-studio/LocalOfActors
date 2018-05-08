package game.actors;

import api.Actor;
import api.annotations.ActionCallable;
import api.annotations.ActionResponse;
import api.utility.Vector;

public class Counter extends Actor {
	private CounterTail counterTail;
	private Barman[] barmans;
	
	public Counter(CounterTail counterTail, Barman barmanLeft, Barman barmanCenter, Barman barmanRight) {
		this.counterTail = counterTail;
        barmans = new Barman[3];
        barmans[0] = barmanLeft;
        barmans[1] = barmanCenter;
        barmans[2] = barmanRight;
        setSprite("counter.png");
		tickEnabled = true;
	}

    @Override
    protected void tick(long deltaTime) {
        if (!counterTail.getWaitingCustomers().isEmpty() && counterTail.isModifyEnabled()){
            boolean isSearchingFreeBarman = true;
	        for (int i = 0; i < 3 && isSearchingFreeBarman; i++) {

                if (barmans[i].isFree()) {
                    barmans[i].setFree(false);
                    counterTail.setModifyEnabled(false);
                    isSearchingFreeBarman = false;

                    actionCall(counterTail, "counter-dequeue-customer", "entry-counter-line-and-movement", i, this);
                }
            }
        }

    }

    @ActionCallable(name = "counter-dequeue-customer")
    public void dequeueResponse(Customer customer, int i){
	    if(customer != null){
            customer.moveTo(barmans[i].getLocation().add(new Vector(0, 130)), "arrived-to-barman", barmans[i]);
        }
    }
}

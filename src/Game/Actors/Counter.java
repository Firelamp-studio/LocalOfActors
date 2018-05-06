package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Counter extends Actor {
	private CounterTail counterTail;
	private Barman[] barmans;
    private Barman freeBarman;
    private boolean isSearchingFreeBarman;
	
	public Counter(CounterTail counterTail, Barman barmanLeft, Barman barmanCenter, Barman barmanRight) {
		this.counterTail = counterTail;
        barmans = new Barman[3];
        barmans[0] = barmanLeft;
        barmans[1] = barmanCenter;
        barmans[2] = barmanRight;
        freeBarman = null;
        setSprite("counter.png");
		tickEnabled = true;
	}

    @Override
    protected void tick(long deltaTime) {
        if (!counterTail.getWaitingCustomers().isEmpty() && counterTail.isModifyEnabled()){
            isSearchingFreeBarman = true;
	        for (int i = 0; i < 3 && isSearchingFreeBarman; i++) {

                if (barmans[i].isFree()) {
                    barmans[i].setFree(false);

                    actionCallResponse(counterTail, "counter-dequeue-customer", "entry-counter-line-and-movement", i);

                    counterTail.setModifyEnabled(false);
                    isSearchingFreeBarman = false;
                }
            }
        }

    }

    @ActionResponse(name = "counter-dequeue-customer")
    public void dequeueResponse(Customer customer){
	    if(customer != null){
            int i = customer.servingBarman;
            customer.moveTo(barmans[i].getLocation().add(new Vector(0, 130)), "arrived-to-barman", barmans[i]);
            customer.servingBarman = -1;
        }
    }
}

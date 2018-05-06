package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Counter extends Actor {
	private CounterTail counterTail;
	private Barman[] barmans;
    private Barman freeBarman;
	
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
	    for (int i = 0; i < 3; i++) {
            if (barmans[i].isFree()) {
                if (counterTail.isModifyEnabled() ){
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    counterTail.setModifyEnabled(false);
                    Customer customer = counterTail.dequeueCustomer("entry-counter-line-and-movement");
                    //gestire i barman;
                    customer.moveTo(barmans[i].getLocation().add(new Vector(0, 150)), "arrived-to-barman", barmans[i]);
                    break;
                }
            }
        }

    }
}

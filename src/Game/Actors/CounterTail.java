package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Vector;

public class CounterTail extends Tail {

    public CounterTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
    }

    @ActionCallable(name = "get-in-line-for-order")
    public Vector newPersonInLocalQueue(Customer customer) {
        addToTail(customer);
        return getPersonPositionInQueue(customer);
    }

    public Customer letPersonOrder() {
        Customer customer = getWaitingCustomers().pop();
        getWaitingCustomers().forEach((c)->{
            c.moveTo(getPersonPositionInQueue(c), "entry-counter-line-and-movement");
        });
        return customer;
    }

    @Override
    protected Vector getPersonPositionInQueue(Customer customer) {
        int xOffset = getTailSize() / 6;
        int tempYOffset = getTailSize() % 6;
        int alternateOffset = xOffset % 2;
        int moreUp = xOffset > 0 ? 1 : 0;
        int yOffset = alternateOffset == 0 ? tempYOffset + moreUp : 5 + moreUp - tempYOffset;

        return getLocation().add(new Vector(xOffset * -50, yOffset * 50));
    }
}

package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Vector;

public class CounterTail extends Tail {

    public CounterTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
    }

    @ActionCallable(name = "get-in-line-for-order")
    public Vector newPersonInLocalQueue(Customer customer) {
        int xOffset = getTailSize() / 6;
        int tempYOffset = getTailSize() % 6;
        int alternateOffset = xOffset % 2;
        int yOffset = 5 * alternateOffset - tempYOffset;
        int secondOffset = xOffset > 0 ? 50 : 0;
        addToTail(customer);
        return getLocation().add(new Vector(xOffset * -50, yOffset * 50 + secondOffset));
    }

    public Customer letPersonOrder() {
        Customer customer = getWaitingCustomers().pop();
        getWaitingCustomers().forEach((c)->{
            c.moveTo(getPersonPositionInQueue(c), "entry-counter-line-and-movement");
        });
        return customer;
    }

    private Vector getPersonPositionInQueue(Customer customer) {
        for (int i = 0; i < getTailSize(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                return getLocation().add(new Vector(0,i * 50));
            }
        }
        return null;
    }
}

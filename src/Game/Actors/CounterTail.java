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
        System.out.println(" - AlternateOffset: " + alternateOffset);
        int moreUp = xOffset > 0 ? 1 : 0;
        int yOffset = alternateOffset == 0 ? tempYOffset + moreUp : 5 + moreUp - tempYOffset;

        addToTail(customer);
        return getLocation().add(new Vector(xOffset * -50, yOffset * 50));
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

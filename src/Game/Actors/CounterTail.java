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
        int relativeY = getWaitingCustomers().size() - 1;
        return getLocation().add(new Vector(0,relativeY * 40));
    }

    public Customer letPersonOrder() {
        Customer customer = getWaitingCustomers().pop();
        getWaitingCustomers().forEach((c)->{
            c.moveTo(getPersonPositionInQueue(c), "entry-counter-line-and-movement");
        });
        return customer;
    }

    private Vector getPersonPositionInQueue(Customer customer) {
        for (int i = 0; i < getWaitingCustomers().size(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                return getLocation().add(new Vector(0,i * 40));
            }
        }
        return null;
    }

    @ActionCallable(name = "customer-arrived-to-position")
    public void customerArrivedToPosition(Customer customer) {
        if (getWaitingCustomers().getFirst() == customer) {
            setModifyEnabled(true);
        }
    }

}

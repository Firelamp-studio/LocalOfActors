package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Vector;

public class LocalTail extends Tail {

    public LocalTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
    }

    @ActionCallable(name = "get-in-line-for-entry")
    public Vector newPersonInLocalQueue(Customer customer) {
        addToTail(customer);
        int relativeX = getWaitingCustomers().size() - 1;
        return getLocation().add(new Vector(relativeX * 40,0));
    }

    public Customer letPersonEntry() {
        Customer customer = getWaitingCustomers().pop();
        getWaitingCustomers().forEach((c)->{
            c.moveTo(getPersonPositionInQueue(c), "entry-local-line-and-movement");
        });
        return customer;
    }

    private Vector getPersonPositionInQueue(Customer customer) {
        for (int i = 0; i < getWaitingCustomers().size(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                return getLocation().add(new Vector(i * 40,0));
            }
        }
        return null;
    }
}

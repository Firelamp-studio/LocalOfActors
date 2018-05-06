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
        int relativeX = getTailSize() - 1;
        return getLocation().add(new Vector(relativeX * 50,0));
    }

    public Customer letPersonEntry() {
        Customer customer = getWaitingCustomers().pop();
        getWaitingCustomers().forEach((c)->{
            c.moveTo(getPersonPositionInQueue(c), "entry-local-line-and-movement");
        });
        return customer;
    }

    @Override
    protected Vector getPersonPositionInQueue(Customer customer) {
        for (int i = 0; i < getTailSize(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                return getLocation().add(new Vector(i * 50,0));
            }
        }
        return null;
    }
}

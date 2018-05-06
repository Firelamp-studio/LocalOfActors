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
            c.moveTo(getPersonPositionInQueue(c), "entry-line-end-movement");
        });
        return customer;
    }

    @ActionCallable(name = "customer-arrived-to-position")
    public void customerArrivedToPosition(Customer customer) {
        if (getWaitingCustomers().getFirst() == customer) {
            setModifyEnabled(true);
        }
    }
}

package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Vector;

public class LocalTail extends Tail {

    public LocalTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
    }

    @ActionCallable(name = "get-in-line-for-entry")
    public Vector newPersonInLocalQueue(Customer customer) {
        return newPersonInQueue(customer);
    }

    @ActionCallable(name = "get-queue-position")
    public Vector getQueuePosition(Customer customer) {
        return getPersonPositionInQueue(customer);
    }

    public Customer letPersonEntry() {
        return customerLeaveQueue();
    }

    @ActionCallable(name = "customer-arrived-to-position")
    public void customerArrivedToPosition(Customer customer) {
        if (getWaitingCustomer().getFirst() == customer) {
            setModifyEnabled(true);
        }
    }
}

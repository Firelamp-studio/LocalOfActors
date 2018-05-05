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

    public Customer letPersonEntry() {
        return customerLeaveQueue();
    }

    @ActionCallable(name = "customer-arrived-to-position")
    public void customerArrivedToPosition(Customer customer) {
        System.out.println("arrivato");
        if (getWaitingCustomers().getFirst() == customer) {
            System.out.println("primo");
            setModifyEnabled(true);
        }
    }
}

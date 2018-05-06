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

    @ActionCallable(name = "let-person-order")
    public void letPersonEntry() {
        customerLeaveQueue();
    }

}

package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Vector;

public class CounterTail extends Tail {

    public CounterTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
    }

    @ActionCallable(name = "get-in-line-for-order")
    public Vector newPersonInLocalQueue(Customer customer) {
        return newPersonInQueue(customer);
    }

    @ActionCallable(name = "let-person-order")
    public void letPersonEntry() {
        customerLeaveQueue("go-to-barman");
    }

}

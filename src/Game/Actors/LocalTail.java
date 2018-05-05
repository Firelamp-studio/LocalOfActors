package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Vector;

public class LocalTail extends Tail {

    public LocalTail(int maxPeopleInQueue, String filename) {
        super(maxPeopleInQueue);
        setSprite(filename);
    }

    @ActionCallable(name = "get-in-line-for-entry")
    public Vector newPersonInLocalQueue(Customer customer) {
        return newPersonInQueue(customer);
    }

    @ActionCallable(name = "let-person-entry")
    public void letPersonEntry() {
        customerLeaveQueue("entry-into-local");
    }

}

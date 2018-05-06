package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Vector;

public class LocalTail extends Tail {
    //"entry-local-line-and-movement"

    public LocalTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
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

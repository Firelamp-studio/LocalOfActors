package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Rotator;
import API.Utility.Transform;
import API.Utility.Vector;

public class CounterTail extends Tail {
    //"entry-counter-line-and-movement"

    public CounterTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
    }

    @Override
    protected Transform getPersonTransformInQueue(Customer customer) {
        int xOffset = getTailSize() / 6;
        int tempYOffset = getTailSize() % 6;
        int alternateOffset = xOffset % 2;
        int moreUp = xOffset > 0 ? 1 : 0;
        int yOffset = alternateOffset == 0 ? tempYOffset + moreUp : 5 + moreUp - tempYOffset;

        return new Transform(getLocation().add(new Vector(xOffset * -70, yOffset * 40)), new Rotator(180 * alternateOffset));
    }

    @ActionCallable(name = "counter-enqueue-customer")
    public Transform counterEnqueueCustomer(Customer customer){
        return enqueueCustomer(customer);
    }
}

package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Rotator;
import API.Utility.Transform;
import API.Utility.Vector;

public class CounterTail extends Tail {

    public CounterTail(int maxPeopleInQueue) {
        super(maxPeopleInQueue);
    }

    @Override
    protected Transform getPersonTransformInQueue(Customer customer) {
        int currentIndex = -1;
        for (int i = 0; i < getTailSize(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                currentIndex = i;
            }
        }

        if(currentIndex >= 0){
            int xOffset = currentIndex / 5;
            int tempYOffset = currentIndex % 5;
            int alternateOffset = xOffset % 2;
            int moreUp = xOffset > 0 ? 1 : 0;
            int yOffset = alternateOffset == 0 ? tempYOffset + moreUp : 5 - tempYOffset;

            float rotationOffset = 0;
            if(alternateOffset == 0 && xOffset > 0 && yOffset == 1){
                rotationOffset = 90;
            } else if (alternateOffset == 1 && xOffset > 0 && yOffset == 5){
                rotationOffset = -90;
            }

            return new Transform(getLocation().add(new Vector(xOffset * -70, yOffset * 60)),
                    new Rotator(180 * alternateOffset + rotationOffset));
        }

        return null;
    }

    @ActionCallable(name = "counter-dequeue-customer")
    public Customer counterDequeueCustomer(String actionAfterUpdateQueueLocation, int i) {
        Customer c = dequeueCustomer(actionAfterUpdateQueueLocation);
        c.servingBarman = i;
        return c;
    }

    @ActionCallable(name = "counter-enqueue-customer")
    public Transform counterEnqueueCustomer(Customer customer){
        return enqueueCustomer(customer);
    }
}

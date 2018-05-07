package game.actors;

import api.annotations.ActionCallable;
import api.utility.Rotator;
import api.utility.Transform;
import api.utility.Vector;

public class CounterTail extends Tail {

    @Override
    protected Transform getPersonTransformInQueue(Customer customer) {
        int currentIndex = -1;
        for (int i = 0; i < getTailSize(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                currentIndex = i;
            }
        }

        if(currentIndex >= 0){
            int xOffset = currentIndex / 6;
            int tempYOffset = currentIndex % 6;
            int alternateOffset = xOffset % 2;
            int moreUp = xOffset > 0 ? 1 : 0;
            int yOffset = alternateOffset == 0 ? tempYOffset + moreUp : 6 - tempYOffset;

            float rotationOffset = 0;
            if(alternateOffset == 0 && xOffset > 0 && yOffset == 1){
                rotationOffset = 90;
            } else if (alternateOffset == 1 && xOffset > 0 && yOffset == 6){
                rotationOffset = -90;
            }

            return new Transform(getLocation().add(new Vector(xOffset * -50, yOffset * 50)),
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

package game.actors;

import api.annotations.ActionCallable;
import api.utility.Rotator;
import api.utility.Transform;
import api.utility.Vector;

public class LocalTail extends Tail {

    @Override
    protected Transform getPersonTransformInQueue(Customer customer) {
        for (int i = 0; i < getTailSize(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                return new Transform(getLocation().add(new Vector(i == 0 ? 0: i * 50 + 20, 0)), new Rotator(i == 0 ? 0 : -90));
            }
        }
        return null;
    }

    @ActionCallable(name = "local-enqueue-customer")
    public Transform localEnqueueCustomer(Customer customer){
        return enqueueCustomer(customer);
    }
}

package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Rotator;
import API.Utility.Transform;
import API.Utility.Vector;

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

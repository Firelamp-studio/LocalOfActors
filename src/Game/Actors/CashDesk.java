package Game.Actors;

import API.Annotations.ActionCallable;
import API.Utility.Rotator;
import API.Utility.Transform;
import API.Utility.Vector;

public class CashDesk extends Tail {

    public CashDesk(){
        setSprite("cashdesk.png");
    }

    @Override
    protected Transform getPersonTransformInQueue(Customer customer) {
        for (int i = 0; i < getTailSize(); i++) {
            if (getWaitingCustomers().get(i) == customer) {
                return new Transform(getLocation().add(new Vector(0,i * 50 + 75)), new Rotator(0));
            }
        }
        return null;
    }

    @ActionCallable(name = "cashdesk-enqueue-customer")
    public Transform cashdeskEnqueueCustomer(Customer customer){
        return enqueueCustomer(customer);
    }
}

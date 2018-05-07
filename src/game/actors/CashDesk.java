package game.actors;

import api.Actor;
import api.annotations.ActionCallable;
import api.utility.Rotator;
import api.utility.Transform;
import api.utility.Vector;

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

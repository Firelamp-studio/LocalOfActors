package game.actors;

import api.Actor;
import api.annotations.ActionCallable;
import api.utility.Rotator;
import api.utility.Transform;
import api.utility.Vector;

/**
 * Questa &egrave; la classe astratta coda, che si occupa di gestire una lista di
 * {@link Customer} nel posizionamento logico e grafico
 *
 * <p>Questa classe &egrave; un componente di
 * <a href="{@docRoot}/java/util/package-summary.html#CollectionsFramework">
 * Una cosa che non abbiamo fatto noi</a>.
 *
 * @author  Lorenzo Pecchio
 * @see     Actor
 * @see     Tail
 * @see     LocalTail
 * @see     CounterTail
 * @since 1.0
 */

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

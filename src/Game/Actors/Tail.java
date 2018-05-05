package Game.Actors;

import java.util.ArrayList;

import API.Actor;
import API.Utility.Vector;

public class Tail extends Actor {

    private ArrayList<Customer> waitingCustomer;
    private int maxPeopleInQueue;

    public Tail(int maxPeopleInQueue) {
        waitingCustomer = new ArrayList();
        this.maxPeopleInQueue = maxPeopleInQueue;
    }

    protected Customer get(int index) {
        return waitingCustomer.get(0);
    }

    protected void addToTail(Customer customer) {
        customer.bindActorForEvents(this);
        waitingCustomer.add(customer);
    }

    protected boolean removeCustomer(Customer customer) {
        return waitingCustomer.remove(customer);
    }

    protected Vector newPersonInQueue(Customer customer) {
        addToTail(customer);
        return getLastFreePlace();
    }

    protected void customerLeaveQueue(String actionName) {
        Customer customer = waitingCustomer.get(0);
        if (customer != null) {
            waitingCustomer.remove(0);
            actionCall(customer, actionName);
            boolean b = actionName.equals("entry-into-local");
            dispatchEvent("customer-in-queue-step-forward", b);
        }

    }

    protected boolean removeFirst() {
        try {
            waitingCustomer.remove(0);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;

    }

    protected int getTailSize() {
        return waitingCustomer.size();
    }

    public Vector getLastFreePlace() {
        //TODO ritornare il posto dove si deve mettere l'ultimo
        return new Vector();
    }

}

package Game.Actors;

import java.util.LinkedList;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.Vector;

public abstract class Tail extends Actor {

    private LinkedList<Customer> waitingCustomers;
    private int maxPeopleInQueue;
    private boolean modifyEnabled;

    public Tail(int maxPeopleInQueue) {
        waitingCustomers = new LinkedList();
        this.maxPeopleInQueue = maxPeopleInQueue;
        modifyEnabled = false;
    }

    protected Customer get() {
        return waitingCustomers.get(0);
    }

    protected void addToTail(Customer customer) {
        waitingCustomers.add(customer);
    }

    protected boolean removeCustomer(Customer customer) {
        return waitingCustomers.remove(customer);
    }

    protected boolean removeFirst() {
        try {
            waitingCustomers.remove(0);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;

    }

    public void setModifyEnabled(boolean modifyEnabled) {
        this.modifyEnabled = modifyEnabled;
    }

    public boolean isModifyEnabled() {
        return modifyEnabled;
    }

    public LinkedList<Customer> getWaitingCustomers() {
        return waitingCustomers;
    }

    protected int getTailSize() {
        return waitingCustomers.size();
    }

    @ActionCallable(name = "customer-arrived-to-position")
    public void customerArrivedToPosition(Customer customer) {
        if (!waitingCustomers.isEmpty() && waitingCustomers.getFirst() == customer) {
            setModifyEnabled(true);
            System.out.println("Il cliente può uscire dalla coda");
        }
    }

    public Customer dequeueCustomer(String actionAfterUpdateQueueLocation) {
        Customer customer = getWaitingCustomers().pop();
        getWaitingCustomers().forEach((c)->{
            c.moveTo(getPersonPositionInQueue(c), actionAfterUpdateQueueLocation);
        });
        return customer;
    }

    protected final Vector enqueueCustomer(Customer customer) {
        addToTail(customer);
        return getPersonPositionInQueue(customer);
    }

    protected abstract Vector getPersonPositionInQueue(Customer customer);
}

package Game.Actors;

import java.util.LinkedList;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.Transform;

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

    @ActionCallable(name = "dequeue-customer")
    public Customer dequeueCustomer(String actionAfterUpdateQueueLocation) {
        Customer customer = waitingCustomers.pop();
        getWaitingCustomers().forEach((c)->{
            Transform transform = getPersonTransformInQueue(c);
            c.moveTo(transform.location, actionAfterUpdateQueueLocation, transform.rotation);
        });
        return customer;
    }

    @ActionCallable(name = "enqueue-customer")
    protected final Transform enqueueCustomer(Customer customer) {
        addToTail(customer);
        return getPersonTransformInQueue(customer);
    }

    protected abstract Transform getPersonTransformInQueue(Customer customer);
}

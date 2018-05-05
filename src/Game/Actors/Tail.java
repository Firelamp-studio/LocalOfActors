package Game.Actors;

import java.util.LinkedList;

import API.Actor;
import API.Utility.Vector;

public class Tail extends Actor {

    private LinkedList<Customer> waitingCustomer;
    private int maxPeopleInQueue;
    private boolean modifyEnabled;

    public Tail(int maxPeopleInQueue) {
        waitingCustomer = new LinkedList();
        this.maxPeopleInQueue = maxPeopleInQueue;
        modifyEnabled = false;
    }

    protected Customer get() {
        return waitingCustomer.get(0);
    }

    protected void addToTail(Customer customer) {
        customer.bindManagerForEvents(this);
        waitingCustomer.add(customer);
    }

    protected boolean removeCustomer(Customer customer) {
        return waitingCustomer.remove(customer);
    }

    protected Vector newPersonInQueue(Customer customer) {
        addToTail(customer);
        int relativeX = waitingCustomer.size() - 1;
        return getLocation().add(new Vector(relativeX * 40,0));
    }

    protected Vector getPersonPositionInQueue(Customer customer) {
        for (int i = 0; i < waitingCustomer.size(); i++) {
            if (waitingCustomer.get(i) == customer) {
                return getLocation().add(new Vector(i * 40,0));
            }
        }
        return null;
    }

    protected Customer customerLeaveQueue() {
        Customer customer = waitingCustomer.pop();
        unbindBindedManager(customer);
        dispatchEvent("update-queue-position");
        return customer;
    }

    protected boolean removeFirst() {
        try {
            waitingCustomer.remove(0);
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

    public LinkedList<Customer> getWaitingCustomer() {
        return waitingCustomer;
    }

    protected int getTailSize() {
        return waitingCustomer.size();
    }

}

package Game.Actors;

import java.util.LinkedList;

import API.Actor;
import API.Utility.Vector;

public class Tail extends Actor {

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

    protected Vector newPersonInQueue(Customer customer) {
        addToTail(customer);
        int relativeX = waitingCustomers.size() - 1;
        return getLocation().add(new Vector(relativeX * 40,0));
    }

    protected Vector getPersonPositionInQueue(Customer customer) {
        for (int i = 0; i < waitingCustomers.size(); i++) {
            if (waitingCustomers.get(i) == customer) {
                return getLocation().add(new Vector(i * 40,0));
            }
        }
        return null;
    }

    protected Customer customerLeaveQueue() {
        Customer customer = waitingCustomers.pop();
        waitingCustomers.forEach((c)->{
            c.moveTo(getPersonPositionInQueue(c), "entry-line-end-movement");
        });
        return customer;
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

}

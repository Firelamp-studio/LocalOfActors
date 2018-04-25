package Game.Actors;

import java.util.ArrayList;
import java.util.Random;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;

public class Tail extends Actor {
	private ArrayList<Customer> waitingCustomer;
	
    public Tail(){
    	waitingCustomer = new ArrayList();
    	
    }
    
    protected void addToTail(Customer customer) {
    	waitingCustomer.add(customer);
    }
    
    protected boolean removeCustomer(Customer customer) {
    	return waitingCustomer.remove(customer);
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

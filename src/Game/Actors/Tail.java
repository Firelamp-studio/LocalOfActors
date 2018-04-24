package Game.Actors;

import java.util.ArrayList;
import java.util.Random;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;

public abstract class Tail extends Actor {
	private ArrayList<Customer> waitingCustomer;
	TimerAction timerEntry;
	
    public Tail(){
    }
    
}

package Game.Actors;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
import API.Element;
import API.Utility.TimerAction;
import API.Utility.Transform;
import API.Utility.Vector;
import Game.Maps.BarMap;

public class EntryDoor extends Actor {
    private TimerAction timerSpawnCustomer;
    private BarMap map;
    private Owner owner;
    private Counter counter;
    private CounterTail counterTail;
    private LocalTail localTail;
    private SitGroup sitGroup;
    private CashDesk cashDesk;
    private int numPeopleInside;
    private int i;

    public EntryDoor(LocalTail localTail, Owner owner, CashDesk cashDesk, Counter counter, CounterTail counterTail, SitGroup sitGroup) {
        this.localTail = localTail;
        this.owner = owner;
        this.counter = counter;
        this.counterTail = counterTail;
        this.sitGroup = sitGroup;
        this.cashDesk = cashDesk;
        numPeopleInside = 0;
        setSprite("door.png");
        tickEnabled = true;
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        if (getMap() instanceof BarMap){
            map = (BarMap) getMap();
            i = map.getTotalPeople();
        }
        timerSpawnCustomer = new TimerAction(true, 250 *  map.getGameSpeed() , this, "spawn-customer-iterator" );
        timerSpawnCustomer.execute();
    }

    @Override
    protected void tick(long deltaTime) {
        if (!localTail.getWaitingCustomers().isEmpty() && localTail.isModifyEnabled() && numPeopleInside < map.getMaxLocalPeople()){
            localTail.setModifyEnabled(false);
            actionCallResponse(localTail, "dequeue-customer", "entry-local-line-and-movement");
            numPeopleInside++;
            setRotation(90);
            new TimerAction(map.getGameSpeed() * 140, this, "close-door").execute();
        }
    }

    @ActionResponse(name = "dequeue-customer")
    public void dequeueResponse(Customer customer){
        if(customer != null)
            actionCall(customer, "start-enqueue-cashdesk");
            //customer.moveTo(cashDesk.getLocation().add(new Vector(0, 60)), "arrived-to-cashdesk");
    }

    @ActionCallable(name = "close-door")
    public void closeDoor(){
        setRotation(0);
    }

    @ActionCallable(name = "spawn-customer-iterator")
    public void spawnCustomerIterator() {
        if (i > 0) {
            i--;
            new TimerAction((long)(Math.random() * 150 * map.getGameSpeed()), this, "spawn-customer").execute();
        } else {
            timerSpawnCustomer.kill();
        }
    }

    @ActionCallable(name = "spawn-customer")
    public void spawnCustomer() {
        map.addActor(new Customer(localTail, this, owner, cashDesk, counter, counterTail, sitGroup), new Vector( 1600, 750 ), 10 );
    }

    @ActionCallable(name = "customer-exit")
    public void customerExit(Customer customer) {
        customer.moveTo(new Vector(165, 1200), "destroy-customer-on-exit");
        numPeopleInside--;
        setRotation(90);
        new TimerAction(120 * map.getGameSpeed(), this, "close-door").execute();
    }

}

package Game.Actors;

import java.util.Random;

import API.Actor;
import API.Annotations.ActionCallable;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.Maps.BarMap;

public class EntryDoor extends Actor {
    private TimerAction timerSpawnCustomer;
    private BarMap map;
    private CashDesk cashDesk;
    private Counter counter;
    private CounterTail counterTail;
    private LocalTail localTail;
    private SitGroup sitGroup;
    private int numPeopleInside;
    private int i;

    public EntryDoor(LocalTail localTail, CashDesk cashDesk, Counter counter, CounterTail counterTail, SitGroup sitGroup) {
        this.localTail = localTail;
        this.cashDesk = cashDesk;
        this.counter = counter;
        this.counterTail = counterTail;
        this.sitGroup = sitGroup;
        numPeopleInside = 0;
        setSprite("door.png");
        timerSpawnCustomer = new TimerAction(true, 2750 , this, "spawn-customer-iterator" );
        tickEnabled = true;
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();
        if (getMap() instanceof BarMap){
            map = (BarMap) getMap();
            i = map.getTotalPeople();
        }
        timerSpawnCustomer.execute();
    }

    @Override
    protected void tick(long deltaTime) {
        if (localTail.isModifyEnabled() && numPeopleInside < map.getMaxLocalPeople()){
            localTail.setModifyEnabled(false);
            System.out.println("Porta: sto facendo entrare un cliente");
            Customer customer = localTail.letPersonEntry();
            numPeopleInside++;
            customer.moveTo(cashDesk.getLocation().add(new Vector(0, 60)), "arrived-to-cashdesk");
            setRotation(90);
            new TimerAction(1200, this, "close-door").execute();
        }
    }

    @ActionCallable(name = "close-door")
    public void closeDoor(){
        setRotation(0);
    }

    @ActionCallable(name = "spawn-customer-iterator")
    public void spawnCustomerIterator() {
        if (i > 0) {
            i--;
            new TimerAction((long)(Math.random() * 1500), this, "spawn-customer").execute();
        } else {
            timerSpawnCustomer.kill();
        }
    }

    @ActionCallable(name = "spawn-customer")
    public void spawnCustomer() {
        map.addActor(new Customer(localTail, this, cashDesk, counter, counterTail, sitGroup), new Vector( 600, 750 ), 10 );
    }
    /*
    public void faiEntrareQualcunoOgniTanto(int maxWaitTimeMS){
    	long delay = new Random().nextInt(maxWaitTimeMS) + 1;
        timerAction = new TimerAction(true, delay, this, "entra-dopo-ritardo", delay);
        timerAction.execute();
    }

    @ActionCallable(name = "entra-dopo-ritardo")
    public void entraDopoRitardo(long delay){
        numPersoneEntrate++;
        System.out.println("Sta entrando qualcuno dopo " + (delay/1000.0)*numPersoneEntrate + " secondi!!!");
        if(numPersoneEntrate >= 5){
            timerAction.kill();
        }
    }*/
}

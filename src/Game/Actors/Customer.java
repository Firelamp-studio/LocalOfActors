package Game.Actors;

import java.awt.event.MouseEvent;
import java.util.Random;

import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
import API.Utility.Rotator;
import API.Utility.TimerAction;
import API.Utility.Transform;
import API.Utility.Vector;
import Game.DrinkCard;
import Game.Maps.BarMap;
import Game.gui.CustomerInfo;

public class Customer extends Person {

    private TimerAction timerChooseAction;
    private DrinkCard drinkCard;
    private LocalTail localTail;
    private EntryDoor entryDoor;
    private Owner owner;
    private Counter counter;
    private CounterTail counterTail;
    private Barman barman;
    private SitGroup sitGroup;
    private int wineGlass;
    private static int generateId = 0;
    private int id;
    public int servingBarman;
    private CustomerInfo customerInfo;

    public Customer(LocalTail localTail, EntryDoor entryDoor, Owner owner, Counter counter, CounterTail counterTail, SitGroup sitGroup) {
        if (Math.random() > 0.5) {
            setSprite("man.png", 0.4);
        } else {
            setSprite("woman.png", 0.4);
        }

        long delay = new Random().nextInt(2000) + 1;
        timerChooseAction = new TimerAction(delay, this, "do-something");

        drinkCard = null;
        this.localTail = localTail;
        this.entryDoor = entryDoor;
        this.owner = owner;
        this.counter = counter;
        this.counterTail = counterTail;
        this.sitGroup = sitGroup;
        servingBarman = -1;
        wineGlass = 0;
        generateId++;
        id = generateId;
        customerInfo = new CustomerInfo(new Vector(100));
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();

        //moveTo(sitGroup.getArmchairLocation(sitGroup.getFreeSitIndex()));
        actionCallResponse(localTail, "local-enqueue-customer", this);
        addRelativeComponent(customerInfo, new Vector(0, 80),100);
    }

    @Override
    protected void tick(long deltaTime) {

    }

    @ActionResponse(name = "local-enqueue-customer")
    public void getInLineForEntry(Transform transform) {
        moveTo(transform.location, "entry-local-line-and-movement", transform.rotation);
    }

    @ActionCallable(name = "entry-local-line-and-movement")
    public void entryLocalLineEndMovement(Rotator rotator) {
        actionCall(localTail, "customer-arrived-to-position", this);
        setRotation(rotator.getRotation());
    }


    @ActionCallable(name = "arrived-to-cashdesk")
    public void payAndGetCard() {
        actionCall(owner, "pay-and-get-card", this);
    }

    @ActionCallable(name = "pay-and-get-card")
    public void onCardRecived(DrinkCard drinkCard) {
        this.drinkCard = drinkCard;
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
    }

    @ActionCallable(name = "choose-what-to-do")
    public void chooseWahtToDo() {
        setRotation((float)Math.random()*360);
        timerChooseAction.execute();
    }

    @ActionCallable(name = "do-something")
    public void doSomething() {
        double random = Math.random();
        if (random > 0.3) {
            if (wineGlass > 0){
                wineGlass -= 50;
                chooseWahtToDo();
            } else {
                actionCallResponse(counterTail, "counter-enqueue-customer", this);
            }
        } else if (random > 0.05) {
            actionCallResponse(sitGroup, "sit-on-sit" );
        } else {
            exit();
        }
    }

    @ActionResponse(name = "counter-enqueue-customer")
    public void getInLineForOrder(Transform transform) {
        if (transform!= null) {
            moveTo(transform.location, "entry-counter-line-and-movement", transform.rotation);
        }
        else {
            doSomething();
        }
    }

    @ActionCallable(name = "entry-counter-line-and-movement")
    public void entryCounterLineEndMovement(Rotator rotator) {
        actionCall(counterTail, "customer-arrived-to-position", this);
        setRotation(rotator.getRotation());
    }

    @ActionCallable(name = "arrived-to-barman")
    public void arrivedToBarman(Barman barman) {
        this.barman = barman;
        setRotation(0);
        if (Math.random() < 0.5) {
            barman.orderWine(true, this);
        } else {
            barman.orderWine(false, this);
        }
    }

    @ActionCallable(name = "recive-wine-glass")
    public void getWineAndWait(int wineGlass) {
        this.wineGlass = wineGlass;
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
    }

    @ActionResponse(name = "sit-on-sit")
    public void goToSit(int index) {
        customerInfo.setIntention("Sto andando a sedermi");
        if (index < 0) {
            doSomething();
        } else {
            moveTo(sitGroup.getArmchairLocation(index), "on-arrived-on-sit", index);
        }
    }

    @ActionCallable(name = "on-arrived-on-sit")
    public void onArrivedOnSit(int index) {
        setRotation(0);

        long delay = 1000;
        if (getMap() instanceof BarMap){
            delay = ((BarMap)getMap()).getGameSpeed() * 100;
        }
        new TimerAction( (long) (Math.random() * delay*4 + delay*2), this, "wait-on-sit").execute(index);
    }

    @ActionCallable(name = "wait-on-sit")
    public void waitOnsit(int index) {
        sitGroup.getArmchair(index).setOccupied(false);
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
    }

    private void exit() {
        customerInfo.setIntention("Sto uscendo");
        moveTo(entryDoor.getLocation().add(new Vector(-30, -50)), "open-door-and-exit");
    }

    @ActionCallable(name = "open-door-and-exit")
    public void opernDoorAndEntry(){
        actionCall(entryDoor, "customer-exit", this);
    }

    @ActionCallable(name = "destroy-customer-on-exit")
    public void destroycCustomer() {
        disposeActor();
    }

    public static Vector getWaitingAreaVector() {
        return new Vector((int) (Math.random() * 300 + 250), (int) (Math.random() * 350 + 250));
    }

    public DrinkCard getDrinkCard() {
        return drinkCard;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if(customerInfo.isVisible()) {
            customerInfo.setVisible(false);
        }
        else {
            customerInfo.setVisible(true);
        }
    }
}

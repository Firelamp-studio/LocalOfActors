package Game.Actors;

import java.awt.event.MouseEvent;
import java.util.Random;

import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
import API.Annotations.BindableEvent;
import API.Utility.TimerAction;
import API.Utility.Vector;
import Game.DrinkCard;
import Game.gui.CustomerInfo;

public class Customer extends Person {

    private TimerAction timerChooseAction;
    private DrinkCard drinkCard;
    private LocalTail localTail;
    private EntryDoor entryDoor;
    private CashDesk cashDesk;
    private Counter counter;
    private CounterTail counterTail;
    private Barman barman;
    private SitGroup sitGroup;
    private int armchairIndex;
    CustomerInfo customer;

    public Customer(LocalTail localTail, EntryDoor entryDoor, CashDesk cashDesk, Counter counter, CounterTail counterTail, SitGroup sitGroup) {
        if (Math.random() > 0.5) {
            setSprite("man.png", 0.3);
        } else {
            setSprite("woman.png", 0.3);
        }

        long delay = new Random().nextInt(2000) + 1;
        timerChooseAction = new TimerAction(false, delay, this, "do-something");

        this.localTail = localTail;
        this.entryDoor = entryDoor;
        this.cashDesk = cashDesk;
        this.counter = counter;
        this.counterTail = counterTail;
        this.sitGroup = sitGroup;
        armchairIndex = -1;
        customer = new CustomerInfo(new Vector(100));
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();

        actionCallResponse(localTail, "get-in-line-for-entry", this);
        addRelativeComponent(customer, new Vector(0, 80));
    }

    @Override
    protected void tick(long deltaTime) {

    }

    @ActionResponse(name = "get-in-line-for-entry")
    public void getInLineForEntry(Vector vector) {
        moveTo(vector, "entry-line-end-movement");
    }

    @ActionCallable(name = "entry-line-end-movement")
    public void entryLineEndMovement() {
        actionCall(localTail, "customer-arrived-to-position", this);
        setRotation(-90);
    }

    @BindableEvent(name = "update-queue-position")
    public void queueStepForward() {

    }

    @ActionCallable(name = "arrived-to-cashdesk")
    public void payAndGetCard() {
        actionCallResponse(cashDesk, "pay-and-get-card");
    }

    @ActionResponse(name = "pay-and-get-card")
    public void onCardRecived(DrinkCard drinkCard) {
        this.drinkCard = drinkCard;
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
    }

    @ActionCallable(name = "choose-what-to-do")
    public void chooseWahtToDo() {
        timerChooseAction.execute();
    }

    @ActionCallable(name = "do-something")
    public void doSomething() {
        double random = Math.random();
        if (random > 0.4) {
            actionCallResponse(counterTail, "get-in-line-for-order", this);
        } else if (random > 0.1) {
            for (int i = 0; i < 4; i++){
                actionCallResponse(sitGroup, "sit-on-sit" );
            }
        } else {
            exit();
        }
    }

    @ActionResponse(name = "sit-on-sit")
    public void goToSit(int index) {
        armchairIndex = index;
        if (index < 0) {
            chooseWahtToDo();
        } else {
            System.out.println(sitGroup.getArmchairLocation(index));
            moveTo(sitGroup.getArmchairLocation(index), "on-arrived-on-sit");
        }
    }

    @ActionCallable(name = "on-arrived-on-sit")
    public void onArrivedOnSit() {
        setRotation(0);
        new TimerAction( (long) (Math.random() * 4000 + 2000), this, "wait-on-sit").execute();
    }

    @ActionCallable(name = "wait-on-sit")
    public void waitOnsit() {
        sitGroup.getArmchair(armchairIndex).setOccupied(false);
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
    }

    @ActionResponse(name = "get-in-line-for-order")
    public void getInLineForOrder(Vector vector) {
        moveTo(vector);
    }

    @ActionCallable(name = "go-to-barman")
    public void getFreeBarman() {
        actionCallResponse(counter, "get-free-barman");
    }

    @ActionResponse(name = "go-to-barman")
    public void moveToBarman(Barman freeBarman) {
        barman = freeBarman;
        moveTo(barman, "go-to-order-wine");
    }

    @ActionCallable(name = "go-to-order-wine")
    public void orderWine() {
        if (Math.random() > 0.5) {
            actionCallResponse(barman, "order-wine", true, drinkCard);
        } else {
            actionCallResponse(barman, "order-wine", false, drinkCard);
        }
    }

    @ActionCallable(name = "give-wine")
    public void getWineAndWait() {
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
    }

    private void exit() {
        //TODO exit
    }

    public Vector getWaitingAreaVector() {
        return new Vector((int) (Math.random() * 300 + 250), (int) (Math.random() * 500 + 210));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        if(customer.isVisible()) {
            customer.setVisible(false);
        }
        else {
            customer.setVisible(true);
        }
    }
}

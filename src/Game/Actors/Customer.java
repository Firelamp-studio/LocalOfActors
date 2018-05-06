package Game.Actors;

import java.awt.event.MouseEvent;
import java.util.Random;

import API.Annotations.ActionCallable;
import API.Annotations.ActionResponse;
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
    private static int generateId = 0;
    private int id;
    CustomerInfo customerInfo;

    public Customer(LocalTail localTail, EntryDoor entryDoor, CashDesk cashDesk, Counter counter, CounterTail counterTail, SitGroup sitGroup) {
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
        this.cashDesk = cashDesk;
        this.counter = counter;
        this.counterTail = counterTail;
        this.sitGroup = sitGroup;
        armchairIndex = -1;
        generateId++;
        id = generateId;
        customerInfo = new CustomerInfo(new Vector(100));
    }

    @Override
    protected void beginPlay() {
        super.beginPlay();

        //moveTo(sitGroup.getArmchairLocation(sitGroup.getFreeSitIndex()));
        actionCallResponse(localTail, "get-in-line-for-entry", this);
        addRelativeComponent(customerInfo, new Vector(0, 80));
    }

    @Override
    protected void tick(long deltaTime) {

    }

    @ActionResponse(name = "get-in-line-for-entry")
    public void getInLineForEntry(Vector vector) {
        moveTo(vector, "entry-local-line-and-movement");
    }

    @ActionCallable(name = "entry-local-line-and-movement")
    public void entryLocalLineEndMovement() {
        System.out.println("Cliente " + id + ": sono in fila per entrare");
        actionCall(localTail, "customer-arrived-to-position", this);
        setRotation(-90);
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
        //if (random > 0.4) {
            actionCallResponse(counterTail, "get-in-line-for-order", this);
        /*} else if (random > 0.1) {
            actionCallResponse(sitGroup, "sit-on-sit" );
        } else {
            exit();
        }*/
    }

    @ActionResponse(name = "get-in-line-for-order")
    public void getInLineForEOrder(Vector vector) {
        if (vector != null)
            moveTo(vector, "entry-counter-line-and-movement");
        else
            doSomething();
    }

    @ActionCallable(name = "entry-order-line-and-movement")
    public void entryCounterLineEndMovement() {
        actionCall(counterTail, "customer-arrived-to-position", this);
        setRotation(0);
    }

    @ActionResponse(name = "sit-on-sit")
    public void goToSit(int index) {
        customerInfo.setIntention("Sto andando a sedermi");
        armchairIndex = index;
        if (index < 0) {
            doSomething();
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
        customerInfo.setIntention("Sto uscendo");
        moveTo(entryDoor.getLocation().add(new Vector(-30, -50)), "open-door-and-exit");
    }

    @ActionCallable(name = "open-door-and-exit")
    public void opernDoorAndEntry(){
        actionCall(entryDoor, "customer-exit", this);
    }

    @ActionCallable(name = "destroy-customer-on-exit")
    public void destroycCustomer() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASJAHDIJAHFKAFHNASIDFHSIUFHNSDIJFNSJKFNSDJIFSIDFNJKSDU");
        disposeActor();
    }

    public Vector getWaitingAreaVector() {
        return new Vector((int) (Math.random() * 300 + 250), (int) (Math.random() * 350 + 250));
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

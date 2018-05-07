package game.actors;

import java.awt.event.MouseEvent;
import java.util.Random;

import api.Actor;
import api.annotations.ActionCallable;
import api.annotations.ActionResponse;
import api.utility.Rotator;
import api.utility.TimerAction;
import api.utility.Transform;
import api.utility.Vector;
import game.DrinkCard;
import game.maps.BarMap;
import game.gui.CustomerInfo;

/**
 * Questa &egrave; la classe che modella il cliente.
 *
 * <p>Ogni comportamento e modo di reagire del cliente &egrave; definito qua
 *
 * <p>Questa classe &egrave; derivata di {@link Actor}.
 *
 * @author  Lorenzo Pecchio
 * @see     Actor
 * @since 1.0
 */

public class Customer extends Person {
    /**
     * {@link TimerAction che gestisce il tempo casuale tra una scelta e l'altra}
     */
    private TimerAction timerChooseAction;

    /**
     * {@link DrinkCard} del cliente
     */
    private DrinkCard drinkCard;

    /**
     * Riferimento alla {@link LocalTail} della mappa
     */
    private LocalTail localTail;

    /**
     * Riferimento alla {@link EntryDoor} della mappa
     */
    private EntryDoor entryDoor;

    /**
     * Riferimento all'{@link Owner} della mappa
     */
    private Owner owner;

    /**
     * Riferimento al {@link Counter} della mappa
     */
    private Counter counter;

    /**
     * Riferimento alla {@link CounterTail} della mappa
     */
    private CounterTail counterTail;

    /**
     * Riferimento al {@link Barman} che lo sta servendo
     */
    private Barman barman;

    /**
     * Riferimento al {@link SitGroup} della mappa
     */
    private SitGroup sitGroup;

    /**
     * Riferimento alla {@link CashDesk} della mappa
     */
    private CashDesk cashDesk;

    /**
     * Quantit&agrave; di vino nel proprio bichiere
     */
    private int wineGlass;

    /**
     * Variabile statica usata al fine di dare un {@link Customer#id} differente ad ogni {@code Customer}
     */
    private static int generateId = 0;

    /**
     * Id del {@code Customer}
     */
    private int id;

    /**
     * palla
     */
    private CustomerInfo customerInfo;

    public Customer(LocalTail localTail, EntryDoor entryDoor, Owner owner,  CashDesk cashDesk, Counter counter, CounterTail counterTail, SitGroup sitGroup) {
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
        this.cashDesk = cashDesk;
        wineGlass = 0;
        generateId++;
        id = generateId;
        customerInfo = new CustomerInfo(new Vector(175, 90), id);

    }

    @Override
    protected void beginPlay() {
        super.beginPlay();

        //moveTo(sitGroup.getArmchairLocation(sitGroup.getFreeSitIndex()));
        actionCallResponse(localTail, "local-enqueue-customer", this);
        addRelativeComponent(customerInfo, new Vector(0, 80),100);
        customerInfo.setIntention("Vado in coda all'entrata");
    }

    @ActionResponse(name = "local-enqueue-customer")
    public void getInLineForEntry(Transform transform) {
        moveTo(transform.location, "entry-local-line-and-movement", transform.rotation);
    }

    @ActionCallable(name = "entry-local-line-and-movement")
    public void entryLocalLineEndMovement(Rotator rotator) {
        customerInfo.setIntention("Sono in coda all'entrata");
        actionCall(localTail, "customer-arrived-to-position", this);
        setRotation(rotator.getRotation());
    }

    @ActionCallable(name = "start-enqueue-cashdesk")
    public void startEnqueueCashdesk(){
        actionCallResponse(cashDesk,"cashdesk-enqueue-customer", this);
    }

    @ActionResponse(name = "cashdesk-enqueue-customer")
    public void cashdeskEnqueueingCustomer(Transform transform){
        customerInfo.setIntention("Sono entrato");
        customerInfo.setIntention("Vado alla cassa");
        moveTo(transform.location, "entry-cashdesk-line-and-movement", transform.rotation);
    }

    @ActionCallable(name = "entry-cashdesk-line-and-movement")
    public void entryCashdeskLineEndMovement(Rotator rotation){
        customerInfo.setIntention("Sono alla cassa");
        actionCall(cashDesk, "customer-arrived-to-position", this);
        setRotation(rotation.getRotation());
    }

    @ActionCallable(name = "arrived-to-cashdesk")
    public void payAndGetCard() {
        actionCall(owner, "pay-and-get-card", this);
    }

    @ActionCallable(name = "pay-and-get-card")
    public void onCardRecived(DrinkCard drinkCard) {
        this.drinkCard = drinkCard;
        customerInfo.bindDrinkCard(drinkCard);
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
        customerInfo.setIntention("Ho ottenuto la tessera");
        customerInfo.setIntention("Scelgo cosa fare");
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
                customerInfo.setIntention("Bevo un po di vino");
                wineGlass -= 50;
                chooseWahtToDo();
            } else {
                customerInfo.setIntention("Vado in coda per il vino");
                actionCallResponse(counterTail, "counter-enqueue-customer", this);
            }
        } else if (random > 0.05) {
            customerInfo.setIntention("Vado a sedermi");
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
        actionCall(barman, "order-wine", Math.random() < 0.5, this);
        customerInfo.setIntention("Sono servito dal barman");
    }

    @ActionCallable(name = "receive-wine-glass")
    public void getWineAndWait() {
        this.wineGlass = 250;
        customerInfo.updateDrinkCard();
        moveTo(getWaitingAreaVector(), "choose-what-to-do");
        customerInfo.setIntention("Ho ottenuto il vino");
        customerInfo.setIntention("Scelgo cosa fare");
    }

    @ActionResponse(name = "sit-on-sit")
    public void goToSit(int index) {
        if (index < 0) {
            doSomething();
        } else {
            moveTo(sitGroup.getArmchairLocation(index), "on-arrived-on-sit", index);
        }
    }

    @ActionCallable(name = "on-arrived-on-sit")
    public void onArrivedOnSit(int index) {
        customerInfo.setIntention("Sono seduto");
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
        customerInfo.setIntention("Scelgo cosa fare");
    }

    @ActionCallable(name = "exit")
    private void exit() {
        customerInfo.setIntention("Me ne vado");
        moveTo(entryDoor.getLocation().add(new Vector(-30, -50)), "open-door-and-exit");
    }

    @ActionCallable(name = "open-door-and-exit")
    public void opernDoorAndEntry(){
        actionCall(entryDoor, "customer-exit", this);
        customerInfo.setVisible(true);
    }

    @ActionCallable(name = "destroy-customer-on-exit")
    public void destroycCustomer() {
        System.out.println("Cliente " + id +": consumazioni vino rosso = " + drinkCard.getRedConsumations() + ", Consumazioni vino bianco = " + drinkCard.getWitheConsumations());
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

package game.actors;

import java.util.LinkedList;

import api.Actor;
import api.annotations.ActionCallable;
import api.utility.Rotator;
import api.utility.Transform;


/**
 * La classe {@code Tail} &egrave; la classe generale che permette di gestire tutte le code.4
 *
 * <p>Estenderla permette di creare una coda che raccolga {@link game.actors.Customer Customer}, e che si possa
 * interfacciare con altri {@link api.Actor Actor} al fine di passargli in gestione i {@link game.actors.Customer Customer}
 * che contiene.
 *
 * <p>{@code Tail} si occupa anche di gestire l'aspetto grafico di una coda.
 *
 * <p>Questa classe &egrave; derivata di {@link api.Actor Actor}.
 *
 * @author  Lorenzo Pecchio
 * @see     Actor
 * @see     LocalTail
 * @see     CounterTail
 * @see     CashDesk
 * @since 1.0
 */
 public abstract class Tail extends Actor {
    /**
     * Lista di persone in coda.
     */
    private LinkedList<Customer> waitingCustomers;

    /**
     * Se &egrave; presente una persona in testa alla coda.
     */
    private boolean modifyEnabled;

    /**
     * Costruisce una coda vuota standard.
     */
    public Tail() {
        waitingCustomers = new LinkedList<>();
        modifyEnabled = false;
    }

    /**
     * Ritorna il primo {@code Customer} della coda.
     *
     * @return il primo {@code Customer} della coda
     */
    protected Customer get() {
        return waitingCustomers.get(0);
    }

    /**
     * Aggiunge un {@code Customer} alla fine della coda.
     *
     * @param customer elemento che viene aggiunto alla fine della coda
     */
    protected void addToTail(Customer customer) {
        waitingCustomers.add(customer);
    }

    /**
     * Rimuove un {@code Customer}
     *
     * @param customer l'elemento che deve essere rimosso dalla coda
     * @return se &egrave; stato trovato e tolto
     */
    protected boolean removeCustomer(Customer customer) {
        return waitingCustomers.remove(customer);
    }

    /**
     * Rimuove il primo {@code Customer} dalla coda
     *
     * @return se &egrave; stato trovato e tolto
     */
    protected boolean removeFirst() {
        try {
            waitingCustomers.remove(0);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;

    }

    /**
     * Imposta la variabile {@link #modifyEnabled}
     *
     * @param modifyEnabled il valore che deve avere
     */
    public void setModifyEnabled(boolean modifyEnabled) {
        this.modifyEnabled = modifyEnabled;
    }

    /**
     * Ottiene il valore di {@link #modifyEnabled}
     *
     * @return il valore di {@link #modifyEnabled}
     */
    public boolean isModifyEnabled() {
        return modifyEnabled;
    }

    /**
     * Ottiene la lista dei {@code Customer} in coda
     *
     * @return {@link LinkedList} contenente i {@code Customer}
     */
    public LinkedList<Customer> getWaitingCustomers() {
        return waitingCustomers;
    }

    /**
     * Ottiene la lunghezza della coda
     *
     * @return la lunghezza della coda
     */
    protected int getTailSize() {
        return waitingCustomers.size();
    }

    /**
     * Azione chiamata da {@link Customer#entryLocalLineEndMovement(Rotator) Customer.entryCashdeskLineEndMovement},
     * {@link Customer#entryCounterLineEndMovement(Rotator) Customer.entryCashdeskLineEndMovement},
     * {@link Customer#entryCashdeskLineEndMovement(Rotator) Customer.entryCashdeskLineEndMovement}.
     *
     * <p>Quando arriva un {@code Customer}, imposta la variabile {@link #modifyEnabled} vera se la coda non &egrave; vuota e se il {@code Customer}
     * &egrave; il primo in coda
     *
     * @param customer {@code Customer} da controllare che sia il primo in coda
     */
    @ActionCallable(name = "customer-arrived-to-position")
    public void customerArrivedToPosition(Customer customer) {
        if (!waitingCustomers.isEmpty() && waitingCustomers.getFirst() == customer) {
            setModifyEnabled(true);
        }
    }

    /**
     * Azione chiamata da {}.
     * TODO
     *
     * @param actionAfterUpdateQueueLocation TODO
     * @return TODO
     */
    @ActionCallable(name = "dequeue-customer")
    public Customer dequeueCustomer(String actionAfterUpdateQueueLocation) {
        if(!waitingCustomers.isEmpty()){
            Customer customer = waitingCustomers.removeFirst();
            waitingCustomers.forEach((c)->{
                Transform transform = getPersonTransformInQueue(c);
                c.moveTo(transform.location, actionAfterUpdateQueueLocation, transform.rotation);
            });
            return customer;
        }
        return null;
    }

    /**
     * Azione chiamata da {}.
     * TODO
     *
     * @param customer TODO
     * @return TODO
     */
    @ActionCallable(name = "enqueue-customer")
    protected final Transform enqueueCustomer(Customer customer) {
        addToTail(customer);
        return getPersonTransformInQueue(customer);
    }

    /**
     * Azione chiamata da {}.
     * TODO
     *
     * @param customer TODO
     * @return TODO
     */
    protected abstract Transform getPersonTransformInQueue(Customer customer);
}

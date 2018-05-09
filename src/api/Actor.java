package api;

import api.annotations.*;
import api.managers.EventManager;
import api.managers.EventManagerTool;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 *
 * La classe {@code Actor} &egrave; la base di questa libreria,
 * estenderla permette di creare oggetti che possono essere eseguiti parallelamente ad altri evitando tutti i
 * problemi legati alla simultaneit&agrave; dei processi.
 *
 * <p>Facendo un paragone con la normale programmazione concorrente in Java, sono come dei {@code Thread} che per&ograve;
 * non generano problemi di concorrenza.
 *
 * <p>In realt&agrave; un {@code Actor} contiene al suo interno un {@code Thread} ma non ci permette di utilizzarlo direttamente.
 *
 * <p>L'{@code Actor}, concettualmente, rappresenta l'attore di una scena. Noi, infatti, non possiamo ascoltare
 * contemporaneamente pi&ugrave; persone, non possiamo formulare pi&ugrave; frasi contemporaneamente...
 * possiamo invece interagire con esse prestando la dovuta attenzione e, una ad
 * una, rispondere e, nel frattempo, fare qualunque altra cosa che per&ograve; non riguardi la comunicazione come:
 * grattarci, osservarci attorno, ragionare...
 *
 * <p>Questo "comunicare" con altri "attori" &egrave;, all'atto pratico, una {@link ActionCallable}, ovvero una azione chiamabile
 * <i>(annotazione da inserire prima della dichiarazione di un metodo)</i>.
 *
 * <p>All'interno della classe si posso definire delle {@link ActionCallable}, ovvero delle azioni che il nostro attore sa
 * svolgere e che gli altri attori possono richiamare.
 *
 * <p>Fino a qu&agrave; niente di strano, il fatto &egrave; che un'{@link ActionCallable} &egrave; per&ograve; un'
 * {@code Azione} che viene chiamata da qualcun'altro; chiamata proprio come intendevamo prima, ovvero che necessita di
 * un dialogo fra due o pi&ugrave; attori.
 *
 * <strong>Facciamo un esempio:</strong>
 * <p>Se sia Marco che Alessia mi chiedono di prestargli una bottiglietta d'acqua (e ne ho per&ograve; una),
 * non potr&ograve; accontentarli tutte e due nello stesso istante ma dovr&ograve; prima prestarla ad uno e poi all'altra.
 * Ecco che questa sarebbe una classica {@link ActionCallable}, ed &egrave; proprio grazie a loro che l'{@code Actor} evita
 * la concorrenza; le {@code ActionCallable} vengono infatti chiamate una per volta, ache se due {@code Actor} hanno
 * effettuato la richiesta in contemporanea.
 *
 * <p>La chiamata di una {@link ActionCallable} non mi impedir&agrave; nel frattempo di fare qualunque altra cosa.
 * (tranne di rispondere ad un'altra chiamata d'azione ,<i>{@link #actionCall(Actor, String, Object...) actionCall()}</i>, ovviamente).
 *
 * <p>&Egrave; facile notare come questo paradigma sia molto pi&ugrave; semplice da comprendere rispetto al classico
 * sistema a {@code Thread}, che necessita la comprensione del funzionamento a basso livello della macchina, l'utilizzo
 * di strumenti come semafori, monitor... per evitare la concorrenza. L'{@code Actor} offre un modo di ragionare molto pi&ugrave;
 * simile alla realt&agrave; semplificando, migliorando e rendendo più comprensibile il lavoro.
 *
 * @author Simone Russo
 * @see ActionCallable
 * @see ActionResponse
 * @see BindableEvent
 * @see AsyncMethod
 * @see EventManager
 * @see EventManagerTool
 * @see Element
 * @see Pawn
 */
public abstract class Actor extends Element implements Runnable, EventManager {

    // Action class for concurrency
    public static class Action {
        private final String actionName;
        private final Method method;
        private final Object[] args;
        private final Actor backCaller;

        public Action(String actionName, Method method, Object[] args, Actor backCaller) {
            this.actionName = actionName;
            this.method = method;
            this.args = args;
            this.backCaller = backCaller;
        }
    }

    // Concurrency properties
    private final List<Method> asyncMethods;
    private final List<Method> actionCallableMethods;
    private final List<Method> actionResponseMethods;
    protected boolean actionsEnabled;
    protected boolean tickEnabled;
    private boolean actionsStopped;
    private boolean tickStopped;
    private List<Action> actionCalls;
    private List<Action> actionCallsToNotify;
    private Thread actorThread;
    
    private EventManagerTool eventManagerTool;
    

    // Constructor
    public Actor() {
        actionsEnabled = true;
        tickEnabled = false;
        actionsStopped = false;
        tickStopped = false;

        actorThread = new Thread(this);
        actorThread.start();

        asyncMethods = new ArrayList<>();
        actionCallableMethods = new ArrayList<>();
        actionResponseMethods = new ArrayList<>();
        actionCalls = Collections.synchronizedList(new LinkedList<Action>());
        actionCallsToNotify = Collections.synchronizedList(new LinkedList<Action>());
        
        eventManagerTool = new EventManagerTool(this);


        for (Method method : getClass().getMethods()) {

            if (method.isAnnotationPresent(AsyncMethod.class)) {
                asyncMethods.add(method);
            }

            else if (actionsEnabled && method.isAnnotationPresent(ActionCallable.class)) {
                actionCallableMethods.add(method);
            }

            else if (actionsEnabled && method.isAnnotationPresent(ActionResponse.class)) {
                actionResponseMethods.add(method);
            }

            else if (actionsEnabled && method.isAnnotationPresent(ActionResponse.class)) {
                actionResponseMethods.add(method);
            }
        }
    }

    @Override
    public List<Method> getBindableMethods() {
        return eventManagerTool.getBindableMethods();
    }

    public List<Method> getAsyncMethods() {
        return asyncMethods;
    }

    public List<Method> getActionCallableMethods() {
        return actionCallableMethods;
    }

    public List<Method> getActionResponseMethods() {
        return actionResponseMethods;
    }

    public void addActionCall(Action element) {
        actionCalls.add(element);
    }

    // Concurrency methods
    @Override
    final public void run() {
        // Execute AsyncMethods
    	if(asyncMethods != null && !asyncMethods.isEmpty()) {
    		for (Method method : asyncMethods) {
                try {
                    method.invoke(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

    	}

        Action[] tempActionCalls;
        ArrayList<Action> actionsToRemove = new ArrayList<>();

        
        Instant currentTime, previousTime = null;
        long tickDuration;
        while (actionsEnabled || tickEnabled) {
        	
            if (!actionsStopped && actionsEnabled){

                if (actionCalls != null && !actionCalls.isEmpty()) {

                    if (!actionsToRemove.isEmpty()) {
                        actionCalls.removeAll(actionsToRemove);
                        actionsToRemove.clear();
                    }

                    tempActionCalls = new Action[actionCalls.size()];
                    tempActionCalls = actionCalls.toArray(tempActionCalls);

                    for (Action action : tempActionCalls) {
                        if(action.method != null){
                            try {
                                Object objResult = action.method.invoke(this, action.args);

                                if(action.backCaller != null){
                                    action.backCaller.actionResponse(action.actionName, objResult);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                        actionsToRemove.add(action);
                    }
                }
            }


            
            if (!tickStopped && tickEnabled){
                currentTime = Instant.now();
            	if(previousTime != null) {
            		tickDuration = Duration.between(previousTime, currentTime).toNanos();
                    tick(tickDuration);
                }
                previousTime = currentTime;
            }
            
            try {
				actorThread.sleep(0, 1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    final public void setTickStopped(boolean stop){
        this.tickStopped = stop;
    }

    final public void setActionsStopped(boolean stop) {
        this.actionsStopped = stop;
    }

    @Override
    final public void bindManagerForEvents(EventManager eventManager) {
    	eventManagerTool.bindActorForEvents(eventManager);
    }

    @Override
    final public void unbindBindedManager(EventManager eventManager) {
    	eventManagerTool.unbindActor(eventManager);
    }

    @Override
    final public void unbindAll() {
    	eventManagerTool.unbindAll();
    }

    @Override
    final public void dispatchEvent(String eventName, Object... args) {
    	eventManagerTool.dispatchEvent(eventName, args);
    }

    final protected void actionCallResponse(Actor actorToCall, String actionName, Object... args) {
        for(Method method : actorToCall.actionCallableMethods){

            ActionCallable actionCallable = method.getAnnotation(ActionCallable.class);

            if(actionName.equals(actionCallable.name()) && method.getParameterCount() == args.length){
                actorToCall.actionCalls.add( new Action(actionName, method, args,this) );
            }
        }
    }

    final protected void actionCallResponse(String actionName, Object... args) {
        actionCallResponse(this, actionName, args);
    }

    final protected void actionCall(Actor actorToCall, String actionName, Object... args) {
        for(Method method : actorToCall.actionCallableMethods){

            ActionCallable actionCallable = method.getAnnotation(ActionCallable.class);

            if(actionName.equals(actionCallable.name()) && method.getParameterCount() == args.length){
                actorToCall.actionCalls.add( new Action(actionName, method, args,null) );
            }
        }
    }

    final protected void actionCall(String actionName, Object... args) {
        actionCall(this, actionName, args);
    }

    final private void actionResponse(String actionName, Object result) {
        for(Method method : actionResponseMethods){

            ActionResponse actionResponse = method.getAnnotation(ActionResponse.class);

            if(actionName.equals(actionResponse.name())){

                try {
                    if( method.getParameterCount() == 1){
                        method.invoke(this, result);
                    } else if (method.getParameterCount() == 0){
                        method.invoke(this);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void actionCallOnNotify(Actor actorToCall, String actionName, Object... args){
        for(Method method : actorToCall.actionCallableMethods){

            ActionCallable actionCallable = method.getAnnotation(ActionCallable.class);

            if(actionName.equals(actionCallable.name()) && method.getParameterCount() == args.length){
                actorToCall.actionCallsToNotify.add( new Action(actionName, method, args,null) );
            }
        }
    }

    public void notifyNextAction(){
        Action[] tempActionCalls = new Action[actionCalls.size()];
        tempActionCalls = actionCallsToNotify.toArray(tempActionCalls);

        for(Action action : tempActionCalls){
            actionCalls.add( action );
            actionCallsToNotify.remove(action);
            return;
        }
    }

    public void notifyAllActions(){
        Action[] tempActionCalls = new Action[actionCalls.size()];
        tempActionCalls = actionCallsToNotify.toArray(tempActionCalls);
        ArrayList<Action> actionsToRemove = new ArrayList<>();

        for(Action action : tempActionCalls){
            actionCalls.add( action );
            actionsToRemove.add(action);
        }

        actionCallsToNotify.removeAll(actionsToRemove);
    }

    public void notifyAllActions(String actionName){
        Action[] tempActionCalls = new Action[actionCalls.size()];
        tempActionCalls = actionCallsToNotify.toArray(tempActionCalls);
        ArrayList<Action> actionsToRemove = new ArrayList<>();

        for(Action action : tempActionCalls){
            if(action.actionName.equals(actionName)){
                actionCalls.add( action );
                actionsToRemove.add(action);
            }
        }

        actionCallsToNotify.removeAll(actionsToRemove);
    }

    public void notifyNextAction(String actionName){
        Action[] tempActionCalls = new Action[actionCalls.size()];
        tempActionCalls = actionCallsToNotify.toArray(tempActionCalls);

        if(tempActionCalls.length > 0){
            for(Action action : tempActionCalls){
                if(action.actionName.equals(actionName)){
                    actionCalls.add( action );
                    actionCallsToNotify.remove(action);
                    return;
                }
            }
        }
    }

    public int getNumOfNotifyActions(String actionName){
        Action[] tempActionCalls = new Action[actionCalls.size()];
        tempActionCalls = actionCallsToNotify.toArray(tempActionCalls);

        int totActions = 0;
        for(Action action : tempActionCalls){
            if(action.actionName.equals(actionName))
                totActions++;
        }
        return totActions;
    }

    public int getNumOfNotifyActions(){
        return actionCallsToNotify.size();
    }

    public String getNextActionToNotify(){
        Action[] tempActionCalls = new Action[actionCalls.size()];
        tempActionCalls = actionCallsToNotify.toArray(tempActionCalls);

        if(tempActionCalls.length > 0){
            for(Action action : tempActionCalls){
                return action.actionName;
            }
        }
        return "";
    }

    protected void tick(long deltaTime){

    }
    
    protected void beginPlay(){

    }
    
    protected void endPlay(){

    }

    public void disposeActor(){
        setActionsStopped(true);
        setTickStopped(true);

        getSprite().setVisible(false);
        getAreaMap().getViewArea().remove(getSprite());

        for(JComponent comp : getAttachedComps()){
            comp.setVisible(false);
            getAreaMap().getViewArea().remove(comp);
            comp = null;
        }
    }
}


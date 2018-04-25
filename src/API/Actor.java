package API;

import API.Annotations.*;
import API.Managers.EventManager;
import API.Managers.EventManagerTool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

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
    protected final boolean actionsEnabled;
    protected final boolean tickEnabled;
    private boolean actionsStopped;
    private boolean tickStopped;
    private List<Action> actionCalls;
    private Thread actorThread;
    
    private EventManagerTool eventManagerTool;
    

    // Constructor
    public Actor() {
        actionsEnabled = true;
        tickEnabled = true;
        actionsStopped = false;
        tickStopped = false;

        actorThread = new Thread(this);
        actorThread.start();

        asyncMethods = new ArrayList<>();
        actionCallableMethods = new ArrayList<>();
        actionResponseMethods = new ArrayList<>();
        actionCalls = Collections.synchronizedList(new LinkedList<Action>());
        
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
        for (Method method : asyncMethods) {
            try {
                method.invoke(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
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
            	
            	if(previousTime != null) {
            	
            		currentTime = Instant.now();
            		
            		tickDuration = Duration.between(previousTime, currentTime).toNanos();
                    tick(tickDuration);
                    
                    previousTime = currentTime;
                }
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
    final public void bindActorForEvents(EventManager eventManager) {
    	eventManagerTool.bindActorForEvents(eventManager);
    }

    @Override
    final public void unbindActor(EventManager eventManager) {
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
                    if( method.getParameterCount() == 1 && result != null){
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

    protected void tick(long deltaTime){

    }
    
    protected void beginPlay(){

    }
    
    protected void endPlay(){

    }
}


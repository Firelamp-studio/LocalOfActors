package api.utility;

import api.annotations.ActionCallable;
import api.Actor;

import java.lang.reflect.Method;

public class TimerAction implements Runnable {
    private Thread timerThread;
    private Actor actorToCall;
    private String actionName;
    private Method methodToCall;
    private long delay;
    private boolean loop;
    private boolean killRequested;
    private Object[] args;

    public TimerAction(boolean loop, long delay, Actor actorToCall, String actionName) {
        this.delay = delay;
        this.loop = loop;
        this.actorToCall = actorToCall;
        this.timerThread = null;
        this.actionName = actionName;

        killRequested = false;

        methodToCall = null;
        for(Method method : actorToCall.getActionCallableMethods()){

            ActionCallable actionCallable = method.getAnnotation(ActionCallable.class);

            if(actionName.equals(actionCallable.name())){
                methodToCall = method;
            }
        }
    }

    public TimerAction(long delay, Actor actorToCall, String actionName) {
        this(false, delay, actorToCall, actionName);
    }


    public void execute(Object... args) {
        this.args = args;
        if (actionName != null && !actionName.isEmpty()) {
            if(timerThread != null){
                if(timerThread.isAlive())
                    timerThread.interrupt();
                timerThread = null;
            }

            timerThread = new Thread(this);
            timerThread.start();
            killRequested = false;
        }
    }


    public void kill() {
        killRequested = true;
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    public boolean isAlive() {
        if(timerThread != null)
            return timerThread.isAlive();

        return false;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(delay);

                actorToCall.addActionCall( new Actor.Action(actionName, methodToCall, args,null) );
            } catch (InterruptedException e) {
                return;
            }
        } while (timerThread!= null && loop && timerThread.isAlive());
    }

    public boolean getKillRequested(){
        return killRequested;
    }
}

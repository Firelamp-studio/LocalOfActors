package API.Utility;

import API.Annotations.ActionCallable;
import API.Actor;

import java.lang.reflect.Method;

public class TimerAction implements Runnable {
    private Thread timerThread;
    private Actor actorToCall;
    private String actionName;
    private Method methodToCall;
    private long delay;
    private boolean loop;
    private Object[] args;

    public TimerAction(boolean loop, long delay, Actor actorToCall, String actionName) {
        this.delay = delay;
        this.loop = loop;
        this.actorToCall = actorToCall;
        this.timerThread = null;
        this.actionName = actionName;

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
        if (actionName != null && !actionName.isEmpty() && timerThread == null) {
            timerThread = new Thread(this);
            timerThread.start();
        }
    }


    public void kill() {
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
        } while (loop && timerThread.isAlive());
    }
}

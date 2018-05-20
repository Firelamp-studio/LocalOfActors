package api.utility;

import api.annotations.ActionCallable;
import api.Actor;

import java.lang.reflect.Method;

/**
 * Un TimerAction non &egrave; altro che un timer che, alla fine del ritardo impostatogli, esegue un'{@link ActionCallable} ad un {@link Actor}.
 *
 * <p>Esso viene seguito parallemente al chiamante senza bloccare il thread principale di chi lo ha eseguito.
 *
 * <p>Il timer pu&ograve; essere anche un loop che chiama ripetutamente l'azione passata.
 *
 * @author Simone Russo
 */
public class TimerAction implements Runnable {
    private Thread timerThread;
    private Actor actorToCall;
    private String actionName;
    private Method methodToCall;
    private long delay;
    private boolean loop;
    private boolean killRequested;
    private Object[] args;

    /**
     * Costruisce il timer.
     * @param loop Se deve o no essere un loop.
     * @param delay Il ritardo prima di eseguire l'azione.
     * @param actorToCall L'attore a cui chiamare l'azione.
     * @param actionName Il nome dell'azione da eseguire dopo il ritardo.
     */
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

    /**
     * Esegue il timer che, dopo il ritardo, attiver&agrave; l'azione.
     * @param args gli argomenti da passare all'azione.
     */
    public void execute(Object... args) {
        this.args = args;
        if (actionName != null && !actionName.isEmpty()) {
            if(timerThread != null && timerThread.isAlive())
                timerThread.interrupt();

            timerThread = new Thread(this);
            timerThread.start();
            killRequested = false;
        }
    }

    /**
     * Serve soprattutto in caso in cui il timer sia un loop. ferma il timer.
     */
    public void kill() {
        killRequested = true;
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    /**
     * Verifica se il timer &egrave; in stato di esecuzione.
     * @return
     */
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
        } while (loop && timerThread.isAlive() && !killRequested);
    }

    /**
     * Controlla se &egrave; stata effettuata la richiesta di kill al timer.
     * @return true se &egrave; stata effettuata la richiesta, false in caso contrario.
     */
    public boolean isKillRequested(){
        return killRequested;
    }
}

package api.managers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import api.annotations.BindableEvent;

public class EventManagerTool {
	private final List<Method> bindableMethods;
	private Set<EventManager> eventManagers;
	
	public EventManagerTool(EventManager toolOwner) {
		bindableMethods = new ArrayList<>();
		eventManagers = Collections.synchronizedSet(new HashSet<EventManager>());
		
		for (Method method : toolOwner.getClass().getMethods()) {
			if (method.isAnnotationPresent(BindableEvent.class)) {
	            bindableMethods.add(method);
	        }
        }
	}
	
	public List<Method> getBindableMethods() {
        return bindableMethods;
    }
	
	final public void bindActorForEvents(EventManager eventManager) {
        eventManagers.add(eventManager);
    }

    final public void unbindActor(EventManager eventManager) {
        eventManagers.remove(eventManager);
    }

    final public void unbindAll() {
        eventManagers.clear();
    }

    final public void dispatchEvent(String eventName, Object... args) {

        for (EventManager bindedEventManager : eventManagers) {

            for (Method method : bindedEventManager.getBindableMethods()) {

                BindableEvent bindableEvent = method.getAnnotation(BindableEvent.class);

                if (eventName.equals(bindableEvent.name()) && method.getParameterCount() == args.length) {
                    try {
                        method.invoke(bindedEventManager, args);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

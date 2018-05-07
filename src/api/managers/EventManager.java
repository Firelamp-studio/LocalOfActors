package api.managers;

import java.lang.reflect.Method;
import java.util.List;

public interface EventManager {
	void bindManagerForEvents(EventManager eventManager);
	void unbindBindedManager(EventManager eventManager);
	void unbindAll();
	void dispatchEvent(String eventName, Object... args);
	List<Method> getBindableMethods();
}

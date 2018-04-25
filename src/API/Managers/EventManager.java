package API.Managers;

import java.lang.reflect.Method;
import java.util.List;

public interface EventManager {
	void bindActorForEvents(EventManager eventManager);
	void unbindActor(EventManager eventManager);
	void unbindAll();
	void dispatchEvent(String eventName, Object... args);
	List<Method> getBindableMethods();
}

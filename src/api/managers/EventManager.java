package api.managers;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Descrive la capacit&agrave; di una classe di saper creare e gestire degli Eventi.
 *
 * <p>Un evento non &egrave; altro che un avviso che viene lanciato a tutti coloro che ascoltano colui che l'ha mandato.
 *
 * <p>&Egrave; importante inserire un {@link EventManagerTool} se si implementa questa interfaccia.
 *
 * @author Simone Russo
 * @see EventManagerTool
 */
public interface EventManager {
	/**
	 * Premette di registrare un EventManager alla classe.
	 * @param eventManager l'EventManager da aggiungere.
	 */
	void bindManagerForEvents(EventManager eventManager);

	/**
	 * Premette di rimuovere un ascoltatore dalla classe.
	 * @param eventManager l'EventManager da rimuovere.
	 */
	void unbindBindedManager(EventManager eventManager);

	/**
	 * Rimuove tutti gli ascoltatori.
	 */
	void unbindAll();

	/**
	 * Lancia un evento a tutti coloro che sono registrati a questa classe.
	 * @param eventName Evento da lanciare.
	 * @param args Parametri da passare assieme all'evento.
	 */
	void dispatchEvent(String eventName, Object... args);

	/**
	 * Permette di ottenere tutti i metodi {@link api.annotations.BindableEvent BindableEvent}) di questa classe.
	 *
	 * <p> L'annotazione {@link api.annotations.BindableEvent} permette di associare il nome di un Evento ad un Metodo
	 * in modo che, quando questo verr&agrave; chiamato, verr&agrave; eseguito il metodo stesso.
	 *
	 * @return I metodi {@code BindableEvent}).
	 */
	List<Method> getBindableMethods();
}

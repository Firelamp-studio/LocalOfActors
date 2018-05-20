package api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Un metodo con questa annotazione diventa un Evento.
 *
 * <p>Un evento non &egrave; altro che un avviso che viene lanciato a tutti coloro che ascoltano colui che l'ha mandato.
 *
 * <p>Leggere la documentazione dell'{@link api.managers.EventManager EventManager} per vederne bene il funzionamento.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BindableEvent {
    String name() default "default";
}

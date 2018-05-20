package api.annotations;

import api.Actor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Una ActionResponse &egrave; un'azione che viene esguita in risposta ad una {@link ActionCallable} se chiamata tramite
 * {@link api.Actor#actionCallResponse(Actor, String, Object...) actionCallResponse()} da un {@link api.Actor Actor}.
 *
 * <p>Leggere la documentazione dell'{@link api.Actor Actor} per vederne bene il funzionamento.
 *
 * @author Simone Russo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionResponse {
    String name() default "defalut";
}

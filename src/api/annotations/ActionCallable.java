package api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Una ActionCallable &egrave; un'azione chiamabile da un {@link api.Actor Actor} ad un altro Actor.
 *
 * <p>Leggere la documentazione dell'{@link api.Actor Actor} per vederne bene il funzionamento.
 *
 * @author Simone Russo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionCallable {
    String name() default "defalut";
}

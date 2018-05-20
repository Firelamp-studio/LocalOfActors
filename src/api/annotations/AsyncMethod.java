package api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Un metodo con questa Annotazione verr&agrave; eseguito automaticamente all'avvio del processo dell'{@link api.Actor Actor}.
 *
 * @author Simone Russo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AsyncMethod {
}
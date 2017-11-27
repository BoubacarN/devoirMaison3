package fr.ekinci.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An aspect annotation for executing retryable methods
 *
 * Limitations :
 *	* Does not handle primitive method parameters
 *	* Does handle method argument's class, not method argument's superclasses
 *
 * @author Gokan EKINCI
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TryAgain {
	Class<? extends Throwable>[] exceptions();
}

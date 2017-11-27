package fr.ekinci.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An aspect class for executing retryable methods
 *
 * @author Gokan EKINCI
 */
@Component
@Aspect
@Log
public class TryAgainAspect {

	@Pointcut("@annotation(fr.ekinci.aop.TryAgain)")
	public void tryAgainAnnotation() {}

	@Around("tryAgainAnnotation() && execution(* *(..))")
	public Object tryAgain(ProceedingJoinPoint joinPoint) throws Throwable {

		Object returnValue;
		try {
			returnValue = joinPoint.proceed();
		} catch (Throwable throwable) {
			final Signature signature = joinPoint.getSignature();
			final Object thisObject = joinPoint.getThis();
			final Object[] args = joinPoint.getArgs();
			log.info(String.format("[TryAgainAspect] : %s", signature.toLongString()));

			final Class<?>[] argTypes = Arrays.stream(args)
				.map(Object::getClass)
				.collect(Collectors.toList())
				.toArray(new Class<?>[args.length]);

			final Method method = signature.getDeclaringType().getMethod(signature.getName(), argTypes);
			Class<? extends Throwable>[] exceptionClasses = method.getDeclaredAnnotation(TryAgain.class).exceptions();

			// If no exception in @TryAgain#exceptions, then continue to invoke
			if (exceptionClasses.length == 0 || isInstanceOf(throwable, exceptionClasses)) {
				returnValue = method.invoke(thisObject, args);
			} else {
				throw throwable;
			}
		}

		return returnValue;
	}

	private boolean isInstanceOf(Throwable throwable, Class<? extends Throwable>[] exceptionClasses){
		return Arrays.stream(exceptionClasses).anyMatch(
			c -> c.isInstance(throwable)
		);
	}
}

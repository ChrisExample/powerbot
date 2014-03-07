package org.powerbot.script.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.EventListener;
import java.util.Queue;

/**
 * The base interface of a script.
 */
public interface Script extends EventListener {

	/**
	 * The representative states of a {@link org.powerbot.script.lang.Script}
	 */
	public enum State {
		START, SUSPEND, RESUME, STOP
	}

	/**
	 * Returns the execution queue.
	 *
	 * @param state the {@link org.powerbot.script.lang.Script.State} to query
	 * @return a {@link Queue} of {@link java.lang.Runnable}s in this {@link org.powerbot.script.lang.Script}s execution queue
	 */
	public Queue<Runnable> getExecQueue(State state);

	/**
	 * A controller for a {@link org.powerbot.script.lang.Script} which invokes and determines state changes.
	 */
	public interface Controller extends Suspendable, Stoppable {

		/**
		 * Adds a {@link java.lang.Runnable} to the executor.
		 *
		 * @param e a runnable to be executed
		 * @param <E> a type that extends {@link java.lang.Runnable}
		 * @return {@code true} if the runnable was added, otherwise {@code false}
		 */
		public <E extends Runnable> boolean offer(E e);
	}

	/**
	 * A {@link Script} descriptor.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE})
	public @interface Manifest {
		/**
		 * The human-friendly name.
		 *
		 * @return the name
		 */
		String name();

		/**
		 * The description, which should be 140 characters or less.
		 *
		 * @return the description
		 */
		String description();

		/**
		 * A series of key=value pairs separated by semicolons (;) or newlines.
		 *
		 * @return the properties
		 */
		String properties() default "";
	}
}
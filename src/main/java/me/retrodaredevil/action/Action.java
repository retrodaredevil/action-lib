package me.retrodaredevil.action;

/**
 * Represents something that can be updated and ended after updating. It's {@link #isDone()} method
 * tells you if it wishes to end (not required). It's {@link #isActive()} tells you if it is actively updating.
 * <p>
 * An Action is actively updating if {@link #update()} has been called after the last call to {@link #end()} or
 * {@link #end()} hasn't been called at all and {@link #update()} has been called at least once.
 */
public interface Action {
	/**
	 * Updates the action
	 * <p>
	 * Assuming {@link #end()} hasn't been called, after this is called {@link #isActive()} will be true
	 */
	void update();

	/**
	 * Ends the action
	 * <p>
	 * Assuming {@link #update()} hasn't been called, after this is called {@link #isActive()} will be false
	 * <p>
	 * If {@link #isActive()} == false, calling this will result in an exception or an undefined result
	 * @throws IllegalStateException If {@link #isActive()} == false. This is optional to be thrown.
	 */
	void end();

	/**
	 * Called after {@link #update()} and may be called at random times afterwards by anything that
	 * desires to do so.
	 * <p>
	 * If {@link #isActive()} == false, this has an undefined result and should not be called. Because of this,
	 * you should never check this until after you've called {@link #update()}
	 * @return true if this action should be ended, false otherwise
	 */
	boolean isDone();

	/** @return true if this is currently active, false otherwise.*/
	boolean isActive();
}

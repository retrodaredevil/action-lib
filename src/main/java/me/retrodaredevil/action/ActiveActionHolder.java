package me.retrodaredevil.action;

import java.util.Collection;

/**
 * Represents some sort of Action that might handle multiple actions that are active.
 */
public interface ActiveActionHolder extends Action {
	/**
	 * NOTE: If you store a reference to the returned value, it may or may not change. If you need to store it,
	 * be sure to copy all the elements.
	 * <p>
	 * NOTE: Not every action's {@link Action#isActive()} will always be true. If one is false, it is expected that
	 * it will be true after calling {@link #update()} on this
	 * <p>
	 * Also NOTE: Calling {@link #update()} on this may change the returned elements so you should update this before calling
	 * this method if you want the most accurate results
	 * <p>
	 * NOTE: The returned collection should not be mutated. Doing so may result in an exception or undefined behaviour
	 * <p>
	 * NOTE: This elements in the returned collection should not be updated, or ended without properly removing them
	 * from being handled by this.
	 *
	 * @return A Collection of Actions that are all active or will be active and being handled by this
	 */
	Collection<? extends Action> getActiveActions();
}

package me.retrodaredevil.action;

import java.util.Collection;

/**
 * Represents a {@link ActiveActionHolder} where only one action is active at a time
 */
public interface SingleActiveActionHolder extends ActiveActionHolder {
	/**
	 * Note it is not guaranteed that {@link Action#isActive()} == true however, if you call {@link #update()}, then it
	 * should be true
     * @return The active action or null if there is none.
	 */
	Action getActiveAction();

	/**
	 * @return A Collection with a size of 1 or 0. If the size is 1, the element in the collection is
	 * 		   the same as {@link #getActiveAction()}
	 */
	@Override
	Collection<? extends Action> getActiveActions();
}

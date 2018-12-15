package me.retrodaredevil.action;

import java.util.Collection;

/**
 * Represents a {@link ActiveActionHolder} where only one action is active at a time
 */
public interface SingleActiveActionHolder extends ActiveActionHolder {
	/**
	 *
	 * @return The active action or null. If not null, {@link Action#isActive()} will be true
	 */
	Action getActiveAction();

	/**
	 * @return A Collection with a size of 1 or 0. If the size is 1, the element in the collection is
	 * 		   the same as {@link #getActiveAction()}
	 */
	@Override
	Collection<? extends Action> getActiveActions();
}

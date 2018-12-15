package me.retrodaredevil.action;

/**
 * Represents an Action that can add other actions to it. The behaviour of this interface is not
 * strictly defined.
 */
public interface ActionHolder extends Action {
	/**
	 * @param action The action to add
	 * @return true if the action was added successfully
	 * @throws NullPointerException if action is null
	 * @throws IllegalArgumentException if action == this <br/>
	 *                                  or if {@link Action#isActive()} == true and this doesn't support adding active actions
	 */
	boolean add(Action action);

	/**
	 * Clears all the actions and ends them if they were active
	 */
    void clear();
}

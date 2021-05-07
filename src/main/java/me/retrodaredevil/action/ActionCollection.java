package me.retrodaredevil.action;

/**
 * Represents an Action that can add other actions to it. The behaviour of this interface is not
 * strictly defined. It is also expected that the actions added via {@link #add(Action)} will eventually be represented
 * in the elements returned from {@link #getActiveActions()} but depending on how the implementation works, is not required.
 */
public interface ActionCollection extends ActiveActionHolder {
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
	 * <p>
     * Note: This is thread safe, but behavior is undefined for {@link #add(Action)} if another thread tries adding an {@link Action} present
	 * in {@link #getActiveActions()} before this call is completed
	 */
    void clear();
}

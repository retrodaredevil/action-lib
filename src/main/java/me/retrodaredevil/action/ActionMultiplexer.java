package me.retrodaredevil.action;

/**
 * Represents an Action that handles other active actions. This is the opposite of {@link ActionQueue}
 * because when this is used, all actions added from {@link #add(Action)} are active at once.
 */
public interface ActionMultiplexer extends ActionCollection {
	/**
	 * Removes the action.
	 * <p>
     * NOTE: Unlike {@link #add(Action)}, this method is not thread safe because it may call {@link Action#end()}.
	 * @param action The action to remove
	 * @return true if the action was found and was removed, false otherwise
	 */
	boolean remove(Action action);

	/**
	 * Adds another action in parallel
	 * <p>
	 * NOTE: action's {@link Action#isActive()} will not be true until {@link #update()} is called on this.
     * <p>
     * NOTE: This is thread safe and can be called from different threads.
	 * @param action the action to add in parallel
	 * @return true if the action was added successfully, false otherwise. Normally will always be true unless action is already here
	 */
	@Override
	boolean add(Action action);
}

package me.retrodaredevil.action;

/**
 * Represents an {@link ActionCollection} that has an active {@link Action} with multiple actions in a
 * queue waiting to be active.
 * <p>
 * The behaviour of this class is not strictly defined except that there is an active {@link Action}
 * and there may be multiple actions waiting to be the active {@link Action}
 */
public interface ActionQueue extends SingleActiveActionHolder, ActionCollection {
	/**
	 * Adds the action to the beginning of the queue to
	 * @param action The action to add
	 * @return true if the action was successfully added to
	 */
	boolean addNext(Action action);

	/**
	 * Ends the current action if there is one and removes it
	 * @return true if there was a current action to end, false otherwise
	 */
	boolean removeCurrentAction();
	/**
     * Ends the current action and moves it to the end of the queue if there is one
	 * <p>
	 * NOTE: This requires the current action to be recyclable
	 * @param doNothingIfEmpty true if you want to do nothing if the queue is empty, false otherwise.
	 *                         If set to true, the current action will not be ended (nothing will happen).
	 */
	boolean moveCurrentToEnd(boolean doNothingIfEmpty);

	/**
	 * Moves the current action to the first spot in the queue and ends it if there is a current action.
	 * <p>
	 * This allows you to call {@link #addNext(Action)} which if called after this method,
	 * will allow you to stop the current running action and put another one in.
	 * <p>
	 * NOTE: This requires the current action to be recyclable
	 * @return true if there was a current action to put at the front of the queue, false otherwise
	 */
	boolean moveCurrentToNext();

	boolean removeQueued(Action action);
}

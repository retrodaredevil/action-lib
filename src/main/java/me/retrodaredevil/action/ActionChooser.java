package me.retrodaredevil.action;

/**
 * An ActionChooser allows you to set the action that it will update. This is useful for switching between actions
 * based on events. However, this is not recommended to use when storing actions or storing a chain of actions because
 * it requires something else to handle setting the action that is currently running.
 * <p>
 * This implementation may choose to "clear" the active action on its own and may or may not return true in {@link #isDone()}
 * depending on certain conditions. This behaviour is not strictly defined and can be used to make the behaviour of
 * an ActionChooser ideal for a certain situation.
 */
public interface ActionChooser extends SingleActiveActionHolder {
	/**
	 * Sets the action that will be returned by {@link #getActiveAction()} after the next call to {@link #update()}
	 * <p>
	 * NOTE: Passing null will only have an effect if {@link #getNextAction()} is not null.
	 * @param action The action to set. If null, it will not clear the active action.
	 */
	void setNextAction(Action action);

	/**
	 * @return The action set by {@link #setNextAction(Action)} or null if {@link #update()} has already been called
	 *         since the last call to {@link #setNextAction(Action)}
	 */
	Action getNextAction();

	/**
	 * Calling this sets the active action ({@link #getActiveAction()}) to be ended. This is similar to {@link #setNextAction(Action)}
	 * except that a new action will not take the active action's place.
	 * <p>
	 * NOTE: This will not clear the next action. To make sure that there is no active action after {@link #update()} is called,
	 * call this method and call {@link #setNextAction(Action)} with action=null
	 * @param b true if the active action should be cleared, false otherwise
	 */
	void setToClearAction(boolean b);

	/**
	 * Same as {@link #setToClearAction(boolean)} with b=true
	 */
	void setToClearAction();

	boolean isSetToClearAction();

}

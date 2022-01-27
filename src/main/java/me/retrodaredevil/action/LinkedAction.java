package me.retrodaredevil.action;

public interface LinkedAction extends Action {
	/**
	 * Note: The value returned by this method should only be considered valid after seeing a true value from {@link #isDone()}.
	 * You should check {@link #isDone()} then call this method to see if there is a next action.
	 * <p>
	 * Do not call this method then call {@link #isDone()}.
	 * <p>
	 * Note: A non-null value does NOT imply {@link #isDone()}{@code == true}
	 *
	 * @return The action that should be ran after this one, or null if there is no next action to run
	 */
	Action getNextAction();
}

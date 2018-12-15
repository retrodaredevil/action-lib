package me.retrodaredevil.action;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Objects;

/**
 * A action that has multiple {@link Action}s waiting to be the current action that is running.
 * Depending on how an instance of this class was initialized, its {@link #isDone()} may or may not
 * return true when all the actions are done.
 */
public class DequeActionQueue extends SimpleAction implements ActionQueue {
	private final boolean canBeDone;
	private final boolean clearActiveOnEnd;
	private final boolean clearQueuedOnEnd;
	private final Deque<Action> actionQueue;
	/** The current action where if this isn't null, {@link Action#isActive()} should always be true*/
	private Action currentAction = null;

	/**
	 *
	 * @param actionQueue A Deque of all the initial actions to be queued up. Once passed to the constructor, do not modify.
	 * @param canBeDone will {@link #isDone()} return true when there are no actions left
	 * @param clearActiveOnEnd If this is forcefully ended, should the active action be removed
	 * @param clearQueuedOnEnd If this is forcefully ended, should everything that's queued be cleared
	 * @param canRecycle Can this be recycled
	 */
	public DequeActionQueue(Deque<Action> actionQueue, boolean canBeDone, boolean clearActiveOnEnd, boolean clearQueuedOnEnd, boolean canRecycle){
		super(canRecycle);
		this.actionQueue = actionQueue;
		this.canBeDone = canBeDone;
        this.clearActiveOnEnd = clearActiveOnEnd;
        this.clearQueuedOnEnd = clearQueuedOnEnd;
	}

	@Override
	public Action getActiveAction() {
        return currentAction;
	}

	@Override
	public Collection<? extends Action> getActiveActions() {
		if(currentAction == null){
			return Collections.emptySet();
		}
        return Collections.singleton(currentAction);
	}

	@Override
	public boolean add(Action action){
		Objects.requireNonNull(action);
		if(action == this){
			throw new IllegalArgumentException();
		}
		actionQueue.addLast(action);
		return true;
	}

	@Override
	public boolean removeQueued(Action action) {
        return actionQueue.remove(action);
	}

	@Override
	public void clear() {
		actionQueue.clear();
	}

	/**
	 * Adds an action next in the queue. If there is currently an action running, it will not be stopped.
	 * @param action The action to be next the queue
	 * @return true if the action was successfully added
	 */
	@Override
	public boolean addNext(Action action){
		Objects.requireNonNull(action);
		if(action.isActive()){
			throw new IllegalArgumentException("action cannot be active when you add it!");
		}
		actionQueue.addFirst(action);
		return true;
	}

	/**
	 * Ends the current action running if there is one
	 * @return true if the current action was ended, false if there is no current action
	 */
	public boolean removeCurrentAction(){
		if(currentAction != null){
			currentAction.end();
			currentAction = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean moveCurrentToEnd(boolean doNothingIfEmpty){
		if((doNothingIfEmpty && actionQueue.isEmpty()) || currentAction == null)
			return false;

		currentAction.end();
		actionQueue.addLast(currentAction);
		currentAction = null;
		return true;
	}
	@Override
	public boolean moveCurrentToNext(){
		if(currentAction == null)
			return false;

		currentAction.end();
		actionQueue.addFirst(currentAction);
		currentAction = null;
		return true;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		if(currentAction == null){
			currentAction = actionQueue.poll();
		}
		if(currentAction != null){
			currentAction.update();
			if(currentAction.isDone()){
                removeCurrentAction();
			} else if(!currentAction.isActive()){
				throw new IllegalStateException("The current action should be active because it was just updated! currentAction: " + currentAction);
			}
		}
	}

	@Override
	protected void onEnd(boolean peacefullyEnded) {
		super.onEnd(peacefullyEnded);
		if(currentAction != null){
			currentAction.end();
			if(clearActiveOnEnd){
				currentAction = null;
			}
		}
		if(clearQueuedOnEnd){
			currentAction = null;
			actionQueue.clear();
		}
	}

	@Override
	protected void onIsDoneRequest() {
		if (canBeDone) {
			setDone(currentAction == null && actionQueue.isEmpty());
		}
	}
}

package me.retrodaredevil.action;

import java.util.*;

/**
 * NOTE that the order that the actions are updated in depend on the implementation of the {@link Set} passed to the constructor
 */
public class SetActionMultiplexer extends BaseAction implements ActionMultiplexer {

	private final Set<Action> actionSet;
	/** Will always have the same elements as {@link #actionSet}, it's just unmodifiable*/
	private final Set<Action> unmodifiableActionSet;

	private final boolean canBeDone;
	private final boolean clearOnEnd;

	/**
	 * NOTE: It is recommended to use {@link Actions.ActionMultiplexerBuilder} instead of directly creating this
	 * @param canRecycle true if {@link #update()} can be called after {@link #end()} is called
	 * @param actionSet The Set that this instance will use to store actions. Do not modify this set after it has been passed to this constructor.
	 * @param canBeDone true if {@link #isDone()} should return true when there are no actions active
	 * @param clearOnEnd true if all actions stored in this instance should be cleared and ended if they are running when {@link #end()} is called.
	 */
	public SetActionMultiplexer(boolean canRecycle, Set<Action> actionSet, boolean canBeDone, boolean clearOnEnd){
		super(canRecycle); // set canRecycle to false because this will never be done
        this.actionSet = actionSet;
		this.unmodifiableActionSet = Collections.unmodifiableSet(this.actionSet);
		this.canBeDone = canBeDone;
		this.clearOnEnd = clearOnEnd;
	}

	@Override
	public Collection<? extends Action> getActiveActions() {
        return unmodifiableActionSet;
	}

	@Override
	public boolean add(Action action){
		Objects.requireNonNull(action);
		if(action == this){
			throw new IllegalArgumentException();
		}
		synchronized (this) {
			if (action.isActive() && !actionSet.contains(action)) {
				throw new IllegalArgumentException("You cannot add an action that is already active! Something else is handling it!");
			}
			return actionSet.add(action);
		}
	}

	@Override
	public boolean remove(Action action) {
		final boolean removed;
		synchronized (this) {
			removed = actionSet.remove(action);
		}
        if(removed){
        	if(action.isActive()){ // may not be active because this may not have been updated yet
        		action.end();
			}
        	return true;
		}
		return false;
	}

	@Override
	public void clear() {
		final List<Action> actions;
		synchronized (this) {
			actions = new ArrayList<>(actionSet);
			actionSet.clear();
		}
		for(Action action : actions){
			if(action.isActive()) {
				action.end();
			}
		}
	}

	@Override
	public boolean isDone() {
		if (!canBeDone) {
			return false;
		}
		synchronized (this) {
			return actionSet.isEmpty();
		}
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		final List<Action> actions;
		synchronized (this) {
			actions = new ArrayList<>(actionSet);
		}
		for (Action action : actions) {
			action.update();
			if (action.isDone()) {
				action.end();
				synchronized (this) {
					actionSet.remove(action);
				}
			} else if (!action.isActive()) {
				throw new IllegalStateException(action + " is not active when it should be!");
			}
		}
	}

	@Override
	protected void onEnd() {
		super.onEnd();
		if(clearOnEnd) {
			clear();
		} else {
			for(Action action : actionSet){
				if(action.isActive()){
					action.end();
				}
			}
		}
	}
}

package me.retrodaredevil.action;

public abstract class StartAction extends SimpleAction {
	private final Action action;
	/** Used in place of {@link Action#isActive()} so we aren't relying on a method that we don't know
	 * the implementation of*/
	private boolean started = false;

	/**
	 * NOTE: It is recommended to use {@link Actions#createWaitToStartAction(Action, Actions.CanStart)}
	 * or {@link Actions#createWaitToStartActionRecyclable(Action, Actions.CanStart)} instead of initializing this directly.
	 *
	 * @param canRecycle
	 * @param action
	 */
	public StartAction(boolean canRecycle, Action action){
		super(canRecycle);
		this.action = action;
	}

	protected abstract boolean shouldStart();


	@Override
	protected void onUpdate() {
		super.onUpdate();
		if(!started){
			if(action.isActive()){
				throw new IllegalStateException("The action is active when we haven't started it yet! this: " + this + " action: " + action);
			}
			started = shouldStart();
		}
		if(started){
			action.update();
			if(!action.isActive()){
				throw new IllegalStateException("Our action's isActive() implementation isn't correct! this: " + this + " action: " + action);
			}
		}
	}

	@Override
	protected void onIsDoneRequest() {
		setDone(action.isDone());
	}

	@Override
	protected void onEnd(boolean peacefullyEnded) {
		super.onEnd(peacefullyEnded);
		if(started) {
			action.end();
			started = false;
		}
	}
}

package me.retrodaredevil.action;

public class SimpleAction implements Action {

	private final boolean canRecycle;

	private boolean running = false;
	private boolean done = false;
	/** Set to true when {@link #end()} is called. Should never be set back to false once true*/
	private boolean oneWayEndedFlag = false;

	protected SimpleAction(){
		this(true);
	}
	protected SimpleAction(boolean canRecycle){
		this.canRecycle = canRecycle;
	}


	protected void onStart(){}
	protected void onUpdate(){}

	/**
	 * Called when being ended.
	 * <p>
	 * NOTE: {@link #isActive()} will have an undefined result. Do not call it.
	 * @param peacefullyEnded true if this wanted to end (when {@link #isDone()} == true), false otherwise
	 */
	protected void onEnd(boolean peacefullyEnded){}
	/** Called when {@link #isDone()} is being called. If you want to, you may call {@link #setDone(boolean)}
	 * to alter the return value of that function.*/
	protected void onIsDoneRequest(){}

	protected final void setDone(boolean done){
		this.done = done;
	}
	/** NOTE: Will not call {@link #onIsDoneRequest()}
	 * @return the current value set from {@link #setDone(boolean)}.*/
	protected final boolean getCurrentIsDone(){
		return done;
	}

	@Override
	public final void update() {
		if(!running){
			if(oneWayEndedFlag && !canRecycle){
				throw new IllegalStateException("This action cannot be recycled!");
			}
			done = false;
			running = true;
			onStart();
		}
		onUpdate();
	}

	@Override
	public final void end() {
		if(!running){
			throw new IllegalStateException("This action must be active to be able to end it!");
		}
		onEnd(getCurrentIsDone());
		running = false;
		oneWayEndedFlag = true;
	}

	@Override
	public final boolean isDone() {
		onIsDoneRequest();
        return getCurrentIsDone();
	}

	@Override
	public final boolean isActive() {
        return running;
	}
}

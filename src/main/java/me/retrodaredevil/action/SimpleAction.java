package me.retrodaredevil.action;

/**
 * A class that handles a simple implementation for an {@link Action}
 * <p>
 * NOTE: That for {@link #onStart()}, {@link #onUpdate()}, and {@link #onEnd(boolean)}, you should call super even though
 * they probably won't do anything. This is to maintain consistency so if you change your base class, those methods will be called.
 * <p>
 * NOTE: When implementing, {@link #getCurrentIsDone()} is reset back to false whenever the action is started (before {@link #onStart()} is called)
 */
public class SimpleAction extends BaseAction {

	/** Set to false when starting (before {@link #onStart()} is called*/
	private volatile boolean done = false;

	/**
	 *
	 * @param canRecycle Can {@link #update()} be called after being ended once via {@link #end()}
	 */
	protected SimpleAction(boolean canRecycle){
		super(canRecycle);
	}

	/**
	 * Called when being ended.
	 * <p>
	 * NOTE: {@link #isActive()} will have an undefined result. Do not call it.
	 * @param peacefullyEnded true if this wanted to end (when {@link #isDone()} == true), false otherwise
	 */
	protected void onEnd(boolean peacefullyEnded){}
	/** Called when {@link #isDone()} is being called. If you want to, you may call {@link #setDone(boolean)}
	 * to alter the return value of that function.
	 * <p>
	 * Note: If you have to override this method, consider using {@link BaseAction} instead of {@link SimpleAction}
	 * <p>
	 * Note: Unlike some of the other on* methods, this may be called in a separate thread. You must synchronize yourself if needed. Note {@link #setDone(boolean)} is always thread safe*/
	protected void onIsDoneRequest(){}

	protected final void setDone(boolean done){
		this.done = done;
	}
	/** NOTE: Will not call {@link #onIsDoneRequest()}
	 * <p>
	 * NOTE: The value of this resets every time this action is started
	 * @return the current value set from {@link #setDone(boolean)}.*/
	protected final boolean getCurrentIsDone(){
		return done;
	}


	@Override
	public final boolean isDone() {
		onIsDoneRequest();
        return getCurrentIsDone();
	}

	@Override
	protected void onStart() {
		super.onStart();
		done = false;
	}

	@Override
	protected final void onEnd() {
		super.onEnd();
		onEnd(getCurrentIsDone());

		// Ideally, we would just set this in onStart() (like we do), but if someone forgets to call super.onStart(), that would be problematic.
		//   So, we'll just set it to false twice. No harm in that. Plus, after end() is called queries to isDone() are undefined because isActive() == false
		done = false;
	}
}

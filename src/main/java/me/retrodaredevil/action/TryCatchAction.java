package me.retrodaredevil.action;


import java.util.Objects;

/**
 * This class is used to wrap an {@link Action} that may throw an exception. You can use this to stop your program from
 * crashing, or you can use this to stop the {@link Action} when an exception is thrown.
 * @param <T> The type of the exception that will be caught
 */
public abstract class TryCatchAction<T extends Throwable> extends SimpleAction {
	
	protected final Action action;
	private final Class<T> throwableClass;
	
	public TryCatchAction(boolean recyclable, Action action, Class<T> throwableClass) {
		super(recyclable);
		this.action = Objects.requireNonNull(action);
		this.throwableClass = Objects.requireNonNull(throwableClass);
	}
	
	/**
	 * Called if {@code action}'s {@link #update()} threw an exception of type {@link T}
	 * @param throwable The throwable of type {@link T} that was thrown
	 */
	protected abstract void onCatchUpdate(T throwable);
	/**
	 * Called if {@code action}'s {@link #end()} threw an exception of type {@link T}
	 * @param throwable The throwable of type {@link T} that was thrown
	 */
	protected abstract void onCatchEnd(T throwable);
	
	/**
	 * Called if {@code action}'s {@link #isDone()} method threw an exception of type {@link T}
	 * @param throwable The throwable of type {@link T} that was thrown
	 * @return true for this action to end, false otherwise
	 */
	protected abstract boolean onCatchIsDone(T throwable);
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		try{
			action.update();
		} catch(Throwable t){
			if(throwableClass.isAssignableFrom(t.getClass())){ // t instanceof throwableClass
				onCatchUpdate((T) t);
			} else {
				throw t;
			}
		}
	}
	
	
	@Override
	protected void onEnd(boolean peacefullyEnded) {
		try{
			action.end();
		} catch(Throwable t){
			if(throwableClass.isAssignableFrom(t.getClass())){ // t instanceof throwableClass
				onCatchEnd((T) t);
			} else {
				throw t;
			}
		}
	}
	
	@Override
	protected void onIsDoneRequest() {
		super.onIsDoneRequest();
		
		boolean done;
		try{
			done = action.isDone();
		} catch(Throwable t){
			if(throwableClass.isAssignableFrom(t.getClass())){ // t instanceof throwableClass
				done = onCatchIsDone((T) t);
			} else {
				throw t;
			}
		}
		setDone(done);
	}
}

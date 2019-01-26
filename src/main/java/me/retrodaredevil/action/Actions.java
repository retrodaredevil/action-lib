package me.retrodaredevil.action;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;

public final class Actions {
	private Actions(){ throw new UnsupportedOperationException(); }

	// region run forever
	/** @return A RunForeverAction where {@link Action#update()} cannot be called after {@link Action#end()} is called. (Non-recyclable)*/
	public static Action createRunForever(Runnable runnable){
		return new RunForeverAction(false, runnable);
	}
	/** @return A RunForeverAction where after called {@link Action#end()} you can call {@link Action#update()} and it will continue (recyclable)*/
	public static Action createRunForeverRecyclable(Runnable runnable){
		return new RunForeverAction(true, runnable);
	}
	// endregion

	// region run once
	/**
	 * Once updated, {@link Action#isDone()} will return true
	 * <p>
	 * If it is started twice, an {@link IllegalStateException} will be thrown. Because of this, the runnable will only
	 * ever be run once
	 * @return A RunOnceAction that can only be started once.
	 */
	public static Action createRunOnce(Runnable runnable){
		return new RunOnceAction(runnable, RunOnceAction.RunType.NON_RECYCLABLE);
	}
	/**
	 * Once updated, {@link Action#isDone()} will return true however, even though it is done, you are still allowed to
	 * end it then update it again. If you start it again by first ending it then updating it, the runnable will be run again.
	 * @return A RunOnceAction that will run the {@link Runnable} every time the action starts
	 */
	public static Action createRunOnceRecyclable(Runnable runnable){
		return new RunOnceAction(runnable, RunOnceAction.RunType.RUN_EVERY_START);
	}
	/**
	 * Once updated, {@link Action#isDone()} will return true
	 * <p>
	 * When first updated, the runnable will be run. After it is run once, it will never run again.
	 * @return A RunOnceAction that will only run the {@link Runnable} once even if it is started and ended multiple times.
	 */
	public static Action createRunOnceRecyclableRunOnce(Runnable runnable){
		return new RunOnceAction(runnable, RunOnceAction.RunType.RUN_ONCE);
	}
	// endregion

	// region wait to start
	private static Action createWaitToStartAction(boolean recyclable, Action action, CanStart canStart){
		return new StartAction(recyclable, action) {
			@Override
			protected boolean shouldStart() {
                return canStart.canStart();
			}
		};
	}
	public static Action createWaitToStartAction(Action action, CanStart canStart){
		return createWaitToStartAction(false, action, canStart);
	}
	public static Action createWaitToStartActionRecyclable(Action action, CanStart canStart){
		return createWaitToStartAction(true, action, canStart);
	}
	// endregion

	public static ActionChooser createActionChooser(WhenDone whenDone){
		return new DefaultActionChooser(false, whenDone);
	}
	public static ActionChooser createActionChooserRecyclable(WhenDone whenDone){
		return new DefaultActionChooser(true, whenDone);
	}
	
	// region Linked Actions
	
	/**
	 * @param action The action to run while the returned action is running
	 * @param nextAction The next action
	 * @return A LinkedAction that runs the passed action that will have nextAction as its next action.
	 */
	public static LinkedAction createLinkedAction(Action action, Action nextAction){
		return new LinkedActionWrapper(action, nextAction);
	}
	
	/**
	 * Creates an {@link Action} that will update the passed action. When the action is done, if it is a {@link LinkedAction},
	 * it will be replaced by the {@link Action} from {@link LinkedAction#getNextAction()}
	 * @param action The action or linked action to update
	 * @return An {@link Action} that will update the passed action. Cannot be recycled
	 */
	public static Action createLinkedActionRunner(Action action, WhenDone whenDone, boolean immediatelyDoNextWhenDone){
		return new LinkedActionRunner(whenDone, immediatelyDoNextWhenDone, action);
	}
	// endregion


	static abstract class Builder<T extends Builder> {
		protected boolean canBeDone = true, canRecycle = false;

		protected abstract T getThis();

		/**
		 * By default true. If true, {@link Action#isDone()} will be able to return true
		 * @param b value to set canBeDone to
		 * @return this
		 */
		public T canBeDone(boolean b){
			this.canBeDone = b;
			return getThis();
		}

		/**
		 * By default false. If true, {@link Action#update()} can be called after {@link Action#end()} is called.
		 * If false, an exception will be thrown when trying to update the action after it has been ended
		 * @param b value to set canRecycle to
		 * @return this
		 */
		public T canRecycle(boolean b){
			this.canRecycle = b;
			return getThis();
		}
	}

	public static class ActionMultiplexerBuilder extends Builder<ActionMultiplexerBuilder>{
		private final Action[] initialActions;
		private boolean clearAllOnEnd = false;

		public ActionMultiplexerBuilder(Action... initialActions){
			this.initialActions = initialActions;
		}
		@Override
		protected ActionMultiplexerBuilder getThis() {
            return this;
		}

		public ActionMultiplexerBuilder clearAllOnEnd(boolean b){
            clearAllOnEnd = b;
			return getThis();
		}
		public ActionMultiplexer build(){
			return new SetActionMultiplexer(canRecycle, new HashSet<>(Arrays.asList(initialActions)), canBeDone, clearAllOnEnd);
		}
	}

	public static class ActionQueueBuilder extends Builder<ActionQueueBuilder>{
		private final Action[] initialActions;
		private boolean clearActiveOnEnd = true, clearQueuedOnEnd = false, immediatelyDoNextWhenDone = false;

		public ActionQueueBuilder(Action... initialActions){
			this.initialActions = initialActions;
		}
		@Override
		protected ActionQueueBuilder getThis() {
			return this;
		}

		public ActionQueueBuilder clearActiveOnEnd(boolean b){
			this.clearActiveOnEnd = b;
			return getThis();
		}
		public ActionQueueBuilder clearQueuedOnEnd(boolean b){
			this.clearQueuedOnEnd = b;
			return getThis();
		}
		public ActionQueueBuilder immediatelyDoNextWhenDone(boolean b){
			this.immediatelyDoNextWhenDone = b;
			return getThis();
		}

		public ActionQueueBuilder clearAllOnEnd(boolean b){
            this.clearQueuedOnEnd = b;
            this.clearActiveOnEnd = b;
			return getThis();
		}
		public ActionQueue build(){
            return new DequeActionQueue(
            		canRecycle, new ArrayDeque<>(Arrays.asList(initialActions)), canBeDone,
					clearActiveOnEnd, clearQueuedOnEnd, immediatelyDoNextWhenDone
			);
		}
	}
	public interface CanStart {
		boolean canStart();
	}
}

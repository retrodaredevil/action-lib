package me.retrodaredevil.action;

import java.util.Objects;

class RunOnceAction extends SimpleAction {

	private final Runnable runnable;
	private final RunType type;
	private boolean ranOnce = false;


	RunOnceAction(Runnable runnable, RunType type){
		super(type != RunType.NON_RECYCLABLE);
		this.runnable = Objects.requireNonNull(runnable);
		this.type = Objects.requireNonNull(type);
	}

	@Override
	protected void onStart() {
		super.onStart();
		setDone(true);
		if(type == RunType.RUN_ONCE && ranOnce){
			return;
		}
		runnable.run();
		ranOnce = true;
	}
	protected enum RunType {
		/** Despite the name, this is just used so if this isn't removed, it will still call it multiple times but still wants to "be done" after it starts*/
		RUN_EVERY_START,
		/** If started multiple times, will throw an error*/
		NON_RECYCLABLE,
		/** This is only different from {@link #RUN_EVERY_START} when this isn't removed after it is called. This will only ever run once*/
		RUN_ONCE
	}
}

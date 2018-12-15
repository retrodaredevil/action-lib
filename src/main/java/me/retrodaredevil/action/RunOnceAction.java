package me.retrodaredevil.action;

class RunOnceAction extends SimpleAction {

	private final Runnable runnable;
	private final RunType type;


	RunOnceAction(Runnable runnable, RunType type){
		super(type != RunType.NON_RECYCLABLE);
		this.runnable = runnable;
		this.type = type;
	}

	@Override
	protected void onStart() {
		super.onStart();
		if(type == RunType.RUN_ONCE && getCurrentIsDone()){
			return;
		}
		runnable.run();
		setDone(true);
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

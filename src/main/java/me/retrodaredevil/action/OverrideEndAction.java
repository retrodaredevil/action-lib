package me.retrodaredevil.action;

class OverrideEndAction extends SimpleAction{
	private final Action action;
	OverrideEndAction(boolean canRecycle, Action action) {
		super(canRecycle);
		this.action = action;
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		action.update();
	}
	
	@Override
	protected void onIsDoneRequest() {
		setDone(action.isDone());
	}
}

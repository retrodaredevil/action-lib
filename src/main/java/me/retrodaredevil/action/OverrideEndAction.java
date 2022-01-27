package me.retrodaredevil.action;

class OverrideEndAction extends BaseAction {
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
	public boolean isDone() {
		return action.isDone();
	}
}

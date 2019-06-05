package me.retrodaredevil.action;

import me.retrodaredevil.action.event.EventListener;

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
	
	@Override
	public EventListener getEventListener() {
		return action.getEventListener();
	}
}

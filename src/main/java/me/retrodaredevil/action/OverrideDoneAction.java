package me.retrodaredevil.action;

import me.retrodaredevil.action.event.EventListener;

class OverrideDoneAction implements Action {
	private final Action action;
	private final boolean isDone;
	
	public OverrideDoneAction(Action action, boolean isDone) {
		this.action = action;
		this.isDone = isDone;
	}
	
	@Override
	public void update() {
		action.update();
	}
	
	@Override
	public void end() {
		action.end();
	}
	
	@Override
	public boolean isDone() {
		return isDone;
	}
	
	@Override
	public boolean isActive() {
		return action.isActive();
	}
	
	@Override
	public EventListener getEventListener() {
		return action.getEventListener();
	}
}

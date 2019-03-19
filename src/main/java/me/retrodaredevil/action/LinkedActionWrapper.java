package me.retrodaredevil.action;

import java.util.Objects;

class LinkedActionWrapper implements LinkedAction {
	
	private final Action action;
	private final Action nextAction;
	
	LinkedActionWrapper(Action action, Action nextAction) {
		this.action = Objects.requireNonNull(action);
		this.nextAction = nextAction;
		if(action.isActive()){
			throw new IllegalArgumentException("action cannot be active!");
		}
	}
	
	@Override
	public Action getNextAction() {
        return nextAction;
	}
	
	@Override
	public void update() {
		action.update();
	}
	
	@Override
	public void pause() {
		action.pause();
	}
	
	@Override
	public boolean isPaused() {
		return action.isPaused();
	}
	
	@Override
	public void end() {
		action.end();
	}
	
	@Override
	public boolean isDone() {
        return action.isDone();
	}
	
	@Override
	public boolean isActive() {
        return action.isActive();
	}
}

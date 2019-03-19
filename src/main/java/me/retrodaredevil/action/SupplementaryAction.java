package me.retrodaredevil.action;

class SupplementaryAction implements Action {
	private final Action mainAction, supplementaryAction;
	public SupplementaryAction(Action mainAction, Action supplementaryAction) {
		this.mainAction = mainAction;
		this.supplementaryAction = supplementaryAction;
	}
	
	@Override
	public void update() {
		mainAction.update();
		supplementaryAction.update();
	}
	
	@Override
	public void pause() {
		mainAction.pause();
		supplementaryAction.pause();
	}
	
	@Override
	public boolean isPaused() {
		return mainAction.isPaused();
	}
	
	@Override
	public void end() {
		mainAction.end();
		supplementaryAction.end();
	}
	
	@Override
	public boolean isDone() {
		return mainAction.isDone();
	}
	
	@Override
	public boolean isActive() {
		return mainAction.isActive();
	}
}

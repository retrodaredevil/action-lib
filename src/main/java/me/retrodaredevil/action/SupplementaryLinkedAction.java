package me.retrodaredevil.action;

class SupplementaryLinkedAction implements LinkedAction {
	
	private final LinkedAction linkedAction;
	private final Action supplementaryAction;
	
	public SupplementaryLinkedAction(LinkedAction linkedAction, Action supplementaryAction) {
		this.linkedAction = linkedAction;
		this.supplementaryAction = supplementaryAction;
	}
	
	@Override
	public Action getNextAction() {
		return linkedAction.getNextAction();
	}
	
	@Override
	public void update() {
		linkedAction.update();
		supplementaryAction.update();
	}
	
	@Override
	public void end() {
		linkedAction.end();
		supplementaryAction.end();
	}
	
	@Override
	public boolean isDone() {
		return linkedAction.isDone();
	}
	
	@Override
	public boolean isActive() {
		return linkedAction.isActive();
	}
}

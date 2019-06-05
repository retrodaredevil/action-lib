package me.retrodaredevil.action;

import me.retrodaredevil.action.event.EventListener;

abstract class DynamicActionRunner extends SimpleAction {
	private final ActionChooser actionChooser;
	
	public DynamicActionRunner() {
		super(false);
		actionChooser = Actions.createActionChooser(WhenDone.BE_DONE);
	}
	protected abstract Action createAction();
	
	@Override
	protected void onStart() {
		super.onStart();
		actionChooser.setNextAction(createAction());
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		actionChooser.update();
	}
	
	@Override
	protected void onEnd(boolean peacefullyEnded) {
		super.onEnd(peacefullyEnded);
		actionChooser.end();
	}
	
	@Override
	protected void onIsDoneRequest() {
		super.onIsDoneRequest();
		setDone(actionChooser.isDone());
	}
	
	@Override
	public EventListener getEventListener() {
		return actionChooser.getEventListener();
	}
}

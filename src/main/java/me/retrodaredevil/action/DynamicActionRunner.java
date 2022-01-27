package me.retrodaredevil.action;

abstract class DynamicActionRunner extends BaseAction {
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
	protected void onEnd() {
		super.onEnd();
		actionChooser.end();
	}

	@Override
	public boolean isDone() {
		return actionChooser.isDone();
	}
}

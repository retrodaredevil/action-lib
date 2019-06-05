package me.retrodaredevil.action;

import me.retrodaredevil.action.event.EventListener;

import java.util.Collection;
import java.util.Collections;

class LinkedActionRunner extends SimpleAction implements SingleActiveActionHolder {
	
	private Action action;
	private final WhenDone whenDone;
	private final boolean immediatelyDoNextWhenDone;
	
	private final EventListener eventListener;
	
	public LinkedActionRunner(WhenDone whenDone, boolean immediatelyDoNextWhenDone, Action action) {
		super(false);
		this.action = action;
		this.whenDone = whenDone;
		this.immediatelyDoNextWhenDone = immediatelyDoNextWhenDone;
		
		eventListener = (event, hasBeenHandled) -> {
			final Action thisAction = this.action;
			if(thisAction != null){
				return thisAction.getEventListener().onEvent(event, hasBeenHandled);
			}
			return false;
		};
	}
	
	@Override
	public Action getActiveAction() {
        return action;
	}
	
	@Override
	public Collection<? extends Action> getActiveActions() {
		final Action action = this.action;
        if(action == null){
        	return Collections.emptySet();
		}
        return Collections.singleton(action);
	}
	
	@Override
	public EventListener getEventListener() {
		return eventListener;
	}
	
	@Override
	protected void onUpdate() {
		super.onUpdate();
		updateAction();
	}
	private void updateAction(){
		boolean done = false;
		if(action != null){
			action.update();
			if(action.isDone()){
				final Action nextAction;
				if(action instanceof LinkedAction){
					nextAction = ((LinkedAction) action).getNextAction();
				} else {
					nextAction = null;
				}
				if(nextAction == null){
					if(whenDone.isClearActiveWhenActiveDone()){
						endCurrent();
					}
					done = true;
				} else {
					action.end();
					action = nextAction;
					if(immediatelyDoNextWhenDone){
						updateAction();
					}
				}
			}
		} else {
			done = true;
		}
		if(whenDone.isBeDoneWhenActiveDone()) {
			setDone(done);
		} else {
			setDone(false);
		}
	}
	
	@Override
	protected void onEnd(boolean peacefullyEnded) {
		super.onEnd(peacefullyEnded);
		endCurrent();
	}
	private void endCurrent(){
		if(action != null && action.isActive()){
			action.end();
			action = null;
		}
	}
}

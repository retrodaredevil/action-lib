package me.retrodaredevil.action;

import java.util.Collection;
import java.util.Collections;

class DefaultActionChooser extends SimpleAction implements ActionChooser {

	private final WhenDone whenDone;
	private Action nextAction = null;
	private boolean clearActive = false;
	private Action activeAction = null;

	/**
	 * @param canRecycle Can {@link #update()} be called after being ended once via {@link #end()}
	 */
	DefaultActionChooser(boolean canRecycle, WhenDone whenDone) {
		super(canRecycle);
		this.whenDone = whenDone;
	}


	@Override
	public synchronized void setNextAction(Action action) {
        this.nextAction = action;
	}
	@Override
	public synchronized Action getNextAction() {
		return nextAction;
	}

	@Override
	public void setToClearAction() { setToClearAction(true); }
	@Override
	public synchronized void setToClearAction(boolean b) {
		clearActive = b;
	}
	@Override
	public synchronized boolean isSetToClearAction() {
        return clearActive;
	}

	@Override
	public synchronized Action getActiveAction() {
        return activeAction;
	}

	@Override
	public Collection<? extends Action> getActiveActions() {
        final Action activeAction = getActiveAction();
        if(activeAction == null){
        	return Collections.emptySet();
		}
        return Collections.singleton(activeAction);
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		synchronized (this) {
			if (nextAction != null || clearActive) {
				endCurrent();
				activeAction = nextAction;
				nextAction = null;
				clearActive = false;
			}
		}
        boolean done = false;
		if(activeAction != null){
			activeAction.update();
			final boolean isActiveDone = activeAction.isDone();
			if(whenDone.isBeDoneWhenActiveDone()) {
                done = isActiveDone;
			}
			if(whenDone.isClearActiveWhenActiveDone()){
				if(isActiveDone){
					activeAction.end();
					activeAction = null;
				}
			}
		} else {
			if(whenDone.isBeDoneWhenActiveDone()){
                done = true;
			}
		}
		setDone(done);
	}

	@Override
	protected void onEnd(boolean peacefullyEnded) {
		super.onEnd(peacefullyEnded);
		endCurrent();
	}

	private void endCurrent(){
		if(activeAction != null){
			if(activeAction.isActive()) {
				activeAction.end();
			} else {
				throw new IllegalStateException("The activeAction should be active! It's not! activeAction: " + activeAction);
			}
			activeAction = null;
		}
	}
}

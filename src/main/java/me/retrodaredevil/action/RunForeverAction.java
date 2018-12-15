package me.retrodaredevil.action;

import java.util.Objects;

class RunForeverAction implements Action {
	private final Runnable runnable;
	private final boolean canRecycle;
	private boolean running = false;
	private boolean endedOnce = false;

	RunForeverAction(Runnable runnable, boolean canRecycle){
		this.runnable = Objects.requireNonNull(runnable);
		this.canRecycle = canRecycle;
	}

	@Override
	public void update() {
		if(endedOnce && !canRecycle){
			throw new IllegalStateException("This is not recyclable! this: " + this);
		}
		running = true;
        runnable.run();
	}

	@Override
	public void end() {
		running = false;
		endedOnce = true;
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public boolean isActive() {
        return running;
	}
}

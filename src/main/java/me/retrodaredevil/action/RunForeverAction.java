package me.retrodaredevil.action;

import java.util.Objects;

class RunForeverAction implements Action {
	private final Runnable runnable;
	private final boolean canRecycle;
	private boolean running = false;
	private boolean paused = false;
	private boolean endedOnce = false;

	RunForeverAction(boolean canRecycle, Runnable runnable){
		this.runnable = Objects.requireNonNull(runnable);
		this.canRecycle = canRecycle;
	}

	@Override
	public void update() {
		if(endedOnce && !canRecycle){
			throw new IllegalStateException("This is not recyclable! this: " + this);
		}
		if(paused){
			paused = false;
		}
		running = true;
        runnable.run();
	}
	
	@Override
	public void pause() {
		if(paused){
			throw new IllegalStateException("Already paused!");
		}
		paused = true;
	}
	
	@Override
	public boolean isPaused() {
		return paused;
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

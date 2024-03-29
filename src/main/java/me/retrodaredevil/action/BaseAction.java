package me.retrodaredevil.action;

public abstract class BaseAction implements Action {

    private final boolean canRecycle;

    /** true when {@link #isActive()} is true, false when {@link #isActive()} is false*/
    private volatile boolean running = false;
    /** Set to true when {@link #end()} is called. Should never be set back to false once true*/
    private boolean oneWayEndedFlag = false;

    /**
     *
     * @param canRecycle Can {@link #update()} be called after being ended once via {@link #end()}
     */
    protected BaseAction(boolean canRecycle){
        this.canRecycle = canRecycle;
    }


    protected void onStart(){}
    protected void onUpdate(){}

    /**
     * Called when being ended.
     * <p>
     * NOTE: {@link #isActive()} will have an undefined result. Do not call it.
     */
    protected void onEnd(){}

    @Override
    public final void update() {
        if(!running){
            if(oneWayEndedFlag && !canRecycle){
                throw new IllegalStateException("This action cannot be recycled!");
            }
            running = true;
            onStart();
        }
        onUpdate();
    }

    @Override
    public final void end() {
        if(!running){
            throw new IllegalStateException("This action must be active to be able to end it!");
        }
        onEnd();
        running = false;
        oneWayEndedFlag = true;
    }

    @Override
    public final boolean isActive() {
        return running;
    }
}

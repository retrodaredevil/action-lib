package me.retrodaredevil.action;

/**
 * Can be used with the factory methods {@link Actions#createActionChooser(WhenDone)} and {@link Actions#createActionChooserRecyclable(WhenDone)}
 */
public enum WhenDone {
	/** When the active action is done, this will keep going like nothing happened. NOT RECOMMENDED*/
	DO_NOTHING(false, false),

	/** When the active action is done, it will be ended and cleared*/
	CLEAR_ACTIVE(true, false),

	/**
	 * When the active action is done, {@link Action#isDone()} will return true
	 * <p>
	 * When there is no active action, {@link Action#isDone()} will also return true
	 */
	BE_DONE(false, true),

	/**
	 * When the active action is done, it will be ended and cleared AND {@link Action#isDone()} will return true
	 * <p>
	 * When there is no active action, {@link Action#isDone()} will also return true
	 */
	CLEAR_ACTIVE_AND_BE_DONE(true, true)

	;

	private final boolean clearActiveWhenActiveDone;
	private final boolean beDoneWhenActiveDone;

	WhenDone(boolean clearActiveWhenActiveDone, boolean beDoneWhenActiveDone){
		this.clearActiveWhenActiveDone = clearActiveWhenActiveDone;
		this.beDoneWhenActiveDone = beDoneWhenActiveDone;
	}
	public boolean isClearActiveWhenActiveDone(){
		return clearActiveWhenActiveDone;
	}
	public boolean isBeDoneWhenActiveDone(){
		return beDoneWhenActiveDone;
	}
}


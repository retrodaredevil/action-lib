package me.retrodaredevil.action.event;

public interface EventListener {
	boolean onEvent(ActionEvent event, boolean hasBeenHandled);
	class Defaults {
		public static final EventListener DO_NOTHING = (event, hasBeenHandled) -> false;
	}
}

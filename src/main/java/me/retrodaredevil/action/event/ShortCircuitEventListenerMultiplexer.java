package me.retrodaredevil.action.event;

import java.util.Set;

public class ShortCircuitEventListenerMultiplexer extends SetEventListenerMultiplexer {
	private final boolean returnIfAlreadyHandled;
	
	protected ShortCircuitEventListenerMultiplexer(Set<EventListener> listeners, boolean returnIfAlreadyHandled) {
		super(listeners);
		this.returnIfAlreadyHandled = returnIfAlreadyHandled;
	}
	
	@Override
	public boolean onEvent(ActionEvent event, boolean hasBeenHandled) {
		if(returnIfAlreadyHandled && hasBeenHandled){
			return false;
		}
		for(EventListener listener : listeners){
			if(listener.onEvent(event, hasBeenHandled)){
				return true;
			}
		}
		return false;
	}
	
}

package me.retrodaredevil.action.event;

import java.util.Set;

abstract class SetEventListenerMultiplexer implements EventListenerMultiplexer {
	protected final Set<EventListener> listeners;
	
	SetEventListenerMultiplexer(Set<EventListener> listeners) {
		this.listeners = listeners;
	}
	
	@Override
	public boolean add(EventListener eventListener) {
		return listeners.add(eventListener);
	}
	
	@Override
	public boolean remove(EventListener eventListener) {
		return listeners.remove(eventListener);
	}
	
}

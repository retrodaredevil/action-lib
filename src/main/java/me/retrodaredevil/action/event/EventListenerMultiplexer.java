package me.retrodaredevil.action.event;

public interface EventListenerMultiplexer extends EventListener {
	boolean add(EventListener eventListener);
	boolean remove(EventListener eventListener);
}

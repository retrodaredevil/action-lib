package me.retrodaredevil.action;

public interface LinkedAction extends Action {
	Action getNextAction();
}

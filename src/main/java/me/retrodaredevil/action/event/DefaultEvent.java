package me.retrodaredevil.action.event;

public enum DefaultEvent implements ActionEvent {
	PAUSE("pause")
	;
	private final String name;
	
	DefaultEvent(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
}

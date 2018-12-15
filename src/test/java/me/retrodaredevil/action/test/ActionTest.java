package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class ActionTest {
	private ActionTest() { throw new UnsupportedOperationException(); }

	static void testRecyclable(Action action){
		action.update();
		action.end();
		action.update();
	}
	static void testNonRecyclable(Action action){
		action.update();
		action.end();
		assertThrows(IllegalStateException.class, action::update);
	}

}

package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class StartActionTest {
	@Test
	void testStartAction(){
		final boolean[] start = {false};
		final boolean[] value = {false};
		final Action action = Actions.createWaitToStartAction(Actions.createRunOnce(() -> value[0] = true), () -> start[0]);

		assertFalse(start[0]);
		assertFalse(value[0]);

		for(int i = 0; i < 10; i++) {
			action.update();

			assertFalse(start[0]);
			assertFalse(value[0]);
		}

		start[0] = true;
		action.update();
		assertTrue(value[0]);
	}
	@Test
	void testRecycling(){
		final Runnable doNothing = () -> {};
		final Actions.CanStart canStart = () -> false;
		ActionTest.testNonRecyclable(Actions.createWaitToStartAction(Actions.createRunOnce(doNothing), canStart));
		ActionTest.testRecyclable(Actions.createWaitToStartActionRecyclable(Actions.createRunOnce(doNothing), canStart));
	}
}

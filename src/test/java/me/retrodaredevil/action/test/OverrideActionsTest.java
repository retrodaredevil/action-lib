package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class OverrideActionsTest {
	@Test
	void testOverrideIsDoneAction(){
		{
			boolean[] ran = {false};
			final Action action = Actions.createIsDoneOverride(Actions.createRunForever(() -> ran[0] = true), true);
			assertFalse(ran[0]);
			action.update();
			assertTrue(ran[0]);
			assertTrue(action.isDone());
			assertTrue(action.isActive());
			
			action.end();
			assertFalse(action.isActive());
		}
		{
			boolean[] ran = {false};
			final Action action = Actions.createIsDoneOverride(Actions.createRunOnce(() -> ran[0] = true), false);
			assertFalse(ran[0]);
			action.update();
			assertTrue(ran[0]);
			assertFalse(action.isDone());
			assertTrue(action.isActive());
		}
	}
	@Test
	void testOverrideEndAction(){
		boolean[] ran = {false};
		final Action running = Actions.createRunOnce(() -> ran[0] = true);
		final Action action = Actions.createEndOverride(running);
		
		assertFalse(ran[0]);
		assertFalse(running.isActive());
		assertFalse(action.isActive());
		
		action.update();
		assertTrue(running.isActive());
		assertTrue(action.isActive());
		
		action.end();
		assertTrue(running.isActive());
		assertFalse(action.isActive());
	}
	
	@Test
	void testOverrideEndActionRecycling(){
		ActionTest.testNonRecyclable(Actions.createEndOverride(Actions.createRunOnce(() -> {})));
		ActionTest.testRecyclable(Actions.createEndOverrideRecyclable(Actions.createRunOnce(() -> {})));
	}
}

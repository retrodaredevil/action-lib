package me.retrodaredevil.action.test;

import org.junit.jupiter.api.Test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RunOnceActionTest {
	@Test
	void testRunOnceNonRecyclable(){
        final int[] value = {0};
		final Action runOnce = Actions.createRunOnce(() -> value[0]++);

		assertEquals(0, value[0]);
		assertFalse(runOnce.isActive());

		runOnce.update();
		assertTrue(runOnce.isActive());
		assertTrue(runOnce.isDone());
		assertEquals(1, value[0]);

		runOnce.update(); // test to make sure there are no errors if we update after it's done (this is allowed)
		assertTrue(runOnce.isActive());
		assertTrue(runOnce.isDone());
		assertEquals(1, value[0]);

		runOnce.end();
		assertFalse(runOnce.isActive());

		assertThrows(IllegalStateException.class, runOnce::update);
	}
	@Test
	void testRunOnceEveryStart(){
		final int[] value = {0};
		final Action action = Actions.createRunOnceRecyclable(() -> value[0]++);

		testRecyclable(action, value);

		action.update();
		assertTrue(action.isActive());
		assertTrue(action.isDone());
		assertEquals(2, value[0]);
	}

	@Test
	void testRunOnceOnlyOnce(){
		final int[] value = {0};
		final Action action = Actions.createRunOnceRecyclableRunOnce(() -> value[0]++);

		testRecyclable(action, value);
		assertEquals(1, value[0]);
		assertFalse(action.isActive());

		action.update();
		assertTrue(action.isActive());
		assertTrue(action.isDone());
		assertEquals(1, value[0]); // make sure value stayed at 1
	}
	private void testRecyclable(Action action, int[] value){
		assertEquals(0, value[0]);
		assertFalse(action.isActive());

		// make sure value increased
		action.update();
		assertTrue(action.isActive());
		assertTrue(action.isDone());
		assertEquals(1, value[0]);

		// make sure value did not increase
		action.update();
		assertTrue(action.isActive());
		assertTrue(action.isDone());
		assertEquals(1, value[0]);

		action.end();
		assertFalse(action.isActive());
		assertEquals(1, value[0]);
	}
}

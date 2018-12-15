package me.retrodaredevil.action.test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
		final Action runOnce = Actions.createRunOnceNonRecyclable(() -> value[0]++);

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
}

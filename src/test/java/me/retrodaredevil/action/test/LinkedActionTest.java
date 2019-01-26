package me.retrodaredevil.action.test;

import me.retrodaredevil.action.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class LinkedActionTest {
	@Test
	void testLinkedActionRunner(){
		final int[] value = {0};
		final Action set1 = Actions.createRunOnce(() -> value[0] = 1);
		final Action set2 = Actions.createRunOnce(() -> value[0] = 2);
		final Action set3 = Actions.createRunOnce(() -> value[0] = 3);
		
		final Action runner = Actions.createLinkedActionRunner(
				Actions.createLinkedAction(set1, Actions.createLinkedAction(set2, set3)),
				WhenDone.CLEAR_ACTIVE_AND_BE_DONE,
				false
		);
		
		assertEquals(0, value[0]);
		assertFalse(runner.isActive());
		
		runner.update();
		assertEquals(1, value[0]);
		assertFalse(set1.isActive());
		assertTrue(runner.isActive());
		assertFalse(runner.isDone());
		
		runner.update();
		assertEquals(2, value[0]);
		assertFalse(set1.isActive());
		assertFalse(set2.isActive()); // it should have started and ended
		assertTrue(runner.isActive());
		assertFalse(runner.isDone());
		
		runner.update();
		assertEquals(3, value[0]);
		assertFalse(set2.isActive());
		assertFalse(set3.isActive());
		assertTrue(runner.isActive());
		
		assertTrue(runner.isDone());
		runner.end();
	}
	
}

package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class SupplementaryTest {
	@Test
	void testSupplementaryAction(){
		final int[] timesRan = {0};
		final int[] timesRanSupplementary = {0};
		final Action mainAction = Actions.createRunForever(() -> timesRan[0]++);
		final Action supplementaryAction = Actions.createRunOnce(() -> timesRanSupplementary[0]++);
		final Action action = Actions.createSupplementaryAction(mainAction, supplementaryAction);
		
		assertFalse(action.isActive());
		assertFalse(mainAction.isActive());
		assertFalse(supplementaryAction.isActive());
		
		action.update();
		assertTrue(action.isActive());
		assertTrue(mainAction.isActive());
		assertTrue(supplementaryAction.isActive());
		assertFalse(action.isDone());
		assertFalse(mainAction.isDone());
		assertTrue(supplementaryAction.isDone());
		
		action.update();
		assertEquals(2, timesRan[0]);
		assertEquals(1, timesRanSupplementary[0]);
		
		action.end();
		assertFalse(action.isActive());
		assertFalse(mainAction.isActive());
		assertFalse(supplementaryAction.isActive());
		
	}
}

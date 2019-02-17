package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class DynamicActionRunnerTest {
	@Test
	void testDynamicActionRunner(){
		final boolean[] hasBeenCreated = {false};
		final int[] timesRan = {0};
		final Action runner = Actions.createDynamicActionRunner(() -> {
			if(hasBeenCreated[0]){
				Assertions.fail();
			}
			hasBeenCreated[0] = true;
			return Actions.createRunOnce(() -> timesRan[0]++);
		});
		
		assertFalse(hasBeenCreated[0]);
		assertFalse(runner.isActive());
		
		assertEquals(0, timesRan[0]);
		runner.update();
		assertTrue(hasBeenCreated[0]);
		assertTrue(runner.isActive());
		assertEquals(1, timesRan[0]);
		assertTrue(runner.isDone());
		runner.update();
		assertEquals(1, timesRan[0]);
		
		runner.end();
		
	}
	@Test
	void testDynamicActionRunnerRecycling(){
		ActionTest.testNonRecyclable(Actions.createDynamicActionRunner(() -> Actions.createRunOnce(() -> {})));
	}
	@Test
	void testDynamicActionRunnerNull(){
		final Action runner = Actions.createDynamicActionRunner(() -> null);
		
		assertFalse(runner.isActive());
		runner.update();
		assertTrue(runner.isDone());
		runner.end();
	}
}

package me.retrodaredevil.action.test;

import org.junit.jupiter.api.Test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RunForeverActionTest {
	@Test
	void testForever(){
		final int[] array = {0};
		final Action forever = Actions.createRunForever(() -> array[0]++);

		assertEquals(0, array[0]);
		assertFalse(forever.isActive());

		updateTenTimes(array, forever);

		forever.end();
		assertFalse(forever.isActive());

		assertThrows(IllegalStateException.class, forever::update);
	}
	@Test
	void testForeverRecyclable(){
		final int[] array = {0};
		final Action forever = Actions.createRunForeverRecyclable(() -> array[0]++);

		assertEquals(0, array[0]);
		assertFalse(forever.isActive());

		updateTenTimes(array, forever);
		assertEquals(10, array[0]);

		forever.end();
		assertFalse(forever.isActive());
		assertEquals(10, array[0]);

		for(int i = 0; i < 10; i++) {
			forever.update(); // make sure it can keep updating
			assertTrue(forever.isActive());
			assertFalse(forever.isDone());
			assertEquals(i + 11, array[0]);
		}
	}

	private void updateTenTimes(int[] array, Action forever) {
		for(int i = 0; i < 10; i++) {
			forever.update();
			assertTrue(forever.isActive());
			assertFalse(forever.isDone());
			assertEquals(i + 1, array[0]);
		}
	}
}

package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;
import me.retrodaredevil.action.ActiveActionHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ActiveActionHolderTest {
	private ActiveActionHolderTest() { throw new UnsupportedOperationException(); }

	static void testClearOnEnd(ActiveActionHolder holder){
		final int[] value = {0};
		final Action action = Actions.createRunForeverRecyclable(() -> value[0]++);
		holder.add(action);

		assertEquals(0, value[0]);
		holder.update();
		assertEquals(1, value[0]);
		assertTrue(holder.getActiveActions().contains(action));

		holder.end();
		assertEquals(1, value[0]);

		holder.update();
		assertEquals(1, value[0]);
		assertFalse(holder.getActiveActions().contains(action));

		holder.update();
		assertEquals(1, value[0]);
		assertFalse(holder.getActiveActions().contains(action));
	}
	static void testNotClearOnEnd(ActiveActionHolder holder){
		final int[] value = {0};
		final Action action = Actions.createRunForeverRecyclable(() -> value[0]++);
		holder.add(action);

		assertEquals(0, value[0]);
		holder.update();
		assertEquals(1, value[0]);
        assertTrue(holder.getActiveActions().contains(action));

		holder.end();
		assertEquals(1, value[0]);

		holder.update();
		assertEquals(2, value[0]);
		assertTrue(holder.getActiveActions().contains(action));

		holder.update();
		assertEquals(3, value[0]);
		assertTrue(holder.getActiveActions().contains(action));
	}
}

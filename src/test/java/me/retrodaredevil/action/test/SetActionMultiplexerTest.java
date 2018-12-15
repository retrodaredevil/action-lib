package me.retrodaredevil.action.test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.ActionMultiplexer;
import me.retrodaredevil.action.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SetActionMultiplexerTest {
	@Test
	void testNeverEnding(){
		final ActionMultiplexer multiplexer = new Actions.ActionMultiplexerBuilder().canBeDone(false).build();
		final Collection<Integer> list = new ArrayList<>();
		multiplexer.add(Actions.createRunOnce(() -> list.add(0)));
		multiplexer.add(Actions.createRunOnce(() -> list.add(1)));
		multiplexer.add(Actions.createRunOnce(() -> list.add(2)));

		assertTrue(list.isEmpty());
		assertFalse(multiplexer.isActive());
		assertFalse(multiplexer.isDone());

		multiplexer.update();

		assertTrue(list.contains(0));
		assertTrue(list.contains(1));
		assertTrue(list.contains(2));
		assertEquals(3, list.size());
		assertFalse(multiplexer.isDone());

	}
	@Test
	void testEnding(){
		final ActionMultiplexer multiplexer = new Actions.ActionMultiplexerBuilder().canBeDone(true).build();
		final Collection<Integer> list = new ArrayList<>();
		multiplexer.add(Actions.createRunOnce(() -> list.add(0)));
		multiplexer.add(Actions.createRunOnce(() -> list.add(1)));
		multiplexer.add(Actions.createRunOnce(() -> list.add(2)));

		assertTrue(list.isEmpty());
		assertFalse(multiplexer.isActive());
		assertFalse(multiplexer.isDone());

		multiplexer.update();

		assertTrue(list.contains(0));
		assertTrue(list.contains(1));
		assertTrue(list.contains(2));
		assertEquals(3, list.size());
		assertTrue(multiplexer.isDone());

		multiplexer.end();

		assertThrows(IllegalStateException.class, multiplexer::update);
	}

	@Test
	void testClearing(){
		ActiveActionHolderTest.testClearOnEnd(new Actions.ActionMultiplexerBuilder().canRecycle(true).clearAllOnEnd(true).build());
		ActiveActionHolderTest.testNotClearOnEnd(new Actions.ActionMultiplexerBuilder().canRecycle(true).clearAllOnEnd(false).build());
	}
	@Test
	void testRecycling(){
		ActionTest.testRecyclable(new Actions.ActionMultiplexerBuilder().canRecycle(true).build());
		ActionTest.testNonRecyclable(new Actions.ActionMultiplexerBuilder().canRecycle(false).build());
	}
	@Test
	void testRemoving(){
		final ActionMultiplexer multiplexer = new Actions.ActionMultiplexerBuilder().build();
		final boolean[] value = {false};
        final Action action = Actions.createRunForever(() -> value[0] = true);
        multiplexer.add(action);

		assertFalse(value[0]);
        multiplexer.update();
        assertTrue(value[0]);

        value[0] = false;
        multiplexer.remove(action);
        assertFalse(value[0]);
        multiplexer.update();
        assertFalse(value[0]);

	}
}

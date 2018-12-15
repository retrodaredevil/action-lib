package me.retrodaredevil.action.test;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import me.retrodaredevil.action.ActionQueue;
import me.retrodaredevil.action.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


final class DequeActionQueueTest {

	@Test
	void testNeverEndingQueue(){
		final ActionQueue queue = new Actions.ActionQueueBuilder().canBeDone(false).build();
        final List<Integer> list = new ArrayList<>();
        queue.add(Actions.createRunOnce(() -> list.add(0)));
		queue.add(Actions.createRunOnce(() -> list.add(1)));
		queue.add(Actions.createRunOnce(() -> list.add(2)));

		assertEquals(0, list.size());
		assertFalse(queue.isActive());

		queue.update();
		assertEquals(1, list.size());
		assertFalse(queue.isDone());
		assertTrue(queue.isActive());

		queue.update();
		assertEquals(2, list.size());
		assertFalse(queue.isDone());
		assertTrue(queue.isActive());

		queue.update();
		assertEquals(3, list.size());
		assertFalse(queue.isDone());
		assertTrue(queue.isActive());

		queue.end(); // force end
		assertFalse(queue.isActive()); // make sure not active after forced end

		assertEquals(0, list.get(0).intValue());
		assertEquals(1, list.get(1).intValue());
		assertEquals(2, list.get(2).intValue());
	}
	@Test
	void testEndingQueue(){
		final ActionQueue queue = new Actions.ActionQueueBuilder().canBeDone(true).build();
        final List<Integer> list = new ArrayList<>();
        queue.add(Actions.createRunOnce(() -> list.add(0)));
		queue.add(Actions.createRunOnce(() -> list.add(1)));
		queue.add(Actions.createRunOnce(() -> list.add(2)));

		assertEquals(0, list.size());
		assertFalse(queue.isActive());

		queue.update();
		assertEquals(1, list.size());
		assertFalse(queue.isDone());
		assertTrue(queue.isActive());

		queue.update();
		assertEquals(2, list.size());
		assertFalse(queue.isDone());
		assertTrue(queue.isActive());

		queue.update();
		assertEquals(3, list.size());
		assertTrue(queue.isDone()); // difference
		assertTrue(queue.isActive());

		queue.end(); // end because isDone() is true
		assertFalse(queue.isActive()); // make sure not active

		assertEquals(0, list.get(0).intValue());
		assertEquals(1, list.get(1).intValue());
		assertEquals(2, list.get(2).intValue());
	}
	@Test
	void testRecycling(){
		ActionTest.testNonRecyclable(new Actions.ActionQueueBuilder().canRecycle(false).build());
		ActionTest.testRecyclable(new Actions.ActionQueueBuilder().canRecycle(true).build());
	}

	@Test
	void testClearing(){
		ActiveActionHolderTest.testNotClearOnEnd(new Actions.ActionQueueBuilder().canRecycle(true).clearAllOnEnd(false).build());
		ActiveActionHolderTest.testClearOnEnd(new Actions.ActionQueueBuilder().canRecycle(true).clearAllOnEnd(true).build());
	}

}

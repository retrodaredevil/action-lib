package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.ActionChooser;
import me.retrodaredevil.action.Actions;
import me.retrodaredevil.action.WhenDone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class DefaultActionChooserTest {
	@Test
	void testActionChooser(){
		final ActionChooser actionChooser = Actions.createActionChooser(WhenDone.DO_NOTHING);
		final int[] value = {0};
		final Action set1 = Actions.createRunForeverRecyclable(() -> value[0] = 1);
		final Action set2 = Actions.createRunForever(() -> value[0] = 2);
		final Action set3 = Actions.createRunForever(() -> value[0] = 3);

		assertNull(actionChooser.getActiveAction());
		assertTrue(actionChooser.getActiveActions().isEmpty());

		// set to 1
		actionChooser.setNextAction(set1);
		assertFalse(set1.isActive());
		assertEquals(0, value[0]);
		actionChooser.update();
		assertTrue(set1.isActive());
		assertEquals(1, value[0]);

		// set to 2
		actionChooser.setNextAction(set2);
		assertTrue(set1.isActive());
		assertFalse(set2.isActive());
		assertEquals(1, value[0]);
		actionChooser.update();
		assertFalse(set1.isActive());
		assertTrue(set2.isActive());
		assertEquals(2, value[0]);

		// set to 3
		actionChooser.setNextAction(set3);
		assertTrue(set2.isActive());
		assertFalse(set3.isActive());
		assertEquals(2, value[0]);
		actionChooser.update();
		assertFalse(set2.isActive());
		assertTrue(set3.isActive());
		assertEquals(3, value[0]);

		// set back to 1
		actionChooser.setNextAction(set1);
		assertTrue(set3.isActive());
		assertEquals(3, value[0]);
		actionChooser.update();
		assertFalse(set3.isActive());
		assertTrue(set1.isActive());
		assertEquals(1, value[0]);

		// set2 is not recyclable. Make sure it throws an error
		actionChooser.setNextAction(set2);
		assertThrows(IllegalStateException.class, actionChooser::update);
	}

	@Test
	void testClearActive(){
		final ActionChooser actionChooser = Actions.createActionChooser(WhenDone.CLEAR_ACTIVE);
		testClearActiveWhenDone(actionChooser);
		assertFalse(actionChooser.isDone());
	}
	@Test
	void testClearActiveAndBeDone(){
		final ActionChooser actionChooser = Actions.createActionChooser(WhenDone.CLEAR_ACTIVE_AND_BE_DONE);
		testClearActiveWhenDone(actionChooser);
		assertTrue(actionChooser.isDone());
	}
	private void testClearActiveWhenDone(ActionChooser actionChooser){
		final boolean[] value = {false};

		assertNull(actionChooser.getActiveAction());
		actionChooser.setNextAction(Actions.createRunOnce(() -> value[0] = true));
		assertNull(actionChooser.getActiveAction());

		assertFalse(value[0]);
		actionChooser.update();
		assertTrue(value[0]);
		assertNull(actionChooser.getActiveAction());

	}

	@Test
	void testBeDone(){
		final ActionChooser actionChooser = Actions.createActionChooser(WhenDone.BE_DONE);
		final boolean[] value = {false};

		actionChooser.update();
		assertTrue(actionChooser.isDone());

		actionChooser.setNextAction(Actions.createRunOnce(() -> value[0] = true));
		actionChooser.update();
		assertTrue(actionChooser.isDone());
		assertTrue(value[0]);

	}

	@Test
	void testRecyclable(){
		ActionTest.testNonRecyclable(Actions.createActionChooser(WhenDone.DO_NOTHING));
		ActionTest.testRecyclable(Actions.createActionChooserRecyclable(WhenDone.DO_NOTHING));
	}

	@Test
	void testSetToClear(){
		final ActionChooser actionChooser = Actions.createActionChooser(WhenDone.DO_NOTHING);
		final int[] value = {0};

		final Action forever = Actions.createRunForever(() -> value[0]++);
		actionChooser.setNextAction(forever);
		actionChooser.update();

		assertEquals(forever, actionChooser.getActiveAction());
		assertEquals(1, value[0]);

		actionChooser.update();
		assertEquals(2, value[0]);

		actionChooser.setToClearAction();
		actionChooser.update();
		assertEquals(2, value[0]); // make sure it didn't run
	}
}

package me.retrodaredevil.action.test;

import me.retrodaredevil.action.Action;
import me.retrodaredevil.action.Actions;
import me.retrodaredevil.action.TryCatchAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class TryCatchActionTest {
	@Test
	void testTryCatchAction(){
		final boolean[] ran = {false};
		final Action action = new TryCatchAction<RuntimeException>(Actions.createRunOnce(() -> { throw new RuntimeException("my exception"); }), RuntimeException.class) {
			@Override
			protected void onCatchUpdate(RuntimeException throwable) {
				assertEquals("my exception", throwable.getMessage());
				ran[0] = true;
				setDone(true);
			}
			
			@Override
			protected void onCatchEnd(RuntimeException throwable) {
				Assertions.fail();
				throw new AssertionError();
			}
			
			@Override
			protected boolean onCatchIsDone(RuntimeException throwable) {
				Assertions.fail();
				throw new AssertionError();
			}
		};
		assertFalse(ran[0]);
		action.update();
		assertTrue(ran[0]);
		assertTrue(action.isDone());
		assertTrue(action.isActive());
		
		action.end();
		assertFalse(action.isActive());
	}
}

package com.minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
	@Test
	void testGameStateEnum() {
		assertTrue(GameState.PLAYING.ordinal() == 0);
		assertTrue(GameState.WON.ordinal() == 1);
		assertTrue(GameState.LOST.ordinal() == 2);
	}
}

package com.minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameFlowTest {
    @Test
    void testParsePositionValid() {
        Game game = new Game();
        Position pos = game.parsePosition("A1", 5);
        assertEquals(0, pos.row());
        assertEquals(0, pos.col());
        pos = game.parsePosition("C3", 5);
        assertEquals(2, pos.row());
        assertEquals(2, pos.col());
    }

    @Test
    void testParsePositionOutOfBounds() {
        Game game = new Game();
        assertThrows(IllegalArgumentException.class, () -> game.parsePosition("Z1", 5));
        assertThrows(IllegalArgumentException.class, () -> game.parsePosition("A6", 5));
    }

    @Test
    void testInputPattern() {
        assertTrue(Game.INPUT_PATTERN.matcher("A1").matches());
        assertTrue(Game.INPUT_PATTERN.matcher("B10").matches());
        assertFalse(Game.INPUT_PATTERN.matcher("AA1").matches());
        assertFalse(Game.INPUT_PATTERN.matcher("1A").matches());
        assertFalse(Game.INPUT_PATTERN.matcher("A0").matches());
    }
}

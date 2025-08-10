package com.minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PositionTest {
    @Test
    void testIsValid() {
        Position pos = new Position(0, 0);
        assertTrue(pos.isValid(3));
        assertFalse(pos.isValid(0));
        assertFalse(new Position(-1, 0).isValid(3));
        assertFalse(new Position(0, 3).isValid(3));
    }

    @Test
    void testGetAdjacentPositions() {
        Position pos = new Position(1, 1);
        Position[] adj = pos.getAdjacentPositions(3);
        assertEquals(8, adj.length);
        for (Position p : adj) {
            assertTrue(p.isValid(3));
        }
        // Corner case
        Position corner = new Position(0, 0);
        Position[] cornerAdj = corner.getAdjacentPositions(3);
        assertEquals(3, cornerAdj.length);
    }
}

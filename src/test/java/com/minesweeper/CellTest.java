package com.minesweeper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {
    @Test
    void testBuilderAndReveal() {
        Cell cell = new Cell.Builder(0, 0).setMine(false).setAdjacentMines(2).build();
        assertFalse(cell.isMine());
        assertFalse(cell.isRevealed());
        assertEquals(2, cell.getAdjacentMines());
        Cell revealed = cell.reveal();
        assertTrue(revealed.isRevealed());
        assertEquals(cell.getPosition(), revealed.getPosition());
    }

    @Test
    void testMineCellDisplay() {
        Cell cell = new Cell.Builder(1, 1).setMine(true).setRevealed(true).build();
        assertEquals('*', cell.getDisplayChar());
    }

   

    @Test
    void testUnrevealedCellDisplay() {
        Cell cell = new Cell.Builder(3, 3).build();
        assertEquals('_', cell.getDisplayChar());
    }

    @Test
    void testAdjacentMinesDisplay() {
        Cell cell = new Cell.Builder(4, 4).setAdjacentMines(3).setRevealed(true).build();
        assertEquals('3', cell.getDisplayChar());
    }

    @Test
    void testToString() {
        Cell cell = new Cell.Builder(0, 0).setMine(false).setAdjacentMines(0).build();
        assertEquals("_", cell.toString());
        Cell revealed = cell.reveal();
        assertEquals("0", revealed.toString());
    }
}

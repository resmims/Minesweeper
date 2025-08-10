
package com.minesweeper;
import java.util.Random;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

class BoardTest {
    @Test
    void testBoardCreation() {
        Board board = Board.create(5, 5);
        int mineCount = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (board.getCell(new Position(r, c)).isMine()) mineCount++;
            }
        }
    assertEquals(5, mineCount);
    }

    @Test
    void testInvalidBoardSize() {
    assertThrows(IllegalArgumentException.class, () -> Board.create(1, 1));
    assertThrows(IllegalArgumentException.class, () -> Board.create(27, 1));
    }

    @Test
    void testInvalidMineCount() {
    assertThrows(IllegalArgumentException.class, () -> Board.create(5, 0));
    assertThrows(IllegalArgumentException.class, () -> Board.create(5, 13));
    }
    
        @Test
        void testRevealAlreadyRevealedCell() {
            Board board = Board.create(3, 1);
            Position pos = new Position(0, 0);
            Board revealed = board.reveal(pos);
            Board again = revealed.reveal(pos);
            assertSame(revealed, again);
        }

        @Test
        void testRevealOutOfBounds() {
            Board board = Board.create(3, 1);
            Position pos = new Position(-1, 0);
            Board result = board.reveal(pos);
            assertSame(board, result);
            pos = new Position(0, 3);
            result = board.reveal(pos);
            assertSame(board, result);
        }

        @Test
        void testRevealWhenGameOver() {
            Board board = Board.create(3, 1);
            // Find mine and lose
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    Position pos = new Position(r, c);
                    if (board.getCell(pos).isMine()) {
                        Board lost = board.reveal(pos);
                        // Try to reveal another cell after game over
                        Board afterLost = lost.reveal(new Position(0, 0));
                        assertSame(lost, afterLost);
                        return;
                    }
                }
            }
            fail();
        }

        @Test
        void testDeepCopyGrid() {
            Board board = Board.create(3, 1);
            Cell[][] copy = board.deepCopyGrid();
            assertEquals(3, copy.length);
            for (int i = 0; i < 3; i++) {
                assertEquals(3, copy[i].length);
            }
            // Changing copy should not affect original
            copy[0][0] = new Cell.Builder(0, 0).setMine(true).build();
            assertNotEquals(copy[0][0], board.getCell(new Position(0, 0)));
        }

        @Test
        void testToStringFormat() {
            Board board = Board.create(2, 1);
            String str = board.toString();
            assertTrue(str.contains("A "));
            assertTrue(str.contains("B "));
            assertTrue(str.contains("1 "));
            assertTrue(str.contains("2 "));
        }

        @Test
        void testRevealAdjacentCellsBFS() {
            // Custom board with no mines, all adjacentMines = 0
            int size = 3;
            Cell[][] grid = new Cell[size][size];
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    grid[r][c] = new Cell.Builder(r, c).setMine(false).setAdjacentMines(0).build();
                }
            }
            Board board = new Board(size, grid, GameState.PLAYING, new Random());
            Board revealed = board.reveal(new Position(1, 1));
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    assertTrue(revealed.getCell(new Position(r, c)).isRevealed());
                }
            }
        }

    @Test
    void testRevealMine() {
        Board board = Board.create(3, 1);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Position pos = new Position(r, c);
                if (board.getCell(pos).isMine()) {
                    Board newBoard = board.reveal(pos);
                    assertEquals(GameState.LOST, newBoard.getGameState());
                    return;
                }
            }
        }
    fail();
    }

    @Test
    void testRevealNonMine() {
        Board board = Board.create(3, 1);
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                Position pos = new Position(r, c);
                if (!board.getCell(pos).isMine()) {
                    Board newBoard = board.reveal(pos);
                    GameState state = newBoard.getGameState();
                    assertTrue(state == GameState.PLAYING || state == GameState.WON);
                    assertTrue(newBoard.getCell(pos).isRevealed());
                    return;
                }
            }
        }
        fail();
    }

    @Test
    void testWinCondition() {
        Board board = Board.create(2, 1);
        Board currentBoard = board;
        
        // Reveal all non-mine cells
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 2; c++) {
                Position pos = new Position(r, c);
                if (!currentBoard.getCell(pos).isMine()) {
                    currentBoard = currentBoard.reveal(pos);
                }
            }
        }
        
    assertEquals(GameState.WON, currentBoard.getGameState());
    }

    // Flagging tests removed; flagging is no longer supported in Board
}

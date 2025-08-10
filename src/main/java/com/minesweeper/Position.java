package com.minesweeper;

/**
 * Represents an immutable position on the game board.
 */
public record Position(int row, int col) {
    /**
     * Validates if the position is within the given bounds.
     * @param size The size of the board
     * @return true if the position is valid
     */
    public boolean isValid(int size) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    /**
     * Returns all valid adjacent positions (including diagonals).
     * @param size The size of the board
     * @return Array of valid adjacent positions
     */
    public Position[] getAdjacentPositions(int size) {
        Position[] allPositions = new Position[8];
        int index = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                Position newPos = new Position(row + dr, col + dc);
                if (newPos.isValid(size)) {
                    allPositions[index++] = newPos;
                }
            }
        }
        Position[] validPositions = new Position[index];
        System.arraycopy(allPositions, 0, validPositions, 0, index);
        return validPositions;
    }
}

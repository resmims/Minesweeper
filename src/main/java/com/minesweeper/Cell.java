package com.minesweeper;

/**
 * Represents an immutable cell in the Minesweeper game.
 * Uses the Builder pattern for construction and maintains immutable state.
 */
public class Cell {
    private final Position position;
    private final boolean isMine;
    private final boolean isRevealed;
    private final int adjacentMines;

    private Cell(Builder builder) {
        this.position = builder.position;
        this.isMine = builder.isMine;
        this.isRevealed = builder.isRevealed;
        this.adjacentMines = builder.adjacentMines;
    }

    public Position getPosition() { return position; }
    public boolean isMine() { return isMine; }
    public boolean isRevealed() { return isRevealed; }
    public int getAdjacentMines() { return adjacentMines; }

    /**
     * Creates a new Cell with revealed state set to true.
     * @return A new Cell instance with updated state
     */
    public Cell reveal() {
        return new Builder(this)
            .setRevealed(true)
            .build();
    }

   

    /**
     * Builder class for creating Cell instances.
     */
    public static class Builder {
        private final Position position;
        private boolean isMine;
        private boolean isRevealed;
        private int adjacentMines;

        public Builder(int row, int col) {
            this.position = new Position(row, col);
        }

        private Builder(Cell cell) {
            this.position = cell.position;
            this.isMine = cell.isMine;
            this.isRevealed = cell.isRevealed;
            this.adjacentMines = cell.adjacentMines;
        }

        public Builder setMine(boolean mine) {
            this.isMine = mine;
            return this;
        }

        public Builder setRevealed(boolean revealed) {
            this.isRevealed = revealed;
            return this;
        }



        public Builder setAdjacentMines(int count) {
            this.adjacentMines = count;
            return this;
        }

        public Cell build() {
            return new Cell(this);
        }
    }

    /**
     * Returns the display character for this cell based on its state.
     * @return char representation of the cell's current state
     */
    public char getDisplayChar() {
        if (!isRevealed) {
            return '_';
        }
        if (isMine) {
            return '*';
        }
        return Character.forDigit(adjacentMines, 10);
    }

    @Override
    public String toString() {
        return String.valueOf(getDisplayChar());
    }
}

package com.minesweeper;

/**
 * Represents the current state of the game.
 */
public enum GameState {
    PLAYING,
    WON,
    LOST;

    /**
     * Returns true if the game has ended (either won or lost).
     */
    public boolean isGameOver() {
        return this == WON || this == LOST;
    }
}

package com.minesweeper;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Manages the game flow and user interaction for Minesweeper.
 */
public class Game {
    public static final Pattern INPUT_PATTERN = Pattern.compile("^[A-Za-z][1-9][0-9]?$", Pattern.CASE_INSENSITIVE);
    private final Scanner scanner;

    public Game() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to Minesweeper!\n");
        while (true) {
            try {
                playOneGame();
                if (!promptPlayAgain()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Starting a new game...\n");
            }
        }
        scanner.close();
    }

    private void playOneGame() {
        int size = promptGridSize();
        int maxMines = (int) (size * size * 0.35);
        int mineCount = promptMineCount(maxMines);

        Board board = Board.create(size, mineCount);
        GameState state;

        do {
            System.out.println("\nHere is your minefield:");
            System.out.println(board);

            Position pos = null;
            while (true) {
                String input = promptRevealInput();
                try {
                    pos = parsePosition(input, size);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid position: " + e.getMessage());
                }
            }

            board = board.reveal(pos);
            if (board.getGameState() == GameState.PLAYING) {
                Cell cell = board.getCell(pos);
                System.out.println("This square contains " + cell.getAdjacentMines() + " adjacent mines.");
            }

            state = board.getGameState();
        } while (state == GameState.PLAYING);

        System.out.println("\nFinal board:");
        System.out.println(board);

        if (state == GameState.WON) {
            System.out.println("Congratulations! You've won the game!");
        } else {
            System.out.println("Oh no, you detonated a mine! Game over.");
        }
    }

    private int promptGridSize() {
        while (true) {
            System.out.print("Enter the size of the grid (2-26): ");
            try {
                int size = Integer.parseInt(scanner.nextLine().trim());
                if (size >= 2 && size <= 26) {
                    return size;
                }
                System.out.println("Size must be between 2 and 26.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private int promptMineCount(int maxMines) {
        while (true) {
            System.out.printf("Enter the number of mines (1-%d): ", maxMines);
            try {
                int mines = Integer.parseInt(scanner.nextLine().trim());
                if (mines >= 1 && mines <= maxMines) {
                    return mines;
                }
                System.out.println("Mine count must be between 1 and " + maxMines + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String promptRevealInput() {
        while (true) {
            System.out.print("Enter position to reveal (e.g., A1): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (!INPUT_PATTERN.matcher(input).matches()) {
                System.out.println("Invalid position format. Use letter followed by number (e.g., A1)");
                continue;
            }
            return input;
        }
    }

    public Position parsePosition(String input, int size) {
        int row = Character.toUpperCase(input.charAt(0)) - 'A';
        int col = Integer.parseInt(input.substring(1)) - 1;
        
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        
        return new Position(row, col);
    }

    private boolean promptPlayAgain() {
        while (true) {
            System.out.print("\nWould you like to play again? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("Please enter 'y' or 'n'");
        }
    }
}

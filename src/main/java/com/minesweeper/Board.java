package com.minesweeper;

import java.util.*;

/**
 * Represents the Minesweeper game board and its logic.
 */
public class Board {
	private static final int MAX_SIZE = 26; // A-Z
	private static final double MAX_MINE_PERCENTAGE = 0.35;

	private final int size;
	private final Cell[][] grid;
	private final GameState gameState;
	private final Random random;

	public Board(int size, Cell[][] grid, GameState gameState, Random random) {
		this.size = size;
		this.grid = grid;
		this.gameState = gameState;
		this.random = random;
	}

	/**
	 * Creates a new board with randomly placed mines.
	 */
	public static Board create(int size, int mineCount) {
		validateParameters(size, mineCount);
		Board board = new Board(size, new Cell[size][size], GameState.PLAYING, new Random());
		board.initializeGrid();
		board.placeMines(mineCount);
		board.calculateAdjacentMines();
		return board;
	}

	private static void validateParameters(int size, int mineCount) {
		if (size < 2 || size > MAX_SIZE) {
			throw new IllegalArgumentException(
				"Board size must be between 2 and " + MAX_SIZE);
		}
		int maxMines = (int) (size * size * MAX_MINE_PERCENTAGE);
		if (mineCount < 1 || mineCount > maxMines) {
			throw new IllegalArgumentException(
				"Mine count must be between 1 and " + maxMines);
		}
	}

	private void initializeGrid() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				grid[row][col] = new Cell.Builder(row, col).build();
			}
		}
	}

	private void placeMines(int mineCount) {
		Set<Position> minePositions = new HashSet<>();
		while (minePositions.size() < mineCount) {
			Position pos = new Position(random.nextInt(size), random.nextInt(size));
			if (minePositions.add(pos)) {
				grid[pos.row()][pos.col()] = new Cell.Builder(pos.row(), pos.col())
					.setMine(true)
					.build();
			}
		}
	}

	private void calculateAdjacentMines() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				Position pos = new Position(row, col);
				int count = countAdjacentMines(pos);
				grid[row][col] = new Cell.Builder(row, col)
					.setMine(grid[row][col].isMine())
					.setAdjacentMines(count)
					.build();
			}
		}
	}

	private int countAdjacentMines(Position pos) {
		int count = 0;
		Position[] positions = pos.getAdjacentPositions(size);
		for (Position p : positions) {
			if (grid[p.row()][p.col()].isMine()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Reveals a cell at the given position.
	 */
	public Board reveal(Position position) {
		if (!position.isValid(size) || gameState.isGameOver()) {
			return this;
		}

		Cell cell = grid[position.row()][position.col()];
		if (cell.isRevealed()) {
			return this;
		}

		Cell[][] newGrid = deepCopyGrid();
		newGrid[position.row()][position.col()] = cell.reveal();

		if (cell.isMine()) {
			return new Board(size, newGrid, GameState.LOST, random);
		}

		if (cell.getAdjacentMines() == 0) {
			revealAdjacentCells(position, newGrid);
		}

		GameState newState = isWon(newGrid) ? GameState.WON : GameState.PLAYING;
		return new Board(size, newGrid, newState, random);
	}

	private void revealAdjacentCells(Position position, Cell[][] grid) {
		Queue<Position> queue = new LinkedList<>();
		Set<Position> visited = new HashSet<>();
		queue.add(position);
		visited.add(position);

		while (!queue.isEmpty()) {
			Position current = queue.poll();
			for (Position adj : current.getAdjacentPositions(size)) {
				if (visited.contains(adj)) continue;
				Cell cell = grid[adj.row()][adj.col()];
				if (!cell.isRevealed() && !cell.isMine()) {
					grid[adj.row()][adj.col()] = cell.reveal();
					visited.add(adj);
					if (cell.getAdjacentMines() == 0) {
						queue.add(adj);
					}
				}
			}
		}
	}

	public Cell[][] deepCopyGrid() {
		Cell[][] newGrid = new Cell[size][size];
		for (int i = 0; i < size; i++) {
			System.arraycopy(grid[i], 0, newGrid[i], 0, size);
		}
		return newGrid;
	}

	private boolean isWon(Cell[][] grid) {
		for (Cell[] row : grid) {
			for (Cell col : row) {
				if (!col.isMine() && !col.isRevealed()) {
					return false;
				}
			}
		}
		return true;
	}

	public GameState getGameState() {
		return gameState;
	}

	public int getSize() {
		return size;
	}

	public Cell getCell(Position position) {
		return grid[position.row()][position.col()];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
        
		// Column headers
		sb.append("  ");
		for (int i = 1; i <= size; i++) {
			sb.append(i).append(" ");
		}
		sb.append("\n");

		// Rows
		for (int row = 0; row < size; row++) {
			sb.append((char) ('A' + row)).append(" ");
			for (int col = 0; col < size; col++) {
				sb.append(grid[row][col]).append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

Enviroment - Windows 
Language used - Java

**How to run the App ?**
  Run the Minesweeper.java class ,it will prompt the user to input the grid size and mine count. System will validate both inputs and diplay the error message if it is invalid .
  
  If inputs are valid ( eg : grid size =4 , minecount =3 ) system will create the grid and place mines at random places and diplay the game grid to the user and ask them to input their choice of the cell to be revealed through a command line interface. 


  user can input their choice in the format 'A1' (point to grid index 00),'A2' (point to grid index 01),'B1'(point to grid index 10) ,'B2' (point to grid index 11) etc 

  - If the selected cell contains a mine, the game is over and the user loses. 
  - Otherwise, the selected cell is uncovered and reveals a number indicating how many of its adjacent squares contain mines. 
  - If an uncovered cell has no adjacent mines, the program will automatically uncover all adjacent squares until it reaches squares that do have adjacent mines. 

- The game is won when all non-mine squares have been uncovered.

- updated grid will be diaplayed after each input .

 Assumptions :
   Grid  min size is 2 and max size 26.
   Grid is a square .

------------------------------------------------------------------
**Key Functions & Their Roles : 
**
1 . Board Creation
  
  a.  Validation
•	validateParameters(int size, int mineCount): Ensures the board size and mine count are within allowed limits.


  b.  Grid Initialization
•	initializeGrid(): Fills the grid with empty cells.


  c. Mine Placement
•	placeMines(int mineCount): Randomly places mines on the grid, ensuring no duplicates.


  d. Number Calculation
•	calculateAdjacentMines(): For each cell, counts how many mines are in adjacent cells and stores that number.


2 . Cell Reveal
•	reveal(Position position): Reveals a cell. If it’s a mine, the game is lost. If it’s empty (no adjacent mines), it auto-reveals all connected empty cells using queue based search algoritham.


3 . Auto-Reveal Algorithm 
•	revealAdjacentCells(Position position, Cell[][] grid): Uses a queue to reveal all connected empty cells (cells with zero adjacent mines) and their neighbors, stopping at cells next to mines.

4 . Win Check
•	isWon(Cell[][] grid): Checks if all non-mine cells are revealed, meaning the player has won.

------------------------------------------------------------------

** Utility Functions :
**
•	deepCopyGrid(): Makes a copy of the grid for safe updates.
•	getGameState(), getSize(), getCell(Position position): Get current game state, board size, or a specific cell.
•	toString(): Returns a string representation of the board for display.

------------------------------------------------------------------

**Algorithms Used : 
**
•	Uses a Set to avoid duplicate mine positions.
•	Randomly selects positions until the required number of mines is placed.
queue based search for Auto-Reveal
•	When an empty cell is revealed, queue based search algorithm  is used to reveal all connected empty cells and their neighbors.
•	Queue keeps track of cells to process, and a set tracks visited cells to avoid repeats
------------------------------------------------------------------

** Game Logic :
**
•	Revealing a mine ends the game (GameState.LOST).
•	Revealing all non-mine cells wins the game (GameState.WON).
•	Revealing an empty cell may auto-reveal a region of empty cells.

------------------------------------------------------------------
**Design Principles :
**
•	Immutability: The board and cells are not changed directly; instead, new objects are created for each update.
•	Separation of Concerns: Each function has a clear, single responsibility.

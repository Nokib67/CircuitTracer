import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 * 
 * @author mvail, Nokib Hossain
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	// constants you may find useful
	private final int ROWS; // initialized in constructor
	private final int COLS; // initialized in constructor
	private final char OPEN = 'O'; // capital 'o', an open position
	private final char CLOSED = 'X';// a blocked position
	private final char TRACE = 'T'; // part of the trace connecting 1 to 2
	private final char START = '1'; // the starting component
	private final char END = '2'; // the ending component
	private final String ALLOWED_CHARS = "OXT12"; // useful for validating with indexOf

	/**
	 * Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 * 'O' an open position
	 * 'X' an occupied, unavailable position
	 * '1' first of two components needing to be connected
	 * '2' second of two components needing to be connected
	 * 'T' is not expected in input files - represents part of the trace
	 * connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 *                 file containing a grid of characters
	 * @throws FileNotFoundException      if Scanner cannot open or read the file
	 * @throws InvalidFileFormatException for any file formatting or content issue
	 */
	public CircuitBoard(String filename) throws FileNotFoundException, InvalidFileFormatException {

		try (Scanner fileScan = new Scanner(new File(filename))) {

			// Checks if the file is empty
			if (!fileScan.hasNextLine()) {
				throw new InvalidFileFormatException("File is empty.");
			}

			String firstLine = fileScan.nextLine();
			try (Scanner lineScanner = new Scanner(firstLine)) {

				// Checks if there is row int
				if (!lineScanner.hasNextInt()) {
					throw new InvalidFileFormatException("Invalid matrix format: ROWS is missing or non-integer.");
				}

				ROWS = lineScanner.nextInt();

				// Checks if there is col int
				if (!lineScanner.hasNextInt()) {
					throw new InvalidFileFormatException("Invalid matrix format: COLS is missing or non-integer.");
				}

				COLS = lineScanner.nextInt();

				// checks if there is additional thing
				if (lineScanner.hasNext()) {
					throw new InvalidFileFormatException("Invalid matrix format: Extra data in dimensions line.");
				}
			}
			this.board = new char[ROWS][COLS];

			// Iterate through the file to read matrix elements
			for (int i = 0; i < ROWS; i++) {

				// checks if there is less rows than that is mentioned
				if (!fileScan.hasNextLine()) {
					throw new InvalidFileFormatException("Fewer rows than expected.");
				}

				try (Scanner lineScanner = new Scanner(fileScan.nextLine())) {
					for (int j = 0; j < COLS; j++) {

						// Checks if there is less coloms in a particular row
						if (!lineScanner.hasNext()) {
							throw new InvalidFileFormatException(
									"Row " + (i + 1) + " has fewer columns than expected.");
						}

						String point = lineScanner.next();
						if (!ALLOWED_CHARS.contains(point)) {
							throw new InvalidFileFormatException(
									point + " is a invalid Char in row: " + i + " col :" + j);
						}
						board[i][j] = point.charAt(0);

						if (point.charAt(0) == START) {
							// Checking if there is multiple 1
							if (startingPoint != null) {
								throw new InvalidFileFormatException("File has multiple starting points");
							}
							startingPoint = new Point(i, j);
						}

						if (point.charAt(0) == END) {
							// Check if there is multiple 2
							if (endingPoint != null) {
								throw new InvalidFileFormatException("File has multiple ending points");
							}
							endingPoint = new Point(i, j);
						}

					}

					// Checks if there is more colums
					if (lineScanner.hasNext()) {
						throw new InvalidFileFormatException("Row " + (i + 1) + " has more columns than expected.");
					}
				}
			}

			//checks if there is staring or ending missing
			if(startingPoint == null || endingPoint == null)
			{
				throw new InvalidFileFormatException("There is no starting or endning");
			}

			// checks if there is more rows
			if (fileScan.hasNextLine()) {
				throw new InvalidFileFormatException("More rows than expected.");
			}
		}
	}

	/**
	 * Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/**
	 * Utility method for copy constructor
	 * 
	 * @return copy of board array
	 */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}

	/**
	 * Return the char at board position x,y
	 * 
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Return whether given board position is open
	 * 
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}

	/**
	 * Set given position to be a 'T'
	 * 
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}

	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}

	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}

	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}

	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}

}// class CircuitBoard

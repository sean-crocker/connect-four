/**
 * Class:			Board
 * Purpose:			Used to simulate a game board of Connect Four. The game board contains six
 * 					rows and seven columns. When four markers from a player are together in a sequence
 * 					either horizontally, vertically, or diagonally, the player wins. The board is represented
 * 					by a two-dimensional array of integers where:
 * 						 0	=	Empty Space;
 * 						 1	=	The player's marker;
 * 						-1	=	The opponent's marker.
 * Student Name:	Sean Crocker
 * Student Number:	3307768
 */
public class Board {
	private int[][] board;		//The 2D array representing the board
	
	/**
	 * Default Constructor used to initialize the two-dimensional array of integers.
	 */
	public Board() {
		this.board = new int[6][7];
	}
	
	/**
	 * A copy constructor used to initialize the two-dimensional array and copy the contents
	 * from another game board's array.
	 * Parameters:		@param gameBoard the object being copied
	 */
	public Board(Board gameBoard) {
		this.board = new int[6][7];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++)
				this.board[i][j] = gameBoard.board[i][j];
		}
	}
	
	/**
	 * Method update
	 * Purpose:			Used to update the game board as if a player made a move. The column
	 * 					and the marker are given. The marker is placed until the row at the position
	 * 					that does not contain another marker and is closest to the bottom.
	 * Postcondition:	The game board is updated with the marker placed.
	 * @param move		The column where the marker should be dropped.
	 * @param turn		1 = player's turn. -1 = opponent's turn.
	 */
	public void update(int move, int turn) {
		for (int i = board.length - 1; i >= 0; i--) {
			if (board[i][move] == 0) {
				board[i][move] = turn;
				break;
			}
		}
	}
	
	/**
	 * Method hasFinished
	 * Purpose:			Used to determine if the game board is in a finished state. This is
	 * 					deemed true if the player or the opponent has won the game or if the
	 * 					game board has no empty spaces, resulting in a draw.
	 * Postcondition:	Returns true if a player has won, lost, or drew.
	 * Return:			@return true if game has finished
	 */
	public boolean hasFinished() {
		if (hasWon(1) || hasWon(-1))
			return true;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if(board[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Method isIllegal
	 * Purpose:			Used to determine whether placing a marker in a column would be allowed
	 * 					or if the current column is full.
	 * Postcondition:	If the column is full, return true, else false.
	 * Parameters:		@param column the column tested
	 * Return:			@return true if every row in the column specified has a marker in it.
	 */
	public boolean isIllegal(int column) {
		if(board[0][column] == 0)
			return false;
		return true;
	}

	/**
	 * Method hasWon
	 * Purpose:			Used to determine if a player has won by checking for four markers in a sequence.
	 * Postcondition:	Returns true if four markers are in a sequence horizontally, vertically or diagonally.
	 * Parameters:		@param turn 1 = player's marker. -1 = opponent's marker
	 * Return:			@return true if four markers are found in sequence.
	 */
	public boolean hasWon(int turn) {
		//Checks for 4 together horizontally
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 3; j++) {
				if (board[i][j] == turn && board[i][j+1] == turn && board[i][j+2] == turn && board[i][j+3] == turn)
					return true;
			}
		}
		//Checks for 4 together vertically
		for (int i = 0; i < board.length - 3; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == turn && board[i+1][j] == turn && board[i+2][j] == turn && board[i+3][j] == turn)
					return true;
			}
		}
		//Checks for 4 together diagonally with a positive slope
		for (int i = 3; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 3; j++) {
				if (board[i][j] == turn && board[i-1][j+1] == turn && board[i-2][j+2] == turn &&  board[i-3][j+3] == turn)
					return true;
			}
		}
		//Checks for 4 together diagonally with a negative slope
		for (int i = 3; i < board.length; i++) {
			for (int j = 3; j < board[i].length; j++) {
				if (board[i][j] == turn && board[i-1][j-1] == turn && board[i-2][j-2] == turn && board[i-3][j-3] == turn)
					return true;
			}
		}
		return false;
	}

	/**
	 * Method threeTogetherCount
	 * Purpose:			Used to count how many times a player has three markers in a sequence.
	 * Postcondition:	Counts the amount of times a player has three markers together horizontally,
	 * 					vertically, and diagonally.
	 * Parameters:		@param turn 1 = player's marker. -1 = opponent's marker
	 * Return:			@return the amount of times a player has three markers together
	 */
	public int threeTogetherCount(int turn) {
		int count = 0;
		//Checks for 3 together horizontally
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 3; j++) {
				if (board[i][j] == turn && board[i][j+1] == turn && board[i][j+2] == turn)
					count++;
			}
		}
		//Checks for 3 together vertically
		for (int i = 0; i < board.length - 3; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == turn && board[i+1][j] == turn && board[i+2][j] == turn)
					count++;
			}
		}
		//Checks for 3 together diagonally with a positive slope
		for (int i = 3; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 3; j++) {
				if (board[i][j] == turn && board[i-1][j+1] == turn && board[i-2][j+2] == turn)
					count++;
			}
		}
		//Checks for 3 together diagonally with a negative slope
		for (int i = 3; i < board.length; i++) {
			for (int j = 3; j < board[i].length; j++) {
				if (board[i][j] == turn && board[i-1][j-1] == turn && board[i-2][j-2] == turn)
					count++;
			}
		}
		return count;
	}

	/**
	 * Method twoTogetherCount
	 * Purpose:			Used to count how many times a player has two markers in a sequence.
	 * Postcondition:	Counts the amount of times a player has tow markers together horizontally,
	 * 					vertically, and diagonally.
	 * Parameters:		@param turn 1 = player's marker. -1 = opponent's marker
	 * Return:			@return the amount of times a player has two markers together
	 */
	public int twoTogetherCount(int turn) {
		int count = 0;
		//Checks for 2 together horizontally
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 3; j++) {
				if (board[i][j] == turn && board[i][j+1] == turn)
					count++;
			}
		}
		//Checks for 2 together vertically
		for (int i = 0; i < board.length - 3; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == turn && board[i+1][j] == turn)
					count++;
			}
		}
		//Checks for 2 together diagonally with a positive slope
		for (int i = 3; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 3; j++) {
				if (board[i][j] == turn && board[i-1][j+1] == turn)
					count++;
			}
		}
		//Checks for 2 together diagonally with a negative slope
		for (int i = 3; i < board.length; i++) {
			for (int j = 3; j < board[i].length; j++) {
				if (board[i][j] == turn && board[i-1][j-1] == turn)
					count++;
			}
		}
		return count;
	}
}
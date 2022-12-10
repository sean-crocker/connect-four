/**
 * Class:			Engine
 * Purpose:			Used to store the current state of the board and retrieve any relevant information
 * 					for responding to the coordinator.
 * Student Name:	Sean Crocker
 * Student Number:	3307768
 */
public class Engine {
	private String name;		//The name of the engine
	private Board board;		//The game board used for the connect four game
	private Tree gameTree;		//The game tree used for searching and performance testing
	
	/**
	 * Default constructor used to initialize all global variables.
	 */
	public Engine() {
		this.name = "testEngine-c3307768";
		this.board = new Board();
		this.gameTree = null;
	}

	/**
	 * Method getName
	 * Purpose:			Used to return the name of the engine.
	 * Postcondition:	The name of the engine is returned.
	 * Return:			@return engine's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method updateBoard
	 * Purpose:			Used to update the board with the column given.
	 * Postcondition:	Calls the board's function update to update the the game's board with
	 * 					the given column and player's turn.
	 * Parameters:		@param column the position to place the marker
	 * 					@param turn 1 = player's turn. -1 = opponent's turn
	 */
	public void updateBoard(int column, int turn) {
		board.update(column, turn);
	}
	
	/**
	 * Method bestMove
	 * Purpose:			Used to create a game tree and call it's best move function to return
	 * 					the column the next marker would be best in. It then updates the game board
	 * 					with the best move and returns the column.
	 * Postcondition:	The game tree is created and the searching algorithm is used to determine the
	 * 					best column to place the next marker in. The board is updated and the column number
	 * 					is returned to the coordinator.
	 * Return:			@return the best move possible
	 */
	public int bestMove() {
		this.gameTree = new Tree(board, 7);
		int column = gameTree.bestMove();
		board.update(column, 1);
		return column;
	}
	
	/**
	 * Method getScore
	 * Purpose:			Used to call the game tree to retrieve the value of an evaluation function
	 * 					after a move.
	 * Precondition:	The Tree collection must have been created and the best move function must have been
	 * 					called.
	 * Postcondition:	The evaluation function after a move is returned.
	 * Return:			@return the evaluation function
	 */
	public int getScore() {
		return gameTree.getBestScore();
	}

	/**
	 * Method perftStart
	 * Purpose:			Creates a new game tree with the given depth and calls the tree's perft function
	 * 					to return the number of nodes in the tree at its current position.
	 * Postcondition:	Returns the amount of nodes in the tree. 
	 * Parameters:		@param depth the depth the game tree is expanded to
	 * Return:			@return the number of nodes in the game tree
	 */
	public int perftStart(int depth) {
		this.gameTree = new Tree(board, depth);
		return gameTree.perftFunction(depth);
	}
}
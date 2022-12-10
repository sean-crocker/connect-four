/**
 * Class:			Tree
 * Purpose:			Used as a game tree to store all possible options that can be played by both the
 * 					opponent and the player.
 * Student Name:	Sean Crocker
 * Student Number:	3307768
 */
public class Tree {
	private Node root;			//The root of the tree
	private int depth;			//The depth of the tree
	private int bestScore;		//The value of an evaluation function after each move
		
	/**
	 * Constructor with parameters to initialize all global variables and create a game tree.
	 * Parameters:		@param board the board being used as the state of the root of the tree.
	 * 					@param depth the depth the tree is expanded to
	 */
	public Tree(Board board, int depth) {
		this.root = new Node(new Board(board));
		this.depth = depth;
		this.bestScore = 0;
		createTree(root, depth, true);
	}
	
	/**
	 * Method createTree
	 * Purpose:			Used to initialize the game tree by creating all possible children for each node.
	 * 					Uses recursion to create many subtrees.
	 * Postcondition:	The game tree is created with all possible moves that can be made up to the given
	 * 					the depth.
	 * Parameters:		@param node the parent node
	 *					@param depth the remaining depth of the tree
	 * 					@param maximisingPlayer	true if it is the player's turn
	 */
	private void createTree(Node node, int depth, boolean maximisingPlayer) {
		if (depth == 0 || node.getState().hasFinished())
			return;
		if (maximisingPlayer) {
			for (int i = 0; i < 7; i++) {
				Board temp = new Board(node.getState());
				if (!temp.isIllegal(i)) {
					temp.update(i, 1);
					Node child = new Node(temp);
					node.getChildren().add(child);
					createTree(child, depth - 1, false);
				}
			}
		}
		else {
			for (int i = 0; i < 7; i++) {
				Board temp = new Board(node.getState());
				if (!temp.isIllegal(i)) {
					temp.update(i, -1);
					Node child = new Node(temp);
					node.getChildren().add(child);
					createTree(child, depth - 1, true);
				}
			}
		}
	}
	
	/**
	 * Method bestMove
	 * Purpose:			Used to retrieve the column number that the next marker should be placed in.
	 * 					This function serves as the helper function for the initial call to the minimax
	 * 					algorithm.
	 * Postcondition:	Returns the column the next marker would be best in. Also sets the value of
	 * 					the evaluation heuristic.
	 * Return:			@return the best move
	 */
	public int bestMove() {
		int column = 0;
		int bestValue = Integer.MIN_VALUE;
		for (int i = 0; i < root.getChildren().size(); i++) {
			int temp = minimaxAB(root.getChildren().get(i), depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			if (temp > bestValue && !root.getChildren().get(i).getState().isIllegal(i)) {
				bestValue = temp;
				column = i;
			}
		}
		this.bestScore = bestValue;
		return column;
	}

	/**
	 * Method minimaxAB
	 * Purpose:			Backtracking algorithm called minimax used to select the optimal move. It minimizes
	 * 					the possible loss for a worst case scenario. This is done by having the player
	 * 					(maximizer) gathering the highest score possible while the opponent (minimizer)
	 * 					gathers the lowest score possible. An optimizing technique called alpha beta pruning
	 * 					is used on the minimax algorithm so that no time is wasted searching for nodes that
	 * 					already have a better and known alternative.
	 * Postcondition:	Uses backtracking to return the evaluation function of all leaf nodes in the tree
	 * 					and then return the highest score for the maximizer and lowest for the minimizer.
	 * 					best value
	 * Parameters:		@param node the current node in the tree
	 * 					@param depth the current depth of the tree
	 * 					@param alpha the best value that the maximizer can guarantee
	 * 					@param beta the best value that the minimizer can guarantee
	 * 					@param maximisingPlayer true if it is the maximizer (player), false if minimizer (opponent)
	 * Return			@return the optimal move
	 */
	private int minimaxAB(Node node, int depth, int alpha, int beta, boolean maximisingPlayer) {
		if (depth == 0 || node.getState().hasFinished()) 
			return node.evaluationFunction();
		if (maximisingPlayer) {						//Trying to maximize
			int value = Integer.MIN_VALUE;
			for (int i = 0; i < node.getChildren().size(); i++) {
				value = Math.max(value, minimaxAB(node.getChildren().get(i), depth - 1, alpha, beta, false));
				alpha = Math.max(alpha, value);
				if (alpha >= beta) 					//Cut off branch 
					break;
			}
			return value;
		}
		else {										//Trying to minimize
			int value = Integer.MAX_VALUE;
			for (int i = 0; i < node.getChildren().size(); i++) {
				value = Math.min(value, minimaxAB(node.getChildren().get(i), depth - 1, alpha, beta, true));
				beta = Math.min(beta, value);
				if (alpha >= beta)					//Cut off branch
					break;
			}
			return value;
		}
	}

	/**
	 * Method getBestScore
	 * Purpose:			Used to return the value of the evaluation heuristic after a move
	 * Postcondition:	Return the current evaluation heuristic
	 * Return:			@return the value from the evaluation function
	 */
	public int getBestScore() {
		return bestScore;
	}

	/**
	 * Method perftFunction
	 * Purpose:			Used as a helper function to the perft function to calculate the performance of the
	 * 					the game tree. Calls the perft function with the root node and the depth given and
	 * 					returns the result.
	 * Precondition:	The perft argument with the associated depth must be stated in the arguments
	 * 					when running the coordinator.
	 * Postcondition:	Calls the perft function with the root of the tree to calculate how many nodes are
	 * 					in the tree.
	 * Parameters:		@param depth the depth the search is expanded to
	 * Return:			@return the number of nodes in the game tree from a certain position
	 */
	public int perftFunction(int depth) {
		if (depth <= 0 || root.getState().hasFinished())
			return 0;
		return perft(root, depth);
	}
	
	/**
	 * Method perft
	 * Purpose:			A performance testing algorithm used to count the number of nodes that are in
	 * 					a game tree when expanded to a certain depth from a current position.
	 * Precondition:	The perftFunction helper algorithm must initiate the function and the depth must
	 * 					not equal or be less than zero.
	 * Postcondition:	Uses recursion to count the nodes at every all depths.
	 * Parameters:		@param node the current node in the tree
	 * 					@param depth the remaining depth of the tree
	 * Return:			@return the number of nodes in the tree.
	 */
	public int perft(Node node, int depth) {
		int count = 0;
		if (depth == 0 || node.getState().hasFinished())
			return 1;
		for (int i = 0; i < node.getChildren().size(); i++) 
			count += perft(node.getChildren().get(i), depth - 1);
		return count;
	}
}
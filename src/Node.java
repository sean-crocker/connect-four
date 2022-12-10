import java.util.ArrayList;

/**
 * Class:			Node
 * Purpose:			Used as a node in a game tree representing one of the many options that a player can
 * 					move.
 * Student Name:	Sean Crocker
 * Student Number:	3307768
 */
public class Node {
	private Board state;					//The board stored in the node
	private ArrayList<Node> children;		//The nodes children
	
	/**
	 * Constructor with a parameter to initialize all global variables.
	 * Parameters:		@param board the state of the board
	 */
	public Node(Board board) {
		this.state = board;
		this.children = new ArrayList<Node>();
	}

	/**
	 * Method getState
	 * Purpose:			Used to return the board when it is in this state.
	 * Postcondition:	Returns the board object
	 * Return:			@return the board
	 */
	public Board getState() {
		return state;
	}

	/**
	 * Method getChildren
	 * Purpose:			Used to return the collection of nodes that are acting as
	 * 					the node's children.
	 * Postcondition:	Returns the collection of nodes.
	 * Return:			@return the node's children
	 */
	public ArrayList<Node> getChildren() {
		return children;
	}
		
	/**
	 * Method evaluationFunction
	 * Purpose:			A basic evaluation function for the node. It is used to return the value
	 * 					of the given state. If the state leads to the player winning, the score of the
	 * 					player is granted a large number (100). If the state leads to the opponent winning/
	 * 					player losing, the score of the opponent is granted a large number (100).
	 * 					The function then counts the amount of times both the player and opponent have
	 * 					three and/or two markers in sequence and adds it to their score. The value of the
	 * 					opponent's score is taken away from the player's score and is returned. If
	 * 					the value of zero is returned, the game is a draw. If the value is negative,
	 * 					the opponent has the upper hand whereas if the value is positive, the player has
	 * 					the upper hand.
	 * Postcondition:	The evaluation heuristic is returned and used for a Minimax search algorithm. 
	 * Return:			@return the evaluation heuristic
	 */
	public int evaluationFunction() {
		int playerOneScore = 0;
		int playerTwoScore = 0;
		if (state.hasWon(1) || state.hasWon(-1)) {
			if (state.hasWon(1)) 
				playerOneScore = 100;
			else 
				playerTwoScore = 100;
		}
		playerOneScore += state.threeTogetherCount(1) * 10 + state.twoTogetherCount(1);
		playerTwoScore += state.threeTogetherCount(-1) * 10 + state.twoTogetherCount(-1);
		return playerOneScore - playerTwoScore;
		
	}
}
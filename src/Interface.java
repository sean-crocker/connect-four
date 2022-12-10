import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class:			Interface
 * Purpose:			Driver class to act as an interface between the engine and the coordinator.
 * 					It reads commands from the coordinator and sends them to the engine. The interface
 * 					parses the inputs received before sending them to the engine.
 * Student Name:	Sean Crocker
 * Student Number:	3307768
 */
public class Interface {	
	
	/**
	 * Method main
	 * Purpose:			Main method that runs when the program begins.
	 * Precondition:	The coordinator.jar file must initiate the program.
	 * Postcondition:	Runs the method read to read the inputs from the coordinator.
	 */
	public static void main(String[] args) {
		Interface main = new Interface();
		main.read();
	}
	
	/**
	 * Method read
	 * Purpose:			Used to create an engine and read commands received from the coordinator.
	 * 					Each command responds with a reply from the engine. Each line is read until the
	 * 					quit command is received.
	 * Postcondition:	The program stops when the quit command is received.
	 */
	private void read() {
		Engine engine = new Engine();
		Scanner scanner = new Scanner(System.in);
		String line;
		while ((line = scanner.nextLine()) != null) {
			if (line.contains("name"))						//Send name to coordinator
				System.out.println(engine.getName());
			else if (line.contains("isready"))				//Reply to coordinator when ready
				System.out.println("readyok");
			//Update the connect 4 game with the opponent's move.
			else if (line.contains("position")) {
				String move = line.substring(line.length() - 1);
				Pattern pattern = Pattern.compile("[0-6]$");
				Matcher matcher = pattern.matcher(move);
				if (matcher.find()) 
					engine.updateBoard(Integer.parseInt(move), - 1);
			}
			//Reply with the best move the engine can make and the value of the evaluation function
			//after that move.
			else if (line.contains("go")) 
				System.out.println("bestmove "+engine.bestMove()+" "+engine.getScore());
			//Tells the engine to count how many nodes are in the game tree up to the depth given from the
			//current position.
			else if (line.contains("perft")) {
				String depth = line.substring(line.length() - 2);
				Pattern pattern = Pattern.compile("[^0-9]+([0-9]+)$");
				Matcher matcher = pattern.matcher(depth);
				if (matcher.find()) 
					System.out.println(engine.perftStart(Integer.parseInt(matcher.group(1))));						
			}
			else if (line.contains("quit"))					//Tells the engine to exit
				System.out.println("quitting");
			else
				continue;
		}
		scanner.close();
	}
}
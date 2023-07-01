package tp1.p2.control;

import static tp1.p2.view.Messages.debug;

import java.io.IOException;
import java.util.Scanner;

import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.Game;
import tp1.p2.view.GamePrinter;
import tp1.p2.view.Messages;

/**
 * Accepts user input and coordinates the game execution logic.
 */
public class Controller {

	private Game game;

	private Scanner scanner;

	private GamePrinter gamePrinter;

	public Controller(Game game, Scanner scanner) {
		this.game = game;
		this.scanner = scanner;
		this.gamePrinter = new GamePrinter(game);
	}

	/**
	 * Draw / Paint the game.
	 */
	private void printGame() {
		System.out.println(gamePrinter);
	}

	/**
	 * Prints the final message once the match is finished.
	 */
	public void printEndMessage() {
		System.out.println(gamePrinter.endMessage());
	}

	/**
	 * Show prompt and request command.
	 *
	 * @return the player command as words
	 */
	private String[] prompt() {
		System.out.print(Messages.PROMPT);
		String line = scanner.nextLine();
		String[] words = line.toLowerCase().trim().split("\\s+");

		System.out.println(debug(line));

		return words;
	}

	/**
	 * Runs the game logic.
	 */
	public void run() {
		boolean refreshDisplay = true;

		while (!game.hasFoundWinner() && !game.doesPlayerQuit()) {
			// 1. Draw
			if (refreshDisplay) {
				printGame();
			}

			// 2. User action
			String[] words = prompt();
			try {
				refreshDisplay = false;
				Command command = Command.parse(words);
				refreshDisplay = game.execute(command);
			} catch (GameException g) {
				System.out.println(g.getMessage());
				if (g.getCause() != null) {
					System.out.println("[DEBUG] Cause: " + g.getCause().getMessage());
				}
			}
		}

		if (refreshDisplay) {
			printGame();
		}

		try {
			game.saveRecord();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			return;
		}

		printEndMessage();

	}

}

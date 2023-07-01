package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.NotCatchablePositionException;
import tp1.p2.logic.Game;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class CatchCommand extends Command {

	private static boolean caughtSunThisCycle;

	private int col;

	private int row;

	public CatchCommand() {
		caughtSunThisCycle = false;
	}

	@Override
	protected void newCycleStarted() {
		caughtSunThisCycle = false;
	}

	public CatchCommand(int col, int row) {
		this.col = col;
		this.row = row;
		caughtSunThisCycle = false;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_CATCH_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CATCH_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_CATCH_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_CATCH_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		// TODO add your code here
		try {
			if (caughtSunThisCycle) {
				throw new NotCatchablePositionException(error(Messages.SUN_ALREADY_CAUGHT));
			}

			boolean sunCaught = game.catchSun(game, col, row);

			caughtSunThisCycle = true;
			return true;
		} catch (NotCatchablePositionException e) {
			throw new CommandExecuteException(e);
		}
	}

	@Override
	public Command create(String[] parameters) throws GameException {
		try {
			int inputCol, inputRow;
			try {
				inputCol = Integer.parseInt(parameters[1]);
				inputRow = Integer.parseInt(parameters[2]);
			} catch (NumberFormatException e) {
				throw new InvalidPositionException(error(Messages.INVALID_PARAMATER_TYPE));
			}

			if (inputRow >= Game.NUM_ROWS || inputRow < 0) {
				throw new InvalidPositionException(error(Messages.INVALID_POSITION
						.formatted(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]))));
			}

			if (inputCol >= Game.NUM_COLS || inputCol < 0) {
				throw new InvalidPositionException(error(Messages.INVALID_POSITION
						.formatted(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]))));
			}

			return new CatchCommand(inputCol, inputRow);
		} catch (InvalidPositionException ipe) {
			throw new CommandParseException(ipe);
		}
	}

}

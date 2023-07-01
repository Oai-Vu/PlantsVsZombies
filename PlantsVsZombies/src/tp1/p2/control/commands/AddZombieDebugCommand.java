package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.logic.Game;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class AddZombieDebugCommand extends Command {

	private int zombieIdx;

	private int col;

	private int row;

	public AddZombieDebugCommand() {

	}

	private AddZombieDebugCommand(int zombieIdx, int col, int row) {
		this.zombieIdx = zombieIdx;
		this.col = col;
		this.row = row;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_ZOMBIE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_ZOMBIE_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_ZOMBIE_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_ZOMBIE_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		try {
			boolean addingResult = game.cheatNewZombie(zombieIdx, game, col, row);
			if (addingResult) {
				game.update();
			}
			return addingResult;
		} catch (InvalidPositionException ipe) {
			throw new CommandExecuteException(ipe);
		}
	}

	@Override
	public Command create(String[] parameters) throws GameException {
		try {
			if (parameters[0].equalsIgnoreCase(getName()) || parameters[0].equalsIgnoreCase(getShortcut())) {
				if (parameters.length != 4) {
					throw new CommandParseException(error(Messages.COMMAND_PARAMETERS_MISSING));
				}

				int index, inputCol, inputRow;
				try {
					index = Integer.parseInt(parameters[1]);
				} catch (NumberFormatException e) {
					throw new CommandParseException(error(Messages.INVALID_GAME_OBJECT));
				}

				try {
					inputCol = Integer.parseInt(parameters[2]);
					inputRow = Integer.parseInt(parameters[3]);
				} catch (NumberFormatException e) {
					throw new InvalidPositionException(error(Messages.INVALID_PARAMATER_TYPE));
				}

				if (inputRow > Game.NUM_ROWS || inputRow < 0) {
					throw new InvalidPositionException(error(Messages.INVALID_POSITION
							.formatted(Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]))));
				}

				if (inputCol > Game.NUM_COLS || inputCol < 0) {
					throw new InvalidPositionException(error(Messages.INVALID_POSITION
							.formatted(Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]))));
				}
				return new AddZombieDebugCommand(index, inputCol, inputRow);
			}
			throw new CommandParseException(error(Messages.UNKNOWN_COMMAND));
		} catch (InvalidPositionException ipe) {
			throw new CommandParseException(ipe);
		}
	}

}

package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.control.Level;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class ResetCommand extends Command {

	private Level level;

	private long seed;

	public ResetCommand() {
	}

	public ResetCommand(Level newLevel) {
		level = newLevel;
		seed = 0;
	}

	public ResetCommand(Level newLevel, long newSeed) {
		level = newLevel;
		seed = newSeed;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_RESET_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_RESET_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_RESET_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_RESET_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		// TODO add your code here
		if (level != null && seed != 0) {
			game.restart(level, seed);
		} else if (level != null && seed == 0) {
			game.restart(level);
		} else {
			game.restart();
		}
		return true;

	}

	@Override
	public Command create(String[] parameters) throws GameException {
		// TODO add your code here
		if (parameters.length == 2) {
			Level level = Level.valueOfIgnoreCase(parameters[1]);
			if (level == null) {
				throw new CommandParseException(error(Messages.ALLOWED_LEVELS));
			}
			return new ResetCommand(level);
		} else if (parameters.length == 3) {
			Level level = Level.valueOfIgnoreCase(parameters[1]);
			if (level == null) {
				throw new CommandParseException(error(Messages.ALLOWED_LEVELS));
			}

			try {
				seed = Long.parseLong(parameters[2]);
			} catch (NumberFormatException nfe) {
				throw new CommandParseException(String.format(Messages.SEED_NOT_A_NUMBER_ERROR, seed));
			}
			return new ResetCommand(level, seed);
		} else if (parameters.length != 1) {
			throw new CommandParseException(error(Messages.INVALID_COMMAND));
		}

		return new ResetCommand();
	}
}

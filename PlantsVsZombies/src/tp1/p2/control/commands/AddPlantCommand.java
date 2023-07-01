package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.NotEnoughCoinsException;
import tp1.p2.logic.Game;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.view.Messages;

public class AddPlantCommand extends Command implements Cloneable {

	private int col;

	private int row;

	private String plantName;

	private boolean consumeCoins;

	public AddPlantCommand() {
		this(true);
	}

	public AddPlantCommand(boolean consumeCoins) {
		this.consumeCoins = consumeCoins;
	}

	public AddPlantCommand(String addedPlant, int commandCol, int commandRow) {
		this();
		plantName = addedPlant;
		row = commandRow;
		col = commandCol;
	}

	public AddPlantCommand(String addedPlant, int commandCol, int commandRow, boolean consumeCoins) {
		this(consumeCoins);
		plantName = addedPlant;
		row = commandRow;
		col = commandCol;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		try {
			boolean addingResult = game.addNewPlant(plantName, game, col, row, consumeCoins);
			if (addingResult) {
				game.update();
			}
			return addingResult;
		} catch (NotEnoughCoinsException nce) {
			throw new CommandExecuteException(nce);
		} catch (InvalidPositionException ipe) {
			throw new CommandExecuteException(ipe);
		}

	}

	@Override
	public Command create(String[] parameters) throws GameException {
		try {
			if (parameters.length != 4) {
				throw new CommandParseException(error(Messages.COMMAND_PARAMETERS_MISSING));
			}

			String plantName = PlantFactory.isValidPlant(parameters[1]);

			int inputCol, inputRow;

			try {
				inputCol = Integer.parseInt(parameters[2]);
				inputRow = Integer.parseInt(parameters[3]);
			} catch (NumberFormatException e) {
				throw new InvalidPositionException(error(Messages.INVALID_PARAMATER_TYPE));
			}

			if (inputRow >= Game.NUM_ROWS || inputRow < 0) {
				throw new InvalidPositionException(error(Messages.INVALID_POSITION
						.formatted(Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]))));
			}

			if (inputCol >= Game.NUM_COLS || inputCol < 0) {
				throw new InvalidPositionException(error(Messages.INVALID_POSITION
						.formatted(Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]))));
			}

			return new AddPlantCommand(plantName, inputCol, inputRow);
		} catch (InvalidPositionException ipe) {
			throw new CommandParseException(ipe);
		}
	}

}

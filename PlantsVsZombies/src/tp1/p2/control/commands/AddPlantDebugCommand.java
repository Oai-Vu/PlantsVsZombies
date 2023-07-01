package tp1.p2.control.commands;

import static tp1.p2.view.Messages.error;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.logic.Game;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.view.Messages;

public class AddPlantDebugCommand extends AddPlantCommand implements Cloneable {

	public AddPlantDebugCommand() {
		super(false);
	}

	public AddPlantDebugCommand(String addedPlant, int commandCol, int commandRow) {
		super(addedPlant, commandCol, commandRow, false);
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_CHEAT_PLANT_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CHEAT_PLANT_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_CHEAT_PLANT_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_CHEAT_PLANT_HELP;
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

			return new AddPlantDebugCommand(plantName, inputCol, inputRow);
		} catch (InvalidPositionException ipe) {
			throw new CommandParseException(ipe);
		}
	}
}

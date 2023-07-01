package tp1.p2.logic.gameobjects;

import static tp1.p2.view.Messages.error;

import java.util.Arrays;
import java.util.List;

import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class PlantFactory {

	private static final List<Plant> AVAILABLE_PLANTS = Arrays.asList(new Sunflower(), new Peashooter(), new WallNut(),
			new CherryBomb());

	public static String isValidPlant(String plantName) {
		for (Plant p : AVAILABLE_PLANTS) {
			if (p.getName().equalsIgnoreCase(plantName) || p.getShortcut().equalsIgnoreCase(plantName)) {
				return p.getName();
			}
		}

		return null;
	}

	public static Plant spawnPlant(String plantName, GameWorld game, int col, int row) throws GameException {
		String name = isValidPlant(plantName);
		if (name == null) {
			throw new CommandParseException(error(Messages.INVALID_GAME_OBJECT));
		}
		if (game.isExcluding(col, row)) {
			throw new InvalidPositionException(error(Messages.INVALID_POSITION.formatted(col, row)));
		}
		Plant p = getPlantByName(name);
		return p.create(game, col, row);
	}

	public static int getPlantPrice(String plantName) {
		Plant p = getPlantByName(plantName);
		return p.getPrice();
	}

	private static Plant getPlantByName(String plantName) {
		for (Plant p : AVAILABLE_PLANTS) {
			if (p.getName().equalsIgnoreCase(plantName)) {
				return p;
			}
		}
		return null;
	}

	public static String getAvailablePlants() {
		StringBuilder buffer = new StringBuilder();
		for (Plant p : AVAILABLE_PLANTS) {
			buffer.append(p.getDescription()).append(Messages.LINE_SEPARATOR);
		}
		return buffer.toString();
	}

	/*
	 * Avoid creating instances of this class
	 */
	private PlantFactory() {
	}
}

package tp1.p2.logic.gameobjects;

import static tp1.p2.view.Messages.error;

import java.util.Arrays;
import java.util.List;

import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class ZombieFactory {
	private static final List<Zombie> AVAILABLE_ZOMBIES = Arrays.asList(new CommonZombie(), new BucketHeadZombie(),
			new SportyZombie(), new ExplosiveZombie());

	/**
	 * Checks to see if an index of a zombie is valid. It is a bit silly to use
	 * index instead of name, but that's in the provided code.
	 * 
	 * @param index the index (?) of the zombie in the AVAILABLE_ZOMBIES list
	 * @return <code>true</code> if the index is within the list
	 */
	public static boolean isValidZombie(int index) {
		if (index >= 0 && index < AVAILABLE_ZOMBIES.size()) {
			return true;
		}

		return false;
	}

	public static Zombie spawnZombie(int zombieIdx, GameWorld game, int col, int row) throws GameException {
		if (game.isExcluding(col, row)) {
			throw new InvalidPositionException(error(Messages.INVALID_POSITION.formatted(col, row)));
		}

		if (!isValidZombie(zombieIdx)) {
			throw new GameException(error(Messages.INVALID_GAME_OBJECT));
		}
		// TODO add your code here
		return AVAILABLE_ZOMBIES.get(zombieIdx).create(game, col, row);
	}

	/**
	 * The corresponding print function for all zombies for the ListZombies command.
	 */
	public static String printAvailableZombies() {
		StringBuilder buffer = new StringBuilder();
		for (Zombie z : AVAILABLE_ZOMBIES) {
			buffer.append(z.getDescription()).append(Messages.LINE_SEPARATOR);
		}
		return buffer.toString();
	}

	public static int getNoOfZombieTypes() {
		return AVAILABLE_ZOMBIES.size();
	}

	/*
	 * Avoid creating instances of this class
	 */
	private ZombieFactory() {
	}
}

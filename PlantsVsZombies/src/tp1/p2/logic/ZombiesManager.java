package tp1.p2.logic;

import java.util.Random;

import tp1.p2.control.Level;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.logic.gameobjects.ZombieFactory;

/**
 * Manage zombies in the game.
 *
 */
public class ZombiesManager {

	private GameWorld game;

	private Level level;

	private Random rand;

	private int remainingZombies;

	public ZombiesManager(GameWorld game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		this.rand = rand;
		this.remainingZombies = level.getNumberOfZombies();
	}

	/**
	 * Checks if the game should add (if possible) a zombie to the game.
	 * 
	 * @return <code>true</code> if a zombie should be added to the game.
	 */
	private boolean shouldAddZombie() {
		return rand.nextDouble() < level.getZombieFrequency();
	}

	/**
	 * Return a random row within the board limits.
	 * 
	 * @return a random row.
	 */
	private int randomZombieRow() {
		return rand.nextInt(GameWorld.NUM_ROWS);
	}

	private int randomZombieType() {
		return rand.nextInt(ZombieFactory.getNoOfZombieTypes());
	}

	public void update() throws GameException {
		addZombie();
	}

	public boolean addZombie() throws GameException {
		int row = randomZombieRow();
		return addZombie(row);
	}

	public boolean addZombie(int row) throws GameException {
		boolean canAdd = getRemainingZombies() > 0 && shouldAddZombie() && isPositionEmpty(GameWorld.NUM_COLS, row);
		int zombieType = randomZombieType();

		if (canAdd) {
			// TODO add your code here
			Zombie newZombie = ZombieFactory.spawnZombie(zombieType, game, Game.NUM_COLS, row);

			newZombie.onEnter();
			remainingZombies--;
		}
		return canAdd;
	}

	// TODO add your code here

	/**
	 * Restart the ZombiesManager to the initial state
	 * 
	 * @param newRand the initial pseudo-random generator
	 */
	public void restart(Random newRand) {
		rand = newRand;
		remainingZombies = level.getNumberOfZombies();
	}

	public void restart(Level newLevel, Random newRand) {
		level = newLevel;
		rand = newRand;
		remainingZombies = level.getNumberOfZombies();
	}

	/**
	 * Get the remaining number of zombies
	 * 
	 * @return the remaining number of zombies
	 */
	public int getRemainingZombies() {
		return remainingZombies;
	}

	/**
	 * Check if tile at position <code> (col, row) </code> is not occupied by a
	 * zombie
	 * 
	 * @param col column position of the tile
	 * @param row row position of this tile
	 * 
	 * @return <code>true</code> if the tile is not occupied by a zombie
	 */
	public boolean isPositionEmpty(int col, int row) {
		return !game.isTileOccupied(col, row);
	}

	// isExclusive() -> GameItem and GameObject
	// isExcluding() -> Game
	// isExcludng() -> Gaem
}

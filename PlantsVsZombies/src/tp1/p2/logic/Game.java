package tp1.p2.logic;

import static tp1.p2.view.Messages.error;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

import tp1.p2.control.Command;
import tp1.p2.control.Level;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.NotCatchablePositionException;
import tp1.p2.control.exceptions.NotEnoughCoinsException;
import tp1.p2.logic.actions.GameAction;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.logic.gameobjects.ZombieFactory;
import tp1.p2.view.Messages;
import tp1.p2.record.Record;
/**
 * Encapsulate the logic and state of the game
 *
 */
public class Game implements GameStatus, GameWorld {

	public boolean hasPlayerQuit;
	private int currCycleNumber;
	private Random rand;
	private ZombiesManager zombiesManager;
	private long seed;
	private Level level;
	private Record record;
	private int sunCoins;
	private SunsManager sunManager;

	private GameObjectContainer objectContainer;

	private Deque<GameAction> actions;

	/**
	 * Construct and initial a game
	 * 
	 * @param inputSeed  input seed used for pseudo-random generator
	 * @param inputLevel input level of the game
	 */
	public Game(long inputSeed, Level inputLevel, Record inputRecord) throws GameException {
		seed = inputSeed;
		level = inputLevel;
		record = inputRecord;
		rand = new Random(seed);
		zombiesManager = new ZombiesManager(this, level, rand);

		currCycleNumber = 0;
		sunCoins = 50;

		hasPlayerQuit = false;

		sunManager = new SunsManager(this, rand);
		objectContainer = new GameObjectContainer();
		actions = new ArrayDeque<GameAction>();
	}

	/**
	 * Get symbol of a game object at position <code>(col, row)</code> if it exists
	 * 
	 * @param col column position of the game object
	 * @param row row position of the game object
	 * 
	 * @return symbol of a game object if it exists at position
	 *         <code>(col, row)</code>, otherwise return an empty string
	 */
	public String positionToString(int col, int row) {
		return objectContainer.positionToString(col, row);
	}

	/**
	 * Add new plant based one the provided name and position and pay for the new
	 * plant is the adding is successful
	 * 
	 * @param plantName name of the new plant
	 * @param row       row position of the new plant
	 * @param col       column position of the new plant
	 * 
	 * @return <code>true</code> if the new plant is successfully added to the game
	 */
	public boolean addNewPlant(String plantName, GameWorld game, int col, int row, boolean consumeCoins)
			throws GameException {
		Plant newPlant = PlantFactory.spawnPlant(plantName, game, col, row);

		if (consumeCoins) {
			tryToBuy(newPlant.getPrice());
		}
		newPlant.onEnter();

		return true;

//		if (newPlant != null) {
//			if (consumeCoins) {
//				updateBalanceAfterPay(newPlant.getPrice());
//			}
//			newPlant.onEnter();
//			return true;
//		}
//		return false;
	}

	/**
	 * Cheats a new zombie in based on provided name and position, for debugging.
	 * 
	 * @param zombieIdx index of the new zombie in the AVAILABLE_ZOMBIES
	 * @param row       row position of the new zombie
	 * @param col       column position of the new zombie
	 * 
	 * @return <code>true</code> if the new zombie is successfully added to the game
	 */
	public boolean cheatNewZombie(int zombieIdx, GameWorld game, int col, int row) throws GameException {
		Zombie newZombie = ZombieFactory.spawnZombie(zombieIdx, game, col, row);
		newZombie.onEnter();
		return true;
	}

	public boolean isTileOccupied(int col, int row) {
		return objectContainer.hasObjectAt(col, row);
	}

	/**
	 * Update number of sun coins after paying a cost
	 * 
	 * @param cost number of coins to pay
	 */
	private void updateBalanceAfterPay(int cost) {
		sunCoins -= cost;
	}

	/**
	 * Update game state
	 */
	public void update() throws GameException {
		currCycleNumber++;
		Command.newCycle(); // refreshes catch command
		zombiesManager.update();
		sunManager.update();
		objectContainer.update();

		boolean deadRemoved = true;
		while (deadRemoved || areTherePendingActions()) {
			// Execute pending actions
			executePendingActions();

			// Remove dead
			deadRemoved = objectContainer.removeDeadObjects();
		}

	}

	/**
	 * Executes a given command.
	 * 
	 * @param command the inputted command
	 * @return <code>true</code> if the command should allow the game to refresh the
	 *         display
	 * @throws GameException
	 */
	public boolean execute(Command command) throws GameException {
		boolean result = command.execute(this);
		return result;
	}

	/**
	 * Perform action after receiving new sun coins
	 */
	public void onReceiveNewSunCoins(int noOfSunCoins) {
		sunCoins += noOfSunCoins;
	}

	/**
	 * Check if the player wins
	 * 
	 * @return <code>true</code> if the player wins
	 */
	public boolean hasPlayerWon() {
		return zombiesManager.getRemainingZombies() == 0 && Zombie.getAliveZombiesCounter() == 0;
	}

	/**
	 * Check if the zombies win
	 * 
	 * @return <code>true</code> if the zombies win
	 */
	public boolean haveZombiesWon() {
		return objectContainer.haveZombiesWon();
	}

	/**
	 * Check if the game winner has been found or if the game should end
	 * 
	 * @return <code>true</code> if the game winner has been found
	 */
	public boolean hasFoundWinner() {
		return hasPlayerWon() || haveZombiesWon();
	}

	public void restart() throws GameException {
		try {
			record.readFromRecord();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			hasPlayerQuit = true; // hasPlayerQuite has a different purpose
			// This is just to make the program terminate
		}
		currCycleNumber = 0;
		sunCoins = 50;
		rand = new Random(seed);

		objectContainer.restart();
		zombiesManager.restart(rand);
		sunManager.restart(rand);

		System.out.println(String.format(Messages.CONFIGURED_LEVEL, level.name()));
		System.out.println(String.format(Messages.CONFIGURED_SEED, seed));
	}

	/**
	 * Restart game to initial configuration
	 * 
	 * @param level the new level
	 */
	public void restart(Level newLevel) throws GameException {
		try {
			record.readFromRecord();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			hasPlayerQuit = true; // hasPlayerQuite has a different purpose
			// This is just to make the program terminate
		}
		currCycleNumber = 0;
		sunCoins = 50;
		rand = new Random(seed);
		setLevel(newLevel);

		objectContainer.restart();
		zombiesManager.restart(newLevel, rand);
		sunManager.restart(rand);

		System.out.println(String.format(Messages.CONFIGURED_LEVEL, newLevel.name()));
		System.out.println(String.format(Messages.CONFIGURED_SEED, seed));
	}

	/**
	 * Restart game to initial configuration
	 * 
	 * @param level the new level
	 * @param seed  the new seed
	 */
	public void restart(Level newLevel, long newSeed) throws GameException {
		try {
			record.readFromRecord();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			hasPlayerQuit = true; // hasPlayerQuite has a different purpose
			// This is just to make the program terminate
		}
		currCycleNumber = 0;
		sunCoins = 50;
		rand = new Random(newSeed);
		setLevel(newLevel);
		setSeed(newSeed);

		objectContainer.restart();
		zombiesManager.restart(level, rand);
		sunManager.restart(rand);

		System.out.println(String.format(Messages.CONFIGURED_LEVEL, newLevel.name()));
		System.out.println(String.format(Messages.CONFIGURED_SEED, newSeed));
	}

	/**
	 * Sets the game's level to a different one
	 * 
	 * @param newLevel the new level of the game
	 */
	private void setLevel(Level newLevel) {
		level = newLevel;
	}

	/**
	 * Sets the game's seed to a different one
	 * 
	 * @param newSeed the new seed of the game
	 */
	private void setSeed(long newSeed) {
		seed = newSeed;
	}

	/**
	 * Get information about current the current number of cycle
	 * 
	 * @return the current number of cycle
	 */
	public int getCycle() {
		return currCycleNumber;
	}

	/**
	 * Get information about the current number of sun coins
	 * 
	 * @return the current number of sun coins
	 */
	public int getSuncoins() {
		return sunCoins;
	}

	/**
	 * Get information about the remaining zombies
	 * 
	 * @return the number of remaining zombies
	 */
	public int getRemainingZombies() {
		return zombiesManager.getRemainingZombies();
	}

	/**
	 * Get information about the current amount of generated suns
	 * 
	 * @return the current number of generated suns
	 */
	public int getGeneratedSuns() {
		return sunManager.getGeneratedSuns();
	}

	/**
	 * Get information about the current amount of caught suns
	 * 
	 * @return the current number of caught suns
	 */
	public int getCaughtSuns() {
		return sunManager.getCatchedSuns();
	}

	/**
	 * Quits the game.
	 */
	public void quitGame() {
		hasPlayerQuit = true;
	}

	public void addItem(GameObject gameObject) {
		objectContainer.addNewGameObject(gameObject);
	}

	public void removeItem(GameObject gameObject) {
		objectContainer.removeGameObject(gameObject);
	}

	public int getBalance() {
		return sunCoins;
	}

	public GameItem getGameItemInPosition(int col, int row) {
		return objectContainer.getGameItemInPosition(col, row);
	}

	/**
	 * Check if the player has exited the game
	 * 
	 * @return <code>true</code> if the game has been exited
	 */
	public boolean doesPlayerQuit() {
		return hasPlayerQuit;
	}

	public boolean canPayPlant(String plantName) {
		if (sunCoins >= PlantFactory.getPlantPrice(plantName)) {
			return true;
		}
		return false;
	}

	private void executePendingActions() {
		while (!this.actions.isEmpty()) {
			GameAction action = this.actions.removeLast();
			action.execute(this);
		}
	}

	private boolean areTherePendingActions() {
		return this.actions.size() > 0;
	}

	public void pushAction(GameAction gameAction) {
		actions.addLast(gameAction);
	}

	@Override
	public boolean catchSun(GameWorld game, int col, int row) throws GameException {
		boolean caught = false;
		GameItem item = objectContainer.getNonExclusiveGameItemInPosition(col, row);
		while (item != null) {
			caught = item.catchObject();
			item = objectContainer.getNonExclusiveGameItemInPosition(col, row);
		}
		if (!caught) {
			throw new NotCatchablePositionException(error(Messages.NO_CATCHABLE_IN_POSITION.formatted(col, row)));
		}
		return caught;
	}

	/*
	 * Indicates whether the cell contains a game object that is exclusive, meaning
	 * that it that cannot share a cell with other exclusive game objects.
	 *
	 * @param col Column of the cell
	 * 
	 * @param row Row of the cell
	 *
	 * @return <code>true</code> if the cell contains a game object with the
	 * exclusive property, <code>false</code> otherwise.
	 */
	@Override
	public boolean isExcluding(int col, int row) {
		return this.objectContainer.isExcluding(col, row);
	}

	/**
	 * Checks if a cell is fully occupied, that is, the position can be shared
	 * between an NPC (Plant, Zombie) and Suns .
	 * 
	 * @param col Column of the cell
	 * @param row Row of the cell
	 * 
	 * @return <code>true</code> if the cell is fully occupied, <code>false</code>
	 *         otherwise.
	 */
	@Override
	public boolean isFullyOcuppied(int col, int row) {
		return this.objectContainer.isFullyOccupied(col, row);
	}

	public void tryToBuy(int cost) throws GameException {
		if (cost > sunCoins) {
			throw new NotEnoughCoinsException(error(Messages.NOT_ENOUGH_COINS));
		}
		sunCoins -= cost;
	}
	
	public int getCurrScore() {
		return Zombie.getNormallyKilledZombiesCounter() * 10 + Zombie.getZombiesKilledInExplosionCounter() * 20;
	}
	
	public void saveRecord() throws IOException {
		record.updateRecord(level.name(), getCurrScore());
	}
	
	public void showRecord() {
		System.out.println(Messages.CURRENT_RECORD.formatted(level.name(), record.getCurrHighScore(level.name())));
	}
	
	@Override
	public void checkValidPlantPosition(int col, int row) throws GameException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkValidZombiePosition(int col, int row) throws GameException {
		// TODO Auto-generated method stub

	}

	@Override
	public void tryToCatchObject(int col, int row) throws GameException {
		// TODO Auto-generated method stub

	}
}

package tp1.p2.logic;

import tp1.p2.control.Level;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.actions.GameAction;
import tp1.p2.logic.gameobjects.GameObject;

public interface GameWorld {

	static final int NUM_ROWS = 4;

	static final int NUM_COLS = 8;

	boolean addNewPlant(String plantName, GameWorld game, int row, int col, boolean consumeCoins) throws GameException;

	boolean cheatNewZombie(int zombieIdx, GameWorld game, int row, int col) throws GameException;

	void restart() throws GameException;

	void restart(Level level) throws GameException;

	void restart(Level level, long seed) throws GameException;

	void quitGame();

	GameItem getGameItemInPosition(int col, int row);

	boolean isTileOccupied(int col, int row);

	void onReceiveNewSunCoins(int noOfSunCoins);

	void addItem(GameObject gameObject);

	void removeItem(GameObject gameObject);

	boolean canPayPlant(String plantName);

	void update() throws GameException;

	void pushAction(GameAction gameAction);

	boolean catchSun(GameWorld game, int col, int row) throws GameException;

	boolean isExcluding(int col, int row);

	void tryToBuy(int cost) throws GameException;

	void checkValidPlantPosition(int col, int row) throws GameException;

	void checkValidZombiePosition(int col, int row) throws GameException;

	void tryToCatchObject(int col, int row) throws GameException;

	void showRecord();
}

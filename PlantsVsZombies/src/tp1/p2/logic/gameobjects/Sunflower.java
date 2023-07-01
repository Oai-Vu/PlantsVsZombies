package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sunflower extends Plant {
	/**
	 * Initial endurance of a sunflower
	 */
	public static final int SUNFLOWER_ENDURANCE = 1;

	/**
	 * Number of coins to buy a sunflower
	 */
	public static final int SUNFLOWER_COST = 20;

	/**
	 * Number of cycles needed for a sunflower to produce new sun coins
	 */
	public static final int SUNFLOWER_GROWING_TIME = 3;

	/**
	 * Number of sun coins that a sunflower can produce each time
	 */
	public static final int SUNFLOWER_PRODUCED_SUN_COINS = 10;
	
	/**
	 * Number of damage points that a sunflower causes on a zombie
	 */
	
	public static final int SUNFLOWER_DAMAGE = 0;
	
	private int currGrowingTime;

	public Sunflower(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = SUNFLOWER_ENDURANCE;
		currGrowingTime = 0;
	}

	public Sunflower() {
		super();
	}

	@Override
	protected String getName() {
		return Messages.SUNFLOWER_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.SUNFLOWER_SYMBOL;
	}

	@Override
	/**
	 * get the decription of the sunflower
	 * 
	 * @return description of the sunflower
	 */
	public String getDescription() {
		return Messages.plantDescription(Messages.SUNFLOWER_NAME_SHORTCUT, SUNFLOWER_COST, SUNFLOWER_DAMAGE,
				SUNFLOWER_ENDURANCE);
	}

	@Override
	protected String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.SUNFLOWER_SYMBOL, remainingLive);
	}

	@Override
	public Plant create(GameWorld game, int col, int row) {
		return new Sunflower(game, col, row);
	}

	@Override
	public int getPrice() {
		return SUNFLOWER_COST;
	}

	@Override
	public void update() {
		int receivedSunCoins = getProducedNewSunCoins();
		if (receivedSunCoins != 0) {
			Sun newSun = new Sun(game, col, row);
			newSun.onEnter();
		} 
		else {
			if (isAlive()) {
				currGrowingTime++;
			}
			
		}
	}

	/**
	 * Get the number of sun coins produced by the sunflower after the current cycle
	 * 
	 * @return the number of sun coins produced by the sunflower after the current
	 *         cycle
	 */
	private int getProducedNewSunCoins() {
		if (currGrowingTime == SUNFLOWER_GROWING_TIME) {
			currGrowingTime = 0;
			return SUNFLOWER_PRODUCED_SUN_COINS;
		}
		return 0;
	}
}

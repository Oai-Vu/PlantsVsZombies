package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.logic.actions.ExplosionAction;
import tp1.p2.logic.actions.GameAction;
import tp1.p2.view.Messages;

/**
 * Model cherry bomb in the game
 */
public class CherryBomb extends Plant {
	/**
	 * Initial endurance of a cherry bomb
	 */
	public static final int CHERRY_BOMB_ENDURANCE = 2;

	/**
	 * Number of coins to buy a cherry bomb
	 */
	public static final int CHERRY_BOMB_COST = 50;
	
	/**
	 * Number of damage points that a cherry bomb causes on a zombie
	 */
	
	public static final int CHERRY_BOMB_DAMAGE = 10;
	
	public static final int CHERRY_BOMB_GROWING_TIME = 2;
	
	private int currGrowingTime;
	
	/**
	 * Parameterized constructor of cherry bomb
	 * 
	 * @param game GameWorld which the cherry bomb belongs to
	 * @param col col position of the cherry bomb
	 * @param row row position of the cherry bomb 
	 */
	public CherryBomb(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = CHERRY_BOMB_ENDURANCE;
	}

	/**
	 * Default constructor of the cherry bomb 
	 */
	public CherryBomb() {

	}

	
	/**
	 * Get the name of the cherry bomb 
	 * 
	 * @return name of cherry bomb 
	 */
	@Override
	protected String getName() {
		return Messages.CHERRY_BOMB_NAME;
	}

	/**
	 * Get the shortcut of the cherry bomb 
	 * 
	 * @return shortcut of the cherry bomb 
	 */
	@Override
	protected String getShortcut() {
		return Messages.CHERRY_BOMB_SYMBOL;
	}

	@Override
	/**
	 * Get the description of the cherry bomb 
	 * 
	 * @return description of the cherry bomb 
	 */
	public String getDescription() {
		return Messages.plantDescription(Messages.CHERRY_BOMB_NAME_SHORTCUT, CHERRY_BOMB_COST, 
				CHERRY_BOMB_DAMAGE, CHERRY_BOMB_ENDURANCE);
	}

	
	/**
	 * Get the symbol of the cherry bomb 
	 * 
	 * @return symbol of the cherry bomb 
	 */
	@Override
	protected String getSymbol() {
		if (currGrowingTime == CHERRY_BOMB_GROWING_TIME) {
			return Messages.GAME_OBJECT_STATUS.formatted(Messages.EXPLOSIVE_CHERRY_BOMB_SYMBOL, remainingLive);
		}
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.CHERRY_BOMB_SYMBOL, remainingLive);
	}

	/**
	 * Get the damage value of the cherry bomb
	 * 
	 * @return damage value of the cherry bomb
	 */
	@Override
	protected int getDamageValue() {
		return CHERRY_BOMB_DAMAGE;
	}

	/**
	 * Create a cherry bomb
	 * 
	 * @return a Plant which represents the cherry bomb 
	 */
	public Plant create(GameWorld game, int col, int row) {
		return new CherryBomb(game, col, row);
	}

	@Override
	public int getPrice() {
		return CHERRY_BOMB_COST;
	}
	
	@Override
	public void update() {
		if (isAlive()) {
			currGrowingTime++;
		}
		if (currGrowingTime == CHERRY_BOMB_GROWING_TIME + 1) {
			onExit();
		} 
	}
	
	@Override
	public void onExit() {
		if (isAlive()) {
			GameAction cherryBombExposion = new ExplosionAction(col, row, CHERRY_BOMB_DAMAGE, false);
			game.pushAction(cherryBombExposion);
			super.onExit();
		} else {
			super.onExit(); // A dead cherry bomb can not explode
		}
	}
}

package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class WallNut extends Plant{
	/**
	 * Initial endurance of a wall-nut
	 */
	public static final int WALL_NUT_ENDURANCE = 10;

	/**
	 * Number of coins to buy a wall-nut
	 */
	public static final int WALL_NUT_COST = 50;
	
	/**
	 * Number of damage points that a wall-nut caused on a zombie
	 */
	public static final int WALL_NUT_DAMAGE = 0;
	
	public WallNut(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = WALL_NUT_ENDURANCE;
	}

	public WallNut() {

	}

	@Override
	protected String getName() {
		return Messages.WALL_NUT_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.WALL_NUT_SYMBOL;
	}

	@Override
	/**
	 * get the decription of the wall-nut
	 * 
	 * @return description of the wall-nut
	 */
	public String getDescription() {
		return Messages.plantDescription(Messages.WALL_NUT_NAME_SHORTCUT, WALL_NUT_COST, 
				WALL_NUT_DAMAGE, WALL_NUT_ENDURANCE);
	}

	@Override
	protected String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.WALL_NUT_SYMBOL, remainingLive);
	}

	@Override
	protected int getDamageValue() {
		return WALL_NUT_DAMAGE;
	}

	public Plant create(GameWorld game, int col, int row) {
		return new WallNut(game, col, row);
	}

	@Override
	public int getPrice() {
		return WALL_NUT_COST;
	}
	
	@Override
	public void update() {
		
	}
}

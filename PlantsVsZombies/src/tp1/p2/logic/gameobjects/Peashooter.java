package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Peashooter extends Plant {
	/**
	 * Initial endurance of a peashooter
	 */
	
	public static final int PEASHOOTER_ENDURANCE = 3;
	
	/**
	 * Number of damage points that a peashooter causes on a zombie
	 */
	
	public static final int PEASHOOTER_DAMAGE = 1;
	
	/**
	 * Number of coins to buy a peashooter
	 */

	public static final int PEASHOOTER_COST = 50;
	
	public Peashooter(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = PEASHOOTER_ENDURANCE;
	}

	public Peashooter() {

	}

	@Override
	protected String getName() {
		return Messages.PEASHOOTER_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.PEASHOOTER_SYMBOL;
	}

	@Override
	/**
	 * get the decription of the peashooter
	 * 
	 * @return description of the peashooter
	 */
	public String getDescription() {
		return Messages.plantDescription(Messages.PEASHOOTER_NAME_SHORTCUT, PEASHOOTER_COST,
				PEASHOOTER_DAMAGE, PEASHOOTER_ENDURANCE);
	}

	@Override
	protected String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.PEASHOOTER_SYMBOL, remainingLive);
	}

	@Override
	protected int getDamageValue() {
		return PEASHOOTER_DAMAGE;
	}

	public Plant create(GameWorld game, int col, int row) {
		return new Peashooter(game, col, row);
	}

	@Override
	public int getPrice() {
		return PEASHOOTER_COST;
	}
	
	@Override
	public void update() {
		for (int i = col + 1; i < GameWorld.NUM_COLS; i++) {
			GameItem item = game.getGameItemInPosition(i, row);
			if (item != null && item.isAlive()) {
				if (item.receivePlantAttack(this.getDamageValue())) {
					if (!item.isAlive() ) {
						item.onNormallyKilled();
					}
					break;
				}
			}
		}
	}
}

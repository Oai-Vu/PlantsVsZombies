package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class CommonZombie extends Zombie {
	/**
	 * Number of damage points that a zombie causes on a plant
	 */
	public static final int COMMON_ZOMBIE_DAMAGE = 1;

	/**
	 * Initial resistence of a zombie
	 */
	public static final int COMMON_ZOMBIE_RESISTENCE = 5;

	public static final int COMMON_ZOMBIE_SPEED = 2;
	
	public CommonZombie(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = COMMON_ZOMBIE_RESISTENCE;
		speed = COMMON_ZOMBIE_SPEED;
	}

	public CommonZombie() {

	}

	@Override
	public String getName() {
		return Messages.ZOMBIE_NAME;
	}

	@Override
	/**
	 * get the decription of the peashooter
	 * 
	 * @return description of the peashooter
	 */
	public String getDescription() {
		return Messages.zombieDescription(Messages.ZOMBIE_NAME, COMMON_ZOMBIE_SPEED, COMMON_ZOMBIE_DAMAGE,
				COMMON_ZOMBIE_RESISTENCE);
	}

	@Override
	public String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.ZOMBIE_SYMBOL, remainingLive);
	}

	@Override
	protected int getDamageValue() {
		return COMMON_ZOMBIE_DAMAGE;
	}

	@Override
	public Zombie create(GameWorld game, int col, int row) {
		return new CommonZombie(game, col, row);
	}

	@Override
	public Zombie create() {
		return new CommonZombie();
	}
}

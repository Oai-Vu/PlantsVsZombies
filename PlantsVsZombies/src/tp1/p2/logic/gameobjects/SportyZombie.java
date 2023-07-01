package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class SportyZombie extends Zombie {
	public static final int SPORTY_ZOMBIE_DAMAGE = 1;

	public static final int SPORTY_ZOMBIE_RESISTENCE = 2;

	public static final int SPORTY_ZOMBIE_SPEED = 1;
	
	public SportyZombie(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = SPORTY_ZOMBIE_RESISTENCE;
		speed = SPORTY_ZOMBIE_SPEED;
	}

	public SportyZombie() {

	}

	@Override
	public String getName() {
		return Messages.SPORTY_ZOMBIE_NAME;
	}

	@Override
	/**
	 * get the decription of the peashooter
	 * 
	 * @return description of the peashooter
	 */
	public String getDescription() {
		return Messages.zombieDescription(Messages.SPORTY_ZOMBIE_NAME, SPORTY_ZOMBIE_SPEED,
				SPORTY_ZOMBIE_DAMAGE, SPORTY_ZOMBIE_RESISTENCE);
	}

	@Override
	public String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.SPORTY_ZOMBIE_SYMBOL, remainingLive);
	}

	@Override
	protected int getDamageValue() {
		return SPORTY_ZOMBIE_DAMAGE;
	}

	@Override
	public Zombie create(GameWorld game, int col, int row) {
		return new SportyZombie(game, col, row);
	}

	@Override
	public Zombie create() {
		return new SportyZombie();
	}
}

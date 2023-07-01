package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.logic.actions.*;

public class ExplosiveZombie extends Zombie {
	public static final int EXPLOSIVE_ZOMBIE_DAMAGE = 1;

	public static final int EXPLOSIVE_ZOMBIE_RESISTENCE = 5;

	public static final int EXPLOSIVE_ZOMBIE_SPEED = 2;
	
	public ExplosiveZombie(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = EXPLOSIVE_ZOMBIE_RESISTENCE;
		speed = EXPLOSIVE_ZOMBIE_SPEED;
	}

	public ExplosiveZombie() {

	}

	@Override
	public String getName() {
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
	}

	@Override
	/**
	 * get the decription of the peashooter
	 * 
	 * @return description of the peashooter
	 */
	public String getDescription() {
		return Messages.zombieDescription(Messages.EXPLOSIVE_ZOMBIE_NAME, EXPLOSIVE_ZOMBIE_SPEED,
				EXPLOSIVE_ZOMBIE_DAMAGE, EXPLOSIVE_ZOMBIE_RESISTENCE);
	}

	@Override
	public String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.EXPLOSIVE_ZOMBIE_SYMBOL, remainingLive);
	}

	@Override
	protected int getDamageValue() {
		return EXPLOSIVE_ZOMBIE_DAMAGE;
	}

	@Override
	public void update() {
		if (!isAlive()) {
			onExit();
			return;
		}
		super.update();
	}

	@Override
	public Zombie create(GameWorld game, int col, int row) {
		return new ExplosiveZombie(game, col, row);
	}

	@Override
	public Zombie create() {
		return new ExplosiveZombie();
	}
	
	@Override
	public void onExit() {
		GameAction explosiveZombieExplosion = new ExplosionAction(col, row, getDamageValue(), true);
		game.pushAction(explosiveZombieExplosion);
		super.onExit();
	}
}

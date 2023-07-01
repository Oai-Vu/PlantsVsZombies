package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class BucketHeadZombie extends Zombie {
	public static final int BUCKET_HEAD_ZOMBIE_DAMAGE = 1;

	public static final int BUCKET_HEAD_ZOMBIE_RESISTENCE = 8;

	public static final int BUCKET_HEAD_ZOMBIE_SPEED = 4;
	
	public BucketHeadZombie(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingLive = BUCKET_HEAD_ZOMBIE_RESISTENCE;
		speed = BUCKET_HEAD_ZOMBIE_SPEED;
	}

	public BucketHeadZombie() {

	}

	@Override
	public String getName() {
		return Messages.BUCKET_HEAD_ZOMBIE_NAME;
	}

	@Override
	/**
	 * get the decription of the peashooter
	 * 
	 * @return description of the peashooter
	 */
	public String getDescription() {
		return Messages.zombieDescription(Messages.BUCKET_HEAD_ZOMBIE_NAME, BUCKET_HEAD_ZOMBIE_SPEED,
				BUCKET_HEAD_ZOMBIE_DAMAGE, BUCKET_HEAD_ZOMBIE_RESISTENCE);
	}

	@Override
	public String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.BUCKET_HEAD_ZOMBIE_SYMBOL, remainingLive);
	}

	@Override
	protected int getDamageValue() {
		return BUCKET_HEAD_ZOMBIE_DAMAGE;
	}

	@Override
	public Zombie create(GameWorld game, int col, int row) {
		return new BucketHeadZombie(game, col, row);
	}

	@Override
	public Zombie create() {
		return new BucketHeadZombie();
	}
}

package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

public abstract class Zombie extends GameObject {
	private static int aliveZombiesCounter = 0;
	private static int normallyKilledZombiesCounter = 0;
	private static int zombiesKilledInExplosionCounter = 0;
	
	protected int remainingLive;
	protected int currMovementCycle;
	protected int speed;

	public Zombie(GameWorld game, int col, int row) {
		super(game, col, row);
		currMovementCycle = 0;
	}

	public Zombie() {

	}

	/**
	 * Check if the zombie is alive
	 * 
	 * @return <code>true</code> if the zombie is alive
	 */
	public boolean isAlive() {
		return remainingLive > 0;
	}

	@Override
	public void onEnter() {
		aliveZombiesCounter++;
		game.addItem(this);
	}

	@Override
	public void onExit() {
		aliveZombiesCounter--;
		game.removeItem(this);
	}

	@Override
	public void update() {
		GameItem item = game.getGameItemInPosition(col - 1, row);
		if (item != null) {
			item.receiveZombieAttack(getDamageValue());
		}
		
		// Zombie only continues movinng if it's still alive
		if (isAlive()) {
			if (currMovementCycle == speed) {
				if (!game.isExcluding(col - 1, row) ) {
					col--;
					currMovementCycle = 1;
				}
			} 
			else {
				currMovementCycle++;
			}
		}
	}

	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}

	@Override
	public boolean receivePlantAttack(int damage) {
		if (!isAlive()) {
			return false;
		}
		remainingLive = remainingLive - damage;
		return true;
	}

	@Override
	public boolean hasReachedLHS() {
		return col == -1;
	}

	abstract public Zombie create(GameWorld game, int col, int row);

	abstract public Zombie create(); // leftover, doesn't do anything but might be useful?
	
	@Override
	public boolean isExclusive() {
		return true;
	}
	
	public static int getAliveZombiesCounter() {
		return aliveZombiesCounter;
	}
	
	public static int getNormallyKilledZombiesCounter() {
		return normallyKilledZombiesCounter;
	}
	
	public static int getZombiesKilledInExplosionCounter() {
		return zombiesKilledInExplosionCounter;
	}
	
	@Override
	public void onKilledInExplosion() {
		zombiesKilledInExplosionCounter++;
	}
	
	@Override
	public void onNormallyKilled() {
		normallyKilledZombiesCounter++;
	}
}

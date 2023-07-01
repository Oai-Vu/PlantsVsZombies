package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

public abstract class Plant extends GameObject {
	
	protected int remainingLive;
	
	public Plant(GameWorld game, int col, int row) {
		super(game, col, row);
	}
	
	public Plant() {
		
	}
	
	abstract protected String getShortcut();
	
	
	/**
	 * Check if the plant is alive
	 * 
	 * @return <code>true</code> if the plant is alive
	 */
	public boolean isAlive() {
		return remainingLive > 0;
	}
	
	@Override 
	public void onEnter() {
		game.addItem(this);
	}
	
	@Override 
	public void onExit() {
		game.removeItem(this);
	}
	
	@Override 
	public boolean receiveZombieAttack(int damage) {
		if (!isAlive()) {
			return false;
		}
		remainingLive = remainingLive - damage;
		return true;
	}
	
	@Override 
	public boolean receivePlantAttack(int damage) {
		return false;
	}
	
	@Override
	protected int getDamageValue() {
		return 0;
	}
	
	@Override
	public boolean hasReachedLHS() {
		return false;
	}
	
	abstract public Plant create(GameWorld game, int col, int row);
	
	abstract public int getPrice();
	
	@Override
	public boolean isExclusive() {
		return true;
	}
	
	@Override
	public void onKilledInExplosion() {
		
	}
	
	@Override
	public void onNormallyKilled() {
		
	}
}

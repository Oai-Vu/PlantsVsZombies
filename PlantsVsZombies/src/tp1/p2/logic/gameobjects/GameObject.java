package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

/**
 * Base class for game non playable character in the game.
 *
 */
public abstract class GameObject implements GameItem {

	protected GameWorld game;

	protected int col;

	protected int row;

	GameObject() {
	}

	GameObject(GameWorld game, int col, int row) {
		this.game = game;
		this.col = col;
		this.row = row;
	}

	public boolean isInPosition(int col, int row) {
		return this.col == col && this.row == row;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	abstract public boolean isAlive();

	public String toString() {
		if (isAlive()) {
			// TODO add your code here
			return getSymbol();
		}
		return "";
	}

	abstract protected String getSymbol();

	abstract public String getDescription();

	abstract protected String getName();

	abstract protected int getDamageValue();

	abstract public void update();

	abstract public void onEnter();

	abstract public void onExit();

	abstract public boolean receiveZombieAttack(int damage);

	abstract public boolean receivePlantAttack(int damage);

	abstract public boolean hasReachedLHS();

	public boolean catchObject() {
		return false;
	}

	abstract public boolean isExclusive();

	public boolean fillPosition() {
		// TODO Auto-generated method stub
		return false;
	}
	
	abstract public void onKilledInExplosion();
	
	abstract public void onNormallyKilled();
}

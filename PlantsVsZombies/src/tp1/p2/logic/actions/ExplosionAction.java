package tp1.p2.logic.actions;

import tp1.p2.logic.Game;
import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.gameobjects.Zombie;

public class ExplosionAction implements GameAction {

	private int col;

	private int row;

	private int damage;

	private boolean shouldAttackPlant;
	
	public ExplosionAction(int col, int row, int damage, boolean attackPlant) {
		this.col = col;
		this.row = row;
		this.damage = damage;
		shouldAttackPlant = attackPlant;
	}

	@Override
	public void execute(GameWorld game) {
		// TODO add your code here
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1 ; c <= col + 1; c++) {
				if (isValidPosition(c, r)) {
					if (shouldAttackPlant) {
						GameItem item = game.getGameItemInPosition(c, r);
						if (item != null && item.isAlive()) {
							item.receiveZombieAttack(damage);
						}
					}
					else {
						GameItem item = game.getGameItemInPosition(c, r);
						if (item != null && item.isAlive()) {
							item.receivePlantAttack(damage);
							if (!item.isAlive()) {
								item.onKilledInExplosion();
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isValidPosition(int col, int row) {
		if (col >= Game.NUM_COLS || col < 0 || row < 0 || row >= Game.NUM_ROWS) {
			return false;
		}
		return true;
	}

}

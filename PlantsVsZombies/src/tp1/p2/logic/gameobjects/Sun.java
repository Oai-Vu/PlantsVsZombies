package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sun extends GameObject {
	public static int generatedSuns = 0;

	public static int caughtSuns = 0;

	private int remainingCycles;

	// Remember that a Sun is updated the very same cycle is added to the container
	public static final int SUN_COOLDOWN = 10 + 1;

	public static final int SUN_VALUE = 10;

	public Sun(GameWorld game, int col, int row) {
		super(game, col, row);
		remainingCycles = SUN_COOLDOWN;
	}

	public Sun() {

	}

	@Override
	public boolean catchObject() {
		game.onReceiveNewSunCoins(SUN_VALUE);
		caughtSuns++;
		remainingCycles = 0;
		return true;
	}

	@Override
	public boolean isExclusive() {
		return false;
	}

	@Override
	protected String getName() {
		return "";
	}

	protected String getShortcut() {
		return Messages.SUN_SYMBOL;
	}

	@Override
	public String getDescription() {
		return Messages.SUN_DESCRIPTION;
	}

	@Override
	public String getSymbol() {
		return Messages.GAME_OBJECT_STATUS.formatted(Messages.SUN_SYMBOL, remainingCycles);
	}

	@Override
	public void onEnter() {
		generatedSuns++;
		game.addItem(this);
	}

	@Override
	public void onExit() {
		game.removeItem(this);
	}

	@Override
	public void update() {
		if (isAlive()) {
			remainingCycles--;
		}
	}

	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
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

	@Override
	public boolean isAlive() {
		return remainingCycles > 0;
	}

	public static void resetSunCounters() {
		generatedSuns = 0;
		caughtSuns = 0;
	}

	public static int getCatchedSuns() {
		return caughtSuns;
	}

	public static int getGeneratedSuns() {
		return generatedSuns;
	}

	@Override
	public boolean fillPosition() {
		return false;
	}
	
	public void onKilledInExplosion() {
		
	}
	
	public void onNormallyKilled() {
		
	}

}

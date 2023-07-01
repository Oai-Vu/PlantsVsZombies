package tp1.p2.logic;

public interface GameStatus {

	int getCycle();

	int getSuncoins();

	// TODO add your code here
	String positionToString(int col, int row);

	int getRemainingZombies();

	boolean hasPlayerWon();

	boolean haveZombiesWon();

	boolean doesPlayerQuit();

	int getGeneratedSuns();

	int getCaughtSuns();

	/**
	 * Checks if a cell is fully occupied, that is, the position can be shared
	 * between an NPC (Plant, Zombie) and Suns .
	 * 
	 * @param col Column of the cell
	 * @param row Row of the cell
	 * 
	 * @return <code>true</code> if the cell is fully occupied, <code>false</code>
	 *         otherwise.
	 */
	boolean isFullyOcuppied(int col, int row);
	
	int getCurrScore();
}

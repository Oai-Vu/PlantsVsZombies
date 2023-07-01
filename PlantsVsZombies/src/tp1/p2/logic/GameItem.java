package tp1.p2.logic;

import tp1.p2.logic.gameobjects.GameObject;

/**
 * Represents a game item and its allowed game actions.
 *
 */
public interface GameItem {

	/**
	 * Receive a zombie attack.
	 * 
	 * @param damage Received damage.
	 * 
	 * @return <code>true</code> if a plant has been attacked, <code>false</code>
	 *         otherwise.
	 */
	boolean receiveZombieAttack(int damage);
	
	/**
	 * Receive a plant attack.
	 * 
	 * @param damage Received damage.
	 * 
	 * @return <code>true</code> if a plant has been attacked, <code>false</code>
	 *         otherwise.
	 */
	boolean receivePlantAttack(int damage);
	
	/**
	 * Indicates whether the game object has the property of being exclusive,
	 * meaning that it cannot share a cell with another {@link GameObject} that is
	 * also exclusive; note that an exclusive game object can always share a cell
	 * with other non-exclusive game objects.
	 *
	 * @return <code>true</code> if this game object cannot share a cell with
	 *         another exclusive game object and <code>false</code> if this game
	 *         object can share a cell with any other game object.
	 */
	boolean isExclusive();
	
	/**
	 * Try to catch a sun (if no other sun has been catched this cycle).
	 * 
	 * @return <code>true</code> if the sun has been catched, <code>false</code>
	 *         otherwise.
	 */
	boolean catchObject();
	
	void onKilledInExplosion();
	
	void onNormallyKilled();
	
	boolean isAlive();

}

package tp1.p2.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.view.Messages;

public class GameObjectContainer {

	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		gameObjects = new ArrayList<GameObject>();
	}

	// TODO add your code here
	public void addNewGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	public void removeGameObject(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}

	public int getSize() {
		return gameObjects.size();
	}

	public GameItem getGameItemInPosition(int col, int row) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.isInPosition(col, row)) {
				return gameObject;
			}
		}
		return null;
	}

	public GameItem getNonExclusiveGameItemInPosition(int col, int row) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.isInPosition(col, row)) {
				if (!gameObject.isExclusive() && gameObject.isAlive()) {
					System.out.println(
							gameObject.getCol() + " " + gameObject.getRow() + " " + gameObject.getDescription());
					return gameObject;
				}
			}
		}
		return null;
	}

	public String getElementSymbol(int index) {
		return gameObjects.get(index).toString();
	}

	public boolean isElementInPosition(int index, int col, int row) {
		return gameObjects.get(index).isInPosition(col, row);
	}

	/**
	 * Get symbol of a game object at position <code>(col, row)</code> if it exists
	 * 
	 * @param col column position of the game object
	 * @param row row position of the game object
	 * 
	 * @return symbol of a game object if it exists at position
	 *         <code>(col, row)</code>, otherwise return an empty string
	 */
	public String positionToString(int col, int row) {
		StringBuilder buffer = new StringBuilder();
		boolean sunPainted = false;
		boolean sunAboutToPaint = false;

		for (GameObject g : gameObjects) {
			if (g.isAlive() && g.getCol() == col && g.getRow() == row) {
				String objectText = g.toString();
				sunAboutToPaint = objectText.indexOf(Messages.SUN_SYMBOL) >= 0;
				if (sunAboutToPaint) {
					if (!sunPainted) {
						buffer.append(objectText);
						sunPainted = true;
					}
				} else {
					buffer.append(objectText);
				}
			}
		}

		return buffer.toString();
	}

	public boolean hasObjectAt(int col, int row) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.isInPosition(col, row)) {
				return true;
			}
		}
		return false;
	}

	public void update() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).update();
		}

	}

	public boolean removeDeadObjects() {
		boolean deadRemoved = false;
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject currGameObject = gameObjects.get(i);
			if (!currGameObject.isAlive()) {
				currGameObject.onExit();
				deadRemoved = true;
			}
		}

		return deadRemoved;
	}

	public void restart() {
		gameObjects = new ArrayList<GameObject>();
	}

	public boolean haveZombiesWon() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.hasReachedLHS()) {
				return true;
			}
		}
		return false;
	}

	public boolean isExcluding(int col, int row) {
		for (GameObject gameObject : gameObjects) {
			if (gameObject.isExclusive() && gameObject.isInPosition(col, row)) {
				return true;
			}
		}
		return false;
	}

	public boolean isFullyOccupied(int col, int row) {
		int i = 0;
		boolean fullyOcuppied = false;

		while (i < gameObjects.size() && !fullyOcuppied) {
			GameObject g = gameObjects.get(i);
			if (g.isAlive() && g.isInPosition(col, row)) {
				fullyOcuppied = g.fillPosition();
			}
			i++;
		}

		return fullyOcuppied;
	}
}

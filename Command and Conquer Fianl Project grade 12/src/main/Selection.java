package main;

import java.util.Vector;

/**
 * Class for selecting units and moving the selected units
 * 
 * @author Justin
 *
 */
public class Selection {
	// Declare variables
	private static Vector<Vector<Boolean>> saves = new Vector<Vector<Boolean>>();
	public static Vector<Boolean> selected = new Vector<Boolean>();
	public static boolean isSelected;
	public static boolean tryingToSave = false;

	/**
	 * When created create the 10 saves for the 10 number keys
	 */
	public Selection() {
		for (int i = 0; i < 10; i++) {
			saves.add(new Vector<Boolean>());
		}
	}

	/**
	 * Selects all of the units in the selection box
	 * 
	 * @param units The vector of all of the players units
	 */
	public static void Selected(Vector<Units> units) {
		Selection.isSelected = false;
		Selection.selected.removeAllElements();
		for (int i = 0; i < units.size(); i++) {
			if (DrawImages.selectionRecX < units.elementAt(i).x + units.elementAt(i).width
					&& units.elementAt(i).x < DrawImages.selectionRecX + DrawImages.selectionRecW
					&& units.elementAt(i).y < DrawImages.selectionRecY + DrawImages.selectionRecH
					&& DrawImages.selectionRecY < units.elementAt(i).y + units.elementAt(i).height) {
				Selection.selected.add(true);
				Selection.isSelected = true;
			} else {
				Selection.selected.add(false);
			}
		}
	}

	/**
	 * Saves the selected units to the correct save key
	 * 
	 * @param saveNum The number you would like to save the currently selected units
	 *                to
	 */
	public static void saveSelected(int saveNum) {
		Selection.saves.elementAt(saveNum).removeAllElements();
		for (int i = 0; i < Selection.selected.size(); i++) {
			Selection.saves.elementAt(saveNum).add(Selection.selected.elementAt(i));
		}
	}

	/**
	 * Selects all of the units that were saved to the key
	 * 
	 * @param num The key that you would like to save from
	 */
	public static void getSelected(int num) {
		Selection.selected.removeAllElements();
		Selection.selected.addAll(Selection.saves.elementAt(num));
		Selection.isSelected = true;
	}

	/**
	 * Moves the selected units to the mouse when pressed
	 */
	public static void move() {
		int countX = 0;
		int countY = 0;
		int distanceLeft = 0;
		int distanceRight = 0;

		if (selected.size() > 0) {
			for (int i = 0; i < selected.size(); i++) {
				if (selected.elementAt(i).booleanValue() == true) {
					// When giving move orders have the units go into a formation were the first
					// unit goes in the center then alternating left and right until you reach the
					// line limit and then create a new line
					if (countX % 2 == 0) {
						DrawImages.units.elementAt(i).moveToX = Play.mouseX + (((countX / 2) * 20)) + distanceRight;
						DrawImages.units.elementAt(i).moveToY = Play.mouseY + (countY * 100);
						DrawImages.units.elementAt(i).moving = true;
						distanceRight = distanceRight + DrawImages.units.elementAt(i).width;
					} else {
						distanceLeft = distanceLeft + DrawImages.units.elementAt(i).width;
						DrawImages.units.elementAt(
								i).moveToX = (int) (Play.mouseX - ((((countX + 1) / 2) * 20)) - distanceLeft);
						DrawImages.units.elementAt(i).moveToY = Play.mouseY + (countY * 100);
						DrawImages.units.elementAt(i).moving = true;
					}
					countX++;
					if (countX == 5) {
						countY++;
						countX = 0;
						distanceLeft = 0;
						distanceRight = 0;
					}

				}
			}
		}
	}

	/**
	 * Moves the enemy units
	 */
	public static void moveEnemy() {
		int countX = 0;
		int countY = 0;
		int distanceLeft = 0;
		int distanceRight = 0;
		if (DrawImages.enemyUnits.size() > 0) {
			for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
				// When giving move orders have the enemyUnits go into a formation were the
				// first
				// unit goes in the center then alternating left and right until you reach the
				// line limit and then create a new line
				if (DrawImages.enemyUnits.elementAt(i).name.equals("HarvesterRed") == false
						&& DrawImages.enemyUnits.elementAt(i).moveToX != DrawImages.buildings.elementAt(0).getX()
						&& DrawImages.enemyUnits.elementAt(i).moveToY != DrawImages.buildings.elementAt(0).getY()) {
					if (countX % 2 == 0) {
						DrawImages.enemyUnits.elementAt(i).moveToX = Computerplayer.waitX + (((countX / 2) * 20))
								+ distanceRight;
						DrawImages.enemyUnits.elementAt(i).moveToY = Computerplayer.waitY + (countY * 100);
						DrawImages.enemyUnits.elementAt(i).moving = true;
						distanceRight = distanceRight + DrawImages.enemyUnits.elementAt(i).width;
					} else {
						distanceLeft = distanceLeft + DrawImages.enemyUnits.elementAt(i).width;
						DrawImages.enemyUnits.elementAt(
								i).moveToX = (int) (Computerplayer.waitX - ((((countX + 1) / 2) * 20)) - distanceLeft);
						DrawImages.enemyUnits.elementAt(i).moveToY = Computerplayer.waitY + (countY * 100);
						DrawImages.enemyUnits.elementAt(i).moving = true;
					}
					countX++;
					if (countX == 5) {
						countY++;
						countX = 0;
						distanceLeft = 0;
						distanceRight = 0;
					}
				}

			}
		}
	}
}

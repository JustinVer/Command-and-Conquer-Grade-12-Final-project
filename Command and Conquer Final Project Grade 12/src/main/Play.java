package main;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class for Key input
 * 
 * @author Justin
 *
 */
class keyEvent implements KeyListener {
	@Override

	public void keyPressed(KeyEvent E) {
		char key = E.getKeyChar();
		// If one of the direction keys are pressed set directionX and directionY to the
		// correct value
		if ("w".equals(Character.toString(key))) {
			Play.directionY = 1;
			Play.directionX = 0;
			Play.pressedY = true;
		}
		if ("s".equals(Character.toString(key))) {
			Play.directionY = 2;
			Play.directionX = 0;
			Play.pressedY = true;
		}
		if ("a".equals(Character.toString(key))) {
			Play.directionX = 1;
			Play.directionY = 0;
			Play.pressedX = true;
		}
		if ("d".equals(Character.toString(key))) {
			Play.directionX = 2;
			Play.directionY = 0;
			Play.pressedX = true;
		}
		// If any of the number keys are hit either save the selected units or select
		// the units associated with that number
		if ("1".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(1);
			} else {
				Selection.getSelected(1);
			}
		}
		if ("2".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(2);
			} else {
				Selection.getSelected(2);
			}
		}
		if ("3".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(3);
			} else {
				Selection.getSelected(3);
			}
		}
		if ("4".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(4);
			} else {
				Selection.getSelected(4);
			}
		}
		if ("5".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(5);
			} else {
				Selection.getSelected(5);
			}
		}
		if ("6".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(6);
			} else {
				Selection.getSelected(6);
			}
		}
		if ("7".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(7);
			} else {
				Selection.getSelected(7);
			}
		}
		if ("8".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(8);
			} else {
				Selection.getSelected(8);
			}
		}
		if ("9".equals(Character.toString(key))) {
			if (Selection.tryingToSave == true) {
				Selection.saveSelected(9);
			} else {
				Selection.getSelected(9);
			}
		}
		// If z is pressed set tryingToSave to true else set to false
		if ("z".equals(Character.toString(key))) {
			Selection.tryingToSave = true;
		} else {
			Selection.tryingToSave = false;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}

/**
 * Class for the timer that repaints and sets the frame
 * 
 * @author Justin
 *
 */
class TimerListener implements ActionListener {

	@Override
	// When timer is triggered do all of the actions that are done every frame
	public void actionPerformed(ActionEvent e) {

		Play.ai.aiBuild();
		Play.ai.aiAttack();
		// Increase some counts
		Play.count++;
		// Checks for chunk distance for moving
		if (DrawImages.count == DrawImages.chunkDistance) {
			Play.pressedY = false;
			Play.pressedX = false;
		}
		// Gets the mouse location
		Play.mouseX = ((int) MouseInfo.getPointerInfo().getLocation().getX()) - ((int) Play.frame.getX() + 8);
		Play.mouseY = ((int) MouseInfo.getPointerInfo().getLocation().getY()) - ((int) Play.frame.getY() + 31);
		// Sets the location of the buttons
		if (Play.count == 5) {
			for (int i = 0; i < Button.buttons.size(); i++) {
				Button.buttons.get(i).setBounds(DrawImages.XY[i * 2], DrawImages.XY[(i * 2) + 1], 100, 40);
			}
		}
		// Moves units
		int speed;
		for (int i = 0; i < DrawImages.units.size(); i++) {
			if (Units.collisionDetection(DrawImages.units.elementAt(i).x - DrawImages.units.elementAt(i).speed - 1,
					DrawImages.units.elementAt(i).y - DrawImages.units.elementAt(i).speed - 1,
					(DrawImages.units.elementAt(i).speed * 2) + 2, (DrawImages.units.elementAt(i).speed * 2) + 2,
					DrawImages.units.elementAt(i).moveToX, 0, 1, 800) == true
					&& Units.collisionDetection(
							DrawImages.units.elementAt(i).x - DrawImages.units.elementAt(i).speed - 1,
							DrawImages.units.elementAt(i).y - DrawImages.units.elementAt(i).speed - 1,
							(DrawImages.units.elementAt(i).speed * 2) + 2,
							(DrawImages.units.elementAt(i).speed * 2) + 2, 0, DrawImages.units.elementAt(i).moveToY,
							1000, 1) == true) {
				DrawImages.units.elementAt(i).moving = false;
				DrawImages.units.elementAt(i).setX(DrawImages.units.elementAt(i).moveToX);
				DrawImages.units.elementAt(i).setY(DrawImages.units.elementAt(i).moveToY);
			} else if (Units.collisionDetection(
					DrawImages.units.elementAt(i).x - DrawImages.units.elementAt(i).speed - 1,
					DrawImages.units.elementAt(i).y - DrawImages.units.elementAt(i).speed - 1,
					(DrawImages.units.elementAt(i).speed * 2) + 2, (DrawImages.units.elementAt(i).speed * 2) + 2,
					DrawImages.units.elementAt(i).moveToX, 0, 1, 800) == true) {
				DrawImages.units.elementAt(i).setX(DrawImages.units.elementAt(i).moveToX);
				DrawImages.units.elementAt(i).directionX = "";
			} else if (Units.collisionDetection(
					DrawImages.units.elementAt(i).x - DrawImages.units.elementAt(i).speed - 1,
					DrawImages.units.elementAt(i).y - DrawImages.units.elementAt(i).speed - 1,
					(DrawImages.units.elementAt(i).speed * 2) + 2, (DrawImages.units.elementAt(i).speed * 2) + 2, 0,
					DrawImages.units.elementAt(i).moveToY, 1000, 1) == true) {
				DrawImages.units.elementAt(i).setY(DrawImages.units.elementAt(i).moveToY);
				DrawImages.units.elementAt(i).directionY = "";
			}

			// Collect resources if harvester
			if (DrawImages.units.elementAt(i).name.equals("Harvester")) {
				DrawImages.units.elementAt(i).collectResources(0);
			}
			// Sets the speed slower if going on diagonal so things don't go to fast on
			// diagonals
			if (DrawImages.units.elementAt(i).moveToX == DrawImages.units.elementAt(i).x
					|| DrawImages.units.elementAt(i).moveToY == DrawImages.units.elementAt(i).y) {
				speed = DrawImages.units.elementAt(i).speed;
			} else {
				speed = (int) ((DrawImages.units.elementAt(i).speed) / (1.25));
			}
			// Figures out wich direction the unit should go then sets the direction
			// variable and moves the unit
			if (DrawImages.units.elementAt(i).moveToX == DrawImages.units.elementAt(i).x
					&& DrawImages.units.elementAt(i).moveToY == DrawImages.units.elementAt(i).y) {
				DrawImages.units.elementAt(i).moving = false;
			} else if (DrawImages.units.elementAt(i).moving == true
					&& DrawImages.units.elementAt(i).x < DrawImages.units.elementAt(i).moveToX) {
				DrawImages.units.elementAt(i).setX(DrawImages.units.elementAt(i).x + speed);
				DrawImages.units.elementAt(i).directionX = "East";
			} else if (DrawImages.units.elementAt(i).moving == true
					&& DrawImages.units.elementAt(i).x > DrawImages.units.elementAt(i).moveToX) {
				DrawImages.units.elementAt(i).setX(DrawImages.units.elementAt(i).x - speed);
				DrawImages.units.elementAt(i).directionX = "West";
			} else if (DrawImages.units.elementAt(i).moving == true) {
				DrawImages.units.elementAt(i).directionX = "";
			}
			if (DrawImages.units.elementAt(i).moving == true
					&& DrawImages.units.elementAt(i).y < DrawImages.units.elementAt(i).moveToY) {
				DrawImages.units.elementAt(i).setY(DrawImages.units.elementAt(i).y + speed);
				DrawImages.units.elementAt(i).directionY = "South";
			} else if (DrawImages.units.elementAt(i).moving == true
					&& DrawImages.units.elementAt(i).y > DrawImages.units.elementAt(i).moveToY) {
				DrawImages.units.elementAt(i).setY(DrawImages.units.elementAt(i).y - speed);
				DrawImages.units.elementAt(i).directionY = "North";
			} else if (DrawImages.units.elementAt(i).moving == true) {
				DrawImages.units.elementAt(i).directionY = "";
			}

			// Does collision with buildings
			DrawImages.units.elementAt(i).collisionUnitsWithBuildings();
			// Does collision with units
			// DrawImages.units.elementAt(i).collisionUnitsWithUnits(i);

			// Adjusts the movement depending on collision with buildings
			if (DrawImages.units.elementAt(i).newDirectionXBuildings == true
					&& DrawImages.units.elementAt(i).newDirectionXCountBuildings < Units.moveDistanceBuildings) {
				DrawImages.units.elementAt(i).directionX = DrawImages.units.elementAt(i).newDirectionXDBuildings;
				DrawImages.units.elementAt(i).directionY = "";
				if (DrawImages.units.elementAt(i).directionX.equals("West")) {
					DrawImages.units.elementAt(i)
							.setX(DrawImages.units.elementAt(i).x - DrawImages.units.elementAt(i).speed * 2);
				} else if (DrawImages.units.elementAt(i).directionX.equals("East")) {
					DrawImages.units.elementAt(i)
							.setX(DrawImages.units.elementAt(i).x + DrawImages.units.elementAt(i).speed * 2);
				}
			} else if (DrawImages.units.elementAt(i).newDirectionXBuildings == true) {
				DrawImages.units.elementAt(i).newDirectionXBuildings = false;
			}

			if (DrawImages.units.elementAt(i).newDirectionYBuildings == true
					&& DrawImages.units.elementAt(i).newDirectionYCountBuildings < Units.moveDistanceBuildings) {
				DrawImages.units.elementAt(i).directionY = DrawImages.units.elementAt(i).newDirectionYDBuildings;
				DrawImages.units.elementAt(i).directionX = "";
				if (DrawImages.units.elementAt(i).directionY.equals("North")) {
					DrawImages.units.elementAt(i)
							.setY(DrawImages.units.elementAt(i).y - DrawImages.units.elementAt(i).speed * 2);
				} else if (DrawImages.units.elementAt(i).directionY.equals("South")) {
					DrawImages.units.elementAt(i)
							.setY(DrawImages.units.elementAt(i).y + DrawImages.units.elementAt(i).speed * 2);
				}
			} else if (DrawImages.units.elementAt(i).newDirectionYBuildings == true) {
				DrawImages.units.elementAt(i).newDirectionYBuildings = false;
			}
			DrawImages.units.elementAt(i).newDirectionXCountBuildings++;
			DrawImages.units.elementAt(i).newDirectionYCountBuildings++;
			// Adjusts the movement depending on collision with Units
			if (DrawImages.units.elementAt(i).newDirectionXUnits == true
					&& DrawImages.units.elementAt(i).newDirectionXCountUnits < Units.moveDistanceUnits) {
				if (DrawImages.units.elementAt(i).newDirectionXUnits == true
						&& DrawImages.units.elementAt(i).newDirectionXCountUnits < Units.moveDistanceUnits) {
					DrawImages.units.elementAt(i).directionX = DrawImages.units.elementAt(i).newDirectionXDUnits;
					DrawImages.units.elementAt(i).directionY = "";
					if (DrawImages.units.elementAt(i).directionX.equals("West")) {
						DrawImages.units.elementAt(i)
								.setX(DrawImages.units.elementAt(i).x - DrawImages.units.elementAt(i).speed);
					} else if (DrawImages.units.elementAt(i).directionX.equals("East")) {
						DrawImages.units.elementAt(i)
								.setX(DrawImages.units.elementAt(i).x + DrawImages.units.elementAt(i).speed);
					}
				} else if (DrawImages.units.elementAt(i).newDirectionXUnits == true) {
					DrawImages.units.elementAt(i).newDirectionXUnits = false;
				}

				if (DrawImages.units.elementAt(i).newDirectionYUnits == true
						&& DrawImages.units.elementAt(i).newDirectionYCountUnits < Units.moveDistanceUnits) {
					DrawImages.units.elementAt(i).directionY = DrawImages.units.elementAt(i).newDirectionYDUnits;
					DrawImages.units.elementAt(i).directionX = "";
					if (DrawImages.units.elementAt(i).directionY.equals("North")) {
						DrawImages.units.elementAt(i)
								.setY(DrawImages.units.elementAt(i).y - DrawImages.units.elementAt(i).speed);
					} else if (DrawImages.units.elementAt(i).directionY.equals("South")) {
						DrawImages.units.elementAt(i)
								.setY(DrawImages.units.elementAt(i).y + DrawImages.units.elementAt(i).speed);
					}
				} else if (DrawImages.units.elementAt(i).newDirectionYUnits == true) {
					DrawImages.units.elementAt(i).newDirectionYUnits = false;
				}
				DrawImages.units.elementAt(i).newDirectionXCountUnits++;
				DrawImages.units.elementAt(i).newDirectionYCountUnits++;
			}
			// Attacks
			DrawImages.units.elementAt(i).attack(0);

			if (DrawImages.units.elementAt(i).directionX.equals("")
					&& DrawImages.units.elementAt(i).directionY.equals("")) {
				DrawImages.units.elementAt(i).directionX = "North";
			}
		}

		// Moves the enemy units
		for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
			if (Units.collisionDetection(
					DrawImages.enemyUnits.elementAt(i).x - DrawImages.enemyUnits.elementAt(i).speed - 1,
					DrawImages.enemyUnits.elementAt(i).y - DrawImages.enemyUnits.elementAt(i).speed - 1,
					(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2,
					(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2, DrawImages.enemyUnits.elementAt(i).moveToX, 0,
					1, 800) == true
					&& Units.collisionDetection(
							DrawImages.enemyUnits.elementAt(i).x - DrawImages.enemyUnits.elementAt(i).speed - 1,
							DrawImages.enemyUnits.elementAt(i).y - DrawImages.enemyUnits.elementAt(i).speed - 1,
							(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2,
							(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2, 0,
							DrawImages.enemyUnits.elementAt(i).moveToY, 1000, 1) == true) {
				DrawImages.enemyUnits.elementAt(i).setX(DrawImages.enemyUnits.elementAt(i).moveToX);
				DrawImages.enemyUnits.elementAt(i).setY(DrawImages.enemyUnits.elementAt(i).moveToY);
				DrawImages.enemyUnits.elementAt(i).moving = false;
			} else if (Units.collisionDetection(
					DrawImages.enemyUnits.elementAt(i).x - DrawImages.enemyUnits.elementAt(i).speed - 1,
					DrawImages.enemyUnits.elementAt(i).y - DrawImages.enemyUnits.elementAt(i).speed - 1,
					(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2,
					(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2, DrawImages.enemyUnits.elementAt(i).moveToX, 0,
					1, 800) == true) {
				DrawImages.enemyUnits.elementAt(i).setX(DrawImages.enemyUnits.elementAt(i).moveToX);
				DrawImages.enemyUnits.elementAt(i).directionX = "";
			} else if (Units.collisionDetection(
					DrawImages.enemyUnits.elementAt(i).x - DrawImages.enemyUnits.elementAt(i).speed - 1,
					DrawImages.enemyUnits.elementAt(i).y - DrawImages.enemyUnits.elementAt(i).speed - 1,
					(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2,
					(DrawImages.enemyUnits.elementAt(i).speed * 2) + 2, 0, DrawImages.enemyUnits.elementAt(i).moveToY,
					1000, 1) == true) {
				DrawImages.enemyUnits.elementAt(i).setY(DrawImages.enemyUnits.elementAt(i).moveToY);
				DrawImages.enemyUnits.elementAt(i).directionY = "";
			}
			// Collect resources if harvester
			if (DrawImages.enemyUnits.elementAt(i).name.equals("HarvesterRed")) {
				DrawImages.enemyUnits.elementAt(i).collectResources(1);
			}
			if (DrawImages.enemyUnits.elementAt(i).moveToX == DrawImages.enemyUnits.elementAt(i).x
					&& DrawImages.enemyUnits.elementAt(i).moveToY == DrawImages.enemyUnits.elementAt(i).y) {
				speed = DrawImages.enemyUnits.elementAt(i).speed;
			} else {
				speed = (int) ((DrawImages.enemyUnits.elementAt(i).speed) / (1.25));
			}
			if (DrawImages.enemyUnits.elementAt(i).moveToX == DrawImages.enemyUnits.elementAt(i).x
					&& DrawImages.enemyUnits.elementAt(i).moveToY == DrawImages.enemyUnits.elementAt(i).y) {
				DrawImages.enemyUnits.elementAt(i).moving = false;
			} else if (DrawImages.enemyUnits.elementAt(i).moving == true
					&& DrawImages.enemyUnits.elementAt(i).x < DrawImages.enemyUnits.elementAt(i).moveToX) {
				DrawImages.enemyUnits.elementAt(i).setX(DrawImages.enemyUnits.elementAt(i).x + speed);
				DrawImages.enemyUnits.elementAt(i).directionX = "East";
			} else if (DrawImages.enemyUnits.elementAt(i).moving == true
					&& DrawImages.enemyUnits.elementAt(i).x > DrawImages.enemyUnits.elementAt(i).moveToX) {
				DrawImages.enemyUnits.elementAt(i).setX(DrawImages.enemyUnits.elementAt(i).x - speed);
				DrawImages.enemyUnits.elementAt(i).directionX = "West";
			} else if (DrawImages.enemyUnits.elementAt(i).moving == true) {
				DrawImages.enemyUnits.elementAt(i).directionX = "";
			}
			if (DrawImages.enemyUnits.elementAt(i).moving == true
					&& DrawImages.enemyUnits.elementAt(i).y < DrawImages.enemyUnits.elementAt(i).moveToY) {
				DrawImages.enemyUnits.elementAt(i).setY(DrawImages.enemyUnits.elementAt(i).y + speed);
				DrawImages.enemyUnits.elementAt(i).directionY = "South";
			} else if (DrawImages.enemyUnits.elementAt(i).moving == true
					&& DrawImages.enemyUnits.elementAt(i).y > DrawImages.enemyUnits.elementAt(i).moveToY) {
				DrawImages.enemyUnits.elementAt(i).setY(DrawImages.enemyUnits.elementAt(i).y - speed);
				DrawImages.enemyUnits.elementAt(i).directionY = "North";
			} else if (DrawImages.enemyUnits.elementAt(i).moving == true) {
				DrawImages.enemyUnits.elementAt(i).directionY = "";
			}

			// Does collision with buildings

			DrawImages.enemyUnits.elementAt(i).collisionUnitsWithBuildings();

			// Does collision with units
			// DrawImages.enemyUnits.elementAt(i).collisionUnitsWithUnits(i);

			// Adjusts the movement depending on collision with buildings
			if (DrawImages.enemyUnits.elementAt(i).newDirectionXBuildings == true
					&& DrawImages.enemyUnits.elementAt(i).newDirectionXCountBuildings < Units.moveDistanceBuildings) {
				DrawImages.enemyUnits.elementAt(i).directionX = DrawImages.enemyUnits
						.elementAt(i).newDirectionXDBuildings;
				DrawImages.enemyUnits.elementAt(i).directionY = "";
				if (DrawImages.enemyUnits.elementAt(i).directionX.equals("West")) {
					DrawImages.enemyUnits.elementAt(i)
							.setX(DrawImages.enemyUnits.elementAt(i).x - DrawImages.enemyUnits.elementAt(i).speed * 2);
				} else if (DrawImages.enemyUnits.elementAt(i).directionX.equals("East")) {
					DrawImages.enemyUnits.elementAt(i)
							.setX(DrawImages.enemyUnits.elementAt(i).x + DrawImages.enemyUnits.elementAt(i).speed * 2);
				}
			} else if (DrawImages.enemyUnits.elementAt(i).newDirectionXBuildings == true) {
				DrawImages.enemyUnits.elementAt(i).newDirectionXBuildings = false;
			}

			if (DrawImages.enemyUnits.elementAt(i).newDirectionYBuildings == true
					&& DrawImages.enemyUnits.elementAt(i).newDirectionYCountBuildings < Units.moveDistanceBuildings) {
				DrawImages.enemyUnits.elementAt(i).directionY = DrawImages.enemyUnits
						.elementAt(i).newDirectionYDBuildings;
				DrawImages.enemyUnits.elementAt(i).directionX = "";
				if (DrawImages.enemyUnits.elementAt(i).directionY.equals("North")) {
					DrawImages.enemyUnits.elementAt(i)
							.setY(DrawImages.enemyUnits.elementAt(i).y - DrawImages.enemyUnits.elementAt(i).speed * 2);
				} else if (DrawImages.enemyUnits.elementAt(i).directionY.equals("South")) {
					DrawImages.enemyUnits.elementAt(i)
							.setY(DrawImages.enemyUnits.elementAt(i).y + DrawImages.enemyUnits.elementAt(i).speed * 2);
				}
			} else if (DrawImages.enemyUnits.elementAt(i).newDirectionYBuildings == true) {
				DrawImages.enemyUnits.elementAt(i).newDirectionYBuildings = false;
			}
			DrawImages.enemyUnits.elementAt(i).newDirectionXCountBuildings++;
			DrawImages.enemyUnits.elementAt(i).newDirectionYCountBuildings++;
			// Attacks
			DrawImages.enemyUnits.elementAt(i).attack(1);

			if (DrawImages.enemyUnits.elementAt(i).directionX.equals("")
					&& DrawImages.enemyUnits.elementAt(i).directionY.equals("")) {
				DrawImages.enemyUnits.elementAt(i).directionX = "North";
			}
		}

		// Adjusts the queue for all of the buildings
		for (int i = 0; i < DrawImages.buildings.size(); i++) {
			if (DrawImages.buildings.elementAt(i).queue.size() > 0) {
				DrawImages.buildings.elementAt(i).queueTime++;
				DrawImages.buildings.elementAt(i).createUnitFromQueue(0);
			}
		}
		// Adjusts the queue for all of the buildings
		for (int i = 0; i < DrawImages.enemyBuildings.size(); i++) {
			if (DrawImages.enemyBuildings.elementAt(i).queue.size() > 0) {
				DrawImages.enemyBuildings.elementAt(i).queueTime++;
				DrawImages.enemyBuildings.elementAt(i).createUnitFromQueue(1);
			}
		}
		for (int i = 0; i < DrawImages.buildings.size(); i++) {
			if (DrawImages.buildings.elementAt(i).name.equals("Turret")) {
				DrawImages.buildings.elementAt(i).attack(0);
			}
		}
		for (int i = 0; i < DrawImages.enemyBuildings.size(); i++) {
			if (DrawImages.enemyBuildings.elementAt(i).name.equals("TurretRed")) {
				DrawImages.enemyBuildings.elementAt(i).attack(1);
			}
		}
		// Repaints all of the images
		Play.images.repaint();

	}
}

/**
 * Class for Mouse input
 * 
 * @author Justin
 *
 */
class MouseEvent implements MouseListener {
	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// Left click
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
			Play.click = 0;

		}
		// Right click
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
			Play.click = 2;
		}
		// Middle click
		if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			Play.click = 1;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// Checks for collision with buildings
		DrawImages.collision = false;
		DrawImages.collisionIndex = -1;
		Buildings.collision(DrawImages.buildings);
		// left click
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
			Play.click = 0;
		}
		// right click
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
			Play.click = 2;
		}
		// middle click
		if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			Play.click = 1;
		}
		// Gets things for selection box
		Play.selection = true;
		Play.selectionX = Play.mouseX;
		Play.selectionY = Play.mouseY;
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// Sets the width and height for selection box
		Play.selectionW = Play.mouseX - Play.selectionX;
		Play.selectionH = Play.mouseY - Play.selectionY;

		// Depending on what, and were the mouse was clicked and released, select Units,
		// move units, or deselect units
		if (Play.selectionX - Play.mouseX > 2 || Play.selectionX - Play.mouseX < -2) {
			Selection.Selected(DrawImages.units);

		} else if (Buildings.collisionReturn(DrawImages.buildings) == false && Play.clicked == 0
				&& Button.clicked.equals(" ")) {
			Selection.move();
		} else {
			Selection.selected.removeAllElements();
		}
		// Resets the selection box
		DrawImages.selectionRecX = 0;
		DrawImages.selectionRecY = 0;
		DrawImages.selectionRecW = 0;
		DrawImages.selectionRecH = 0;
		Play.selection = false;
		Play.clicked = 5;
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {

	}

}

/**
 * Class for Main
 * 
 * @author Justin
 *
 */
public class Play {

	// Creates variables
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int directionX = 0;
	public static int directionY = 0;
	public static boolean pressedY = true;
	public static boolean pressedX = true;
	public static int grassX = -100;
	public static int grassY = -505;
	public static int grassWidth = 8000;
	public static int grassHeight = 8000;
	public static JFrame frame = new JFrame("Command and conquer");
	public static JPanel panel = new JPanel();
	public static DrawImages images = new DrawImages();
	public static Selection select = new Selection();
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static int click = 5;
	public static int clicked = 5;
	public static int count = 0;
	public static boolean selection = false;
	public static int selectionX;
	public static int selectionY;
	public static int selectionW;
	public static int selectionH;
	public static int resourceCount = 10000;
	public static int resourceCountRed = 10000;
	public static int harvesterCountRed = 0;

	public static int militaryCountRed = 0;
	public static Computerplayer ai = new Computerplayer();
	public static ResourcesPlacement placement = new ResourcesPlacement();

	public static boolean refineryBuilt = false;
	public static boolean startGame = false;
	public static boolean factoryBuilt = false;
	public static boolean PlayerWon = false;
	public static boolean harvesterBuilt = false;
	public static boolean militaryBuilt = false;
	public static boolean gameEnded;
	public static boolean EndGameMenuCreated;
	public static int enemyMainY = 500;
	public static int Turret = 0;
	public static int moveSpeed = 10;

	/**
	 * Main
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		// Creates the frame and adds the Key listener, mouse listener, timer, and
		// imagess
		@SuppressWarnings("unused")
		StartMenu start = new StartMenu(1);
		while (Play.startGame == false) {
			System.out.print("");
		}
		placement.Placement();
		DrawImages.buildings.add(new Buildings(100, 500, "MainBase"));

		DrawImages.enemyBuildings.add(new Buildings(5600, enemyMainY, "MainBaseRed"));
		frame.getContentPane();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new keyEvent());
		frame.addMouseListener(new MouseEvent());
		new Timer(15, new TimerListener()).start();

		frame.add(images);
		frame.setVisible(true);

		while (Play.EndGameMenuCreated == false) {
			System.out.print("");
			if (Play.gameEnded == true) {
				@SuppressWarnings("unused")
				EndGameMenu endGameMenu = new EndGameMenu(1, Play.PlayerWon);
			}

		}

	}
}

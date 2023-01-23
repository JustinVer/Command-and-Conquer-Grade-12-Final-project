package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Class for drawing all of the images
 * 
 * @author Justin
 *
 */
public class DrawImages extends JPanel {

	// Declare variables
	private static final long serialVersionUID = 1L;
	private ImageIcon Ore1 = new ImageIcon(this.getClass().getResource("Ore1.png"));
	private ImageIcon tiberium1 = new ImageIcon(this.getClass().getResource("Tiberium.png"));

	private ImageIcon PowerPlant1T = new ImageIcon(this.getClass().getResource("powerPlantGreenTransparent.png"));
	private ImageIcon BarracksGT = new ImageIcon(this.getClass().getResource("BarracksTransparent.png"));
	private ImageIcon FactoryGT = new ImageIcon(this.getClass().getResource("FactoryTransparent.png"));
	private ImageIcon RefineryGT = new ImageIcon(this.getClass().getResource("RefineryTransparent.png"));
	private ImageIcon backround = new ImageIcon(this.getClass().getResource("backgorund_3.png"));
	private ImageIcon TurretTR = new ImageIcon(this.getClass().getResource("TurretTransparent.png"));

	private Image PowerPlantGreenT = PowerPlant1T.getImage();
	private Image BarracksGreenT = BarracksGT.getImage();
	private Image FactoryGreenT = FactoryGT.getImage();
	private Image RefineryGreenT = RefineryGT.getImage();
	private Image background = backround.getImage();
	private Image Ore = Ore1.getImage();
	private Image tiberium = tiberium1.getImage();
	private Image TurretT = TurretTR.getImage();

	public static int count = 0;
	public static final int chunkDistance = 10;
	public static int[] XY = { 0, Play.screenSize.height - 100, 100, Play.screenSize.height - 100, 200,
			Play.screenSize.height - 100, 000, Play.screenSize.height - 200, 000, Play.screenSize.height - 150, 200,
			Play.screenSize.height - 150, 100, Play.screenSize.height - 150, 100, Play.screenSize.height - 200, 100,
			Play.screenSize.height - 250, 100, Play.screenSize.height - 300, 300, Play.screenSize.height - 100 };
	private static String[] names = { "Barracks", "Factory", "Refinery", "Rocket", "Rifle", "Harvester", "MediumTank",
			"Artillery", "MammothTank", "Humvee", "Turret" };
	@SuppressWarnings("unused")
	private static Button buttons = new Button(XY, names);
	public static int collisionIndex = -1;
	public static boolean collision = false;
	public static String collisionType = " ";
	public static Vector<Buildings> buildings = new Vector<Buildings>();
	public static Vector<Units> units = new Vector<Units>();
	public static Vector<Buildings> enemyBuildings = new Vector<Buildings>();
	public static Vector<Units> enemyUnits = new Vector<Units>();
	public static Vector<Resources> Resources = new Vector<Resources>(500);
	public static int[] valueX = new int[10];
	public static int[] valueY = new int[10];
	public static int selectionRecX = 0;
	public static int selectionRecY = 0;
	public static int selectionRecW = 0;
	public static int selectionRecH = 0;
	public static int rCount = 0;
	public static int tiberiumX = Play.screenSize.width - 200;
	public static int tiberiumY = 0;
	public static int powerPlantCost = 750;
	public static int barracksCost = 600;
	public static int factoryCost = 1200;
	public static int refineryCost = 1500;
	public static int artilleryCost = 1200;
	public static int mediumTankCost = 1000;
	public static int harvesterCost = 1600;
	public static int HumveeCost = 900;
	public static int MammothTankCost = 2000;
	public static int turretCost = 1200;
	private static boolean edge = false;

	static int countFrame = 0;

	/**
	 * Paint method that draw all of the images and adds all of the buttons
	 */
	public void paintComponent(Graphics g) {
		// Increase count
		countFrame++;
		super.paintComponent(g);
		// Draws the background
		g.drawImage(background, Play.grassX, Play.grassY, Play.grassHeight, Play.grassWidth, this);

		// Draws the resources
		for (int i = 0; i < Resources.size(); i++) {
			g.drawImage(Ore, Resources.get(i).getX(), Resources.get(i).getY(), Resources.get(i).getWidth(),
					Resources.get(i).getHeight(), this);
		}

		// Draws the buildings
		for (int i = 0; i < buildings.size(); i++) {
			g.drawImage(buildings.get(i).getImage(), buildings.get(i).getX(), buildings.get(i).getY(),
					buildings.get(i).getWeigth(), buildings.get(i).getHeight(), this);
			DrawImages.buildings.elementAt(i).moveBullets();
			// Draws all of the bullets for the unit
			for (int j = 0; j < DrawImages.buildings.elementAt(i).bullets.size(); j++) {
				ImageIcon bullet = new ImageIcon(this.getClass()
						.getResource("Bullet" + DrawImages.buildings.elementAt(i).bullets.elementAt(j).directionY
								+ DrawImages.buildings.elementAt(i).bullets.elementAt(j).directionX + ".png"));
				Image bulletImage = bullet.getImage();
				g.drawImage(bulletImage, DrawImages.buildings.elementAt(i).bullets.elementAt(j).x,
						DrawImages.buildings.elementAt(i).bullets.elementAt(j).y,
						DrawImages.buildings.elementAt(i).bullets.elementAt(j).width,
						DrawImages.buildings.elementAt(i).bullets.elementAt(j).height, this);
			}
			if (DrawImages.buildings.elementAt(i).HP <= 0) {
				if (i == 0) {
					Play.PlayerWon = false;
					Play.gameEnded = true;
				}
				DrawImages.buildings.remove(i);
			}
		}
		// Draws the enemy buildings
		for (int i = 0; i < enemyBuildings.size(); i++) {
			g.drawImage(enemyBuildings.get(i).getImage(), enemyBuildings.get(i).getX(), enemyBuildings.get(i).getY(),
					enemyBuildings.get(i).getWeigth(), enemyBuildings.get(i).getHeight(), this);
			DrawImages.enemyBuildings.elementAt(i).moveBullets();
			// Draws all of the bullets for the unit
			for (int j = 0; j < DrawImages.enemyBuildings.elementAt(i).bullets.size(); j++) {
				ImageIcon bullet = new ImageIcon(this.getClass()
						.getResource("Bullet" + DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).directionY
								+ DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).directionX + ".png"));
				Image bulletImage = bullet.getImage();
				g.drawImage(bulletImage, DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).x,
						DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).y,
						DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).width,
						DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).height, this);
			}
			if (DrawImages.enemyBuildings.elementAt(i).HP <= 0) {
				if (i == 0) {
					Play.PlayerWon = true;
					Play.gameEnded = true;
				}
				DrawImages.enemyBuildings.remove(i);
				if (DrawImages.enemyBuildings.elementAt(i).name.equals("TurretRed")) {
					Play.Turret--;
				}
			}
		}
		// Draws the units
		for (int i = 0; i < DrawImages.units.size(); i++) {
			g.drawImage(DrawImages.units.elementAt(i).getImage(), DrawImages.units.elementAt(i).getX(),
					DrawImages.units.elementAt(i).getY(), DrawImages.units.elementAt(i).getWidth(),
					DrawImages.units.elementAt(i).getHeight(), this);
			DrawImages.units.elementAt(i).moveBullets();
			// Draws all of the bullets for the unit
			for (int j = 0; j < DrawImages.units.elementAt(i).bullets.size(); j++) {
				ImageIcon bullet = new ImageIcon(this.getClass()
						.getResource("Bullet" + DrawImages.units.elementAt(i).bullets.elementAt(j).directionY
								+ DrawImages.units.elementAt(i).bullets.elementAt(j).directionX + ".png"));
				Image bulletImage = bullet.getImage();
				g.drawImage(bulletImage, DrawImages.units.elementAt(i).bullets.elementAt(j).x,
						DrawImages.units.elementAt(i).bullets.elementAt(j).y,
						DrawImages.units.elementAt(i).bullets.elementAt(j).width,
						DrawImages.units.elementAt(i).bullets.elementAt(j).height, this);
			}
			// Checks to see if the units HP is less than zero and then deletes the unit
			// since it is now died
			if (DrawImages.units.elementAt(i).HP <= 0) {
				DrawImages.units.remove(i);
				try {
					Selection.selected.remove(i);
				} catch (ArrayIndexOutOfBoundsException e) {

				}
			}
		}

		// Draws the enemy units
		for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
			g.drawImage(DrawImages.enemyUnits.elementAt(i).getImage(), DrawImages.enemyUnits.elementAt(i).getX(),
					DrawImages.enemyUnits.elementAt(i).getY(), DrawImages.enemyUnits.elementAt(i).getWidth(),
					DrawImages.enemyUnits.elementAt(i).getHeight(), this);
			// Attacks
			DrawImages.enemyUnits.elementAt(i).attack(1);
			// Moves bullets
			DrawImages.enemyUnits.elementAt(i).moveBullets();
			// Draws bullets
			for (int j = 0; j < DrawImages.enemyUnits.elementAt(i).bullets.size(); j++) {
				ImageIcon bullet = new ImageIcon(this.getClass()
						.getResource("Bullet" + DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).directionY
								+ DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).directionX + ".png"));
				Image bulletImage = bullet.getImage();
				g.drawImage(bulletImage, DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).x,
						DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).y,
						DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).width,
						DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).height, this);
			}
			// Checks to see if the units HP is less than zero and then deletes the unit
			// since it is now dead
			if (DrawImages.enemyUnits.elementAt(i).HP <= 0) {
				if (DrawImages.enemyUnits.elementAt(i).name.equals("HarvesterRed")) {
					Play.harvesterCountRed -= 1;
				} else {
					Play.militaryCountRed -= 1;
				}
				DrawImages.enemyUnits.remove(i);

			}
		}

		// If you are not on the edge of the map changes the X or Y of everything
		// depending on the direction wanted to move.
		if (Play.grassX + 5 > 0 && Play.directionX == 1) {
			DrawImages.edge = true;
		} else if (Play.grassX + Play.grassWidth - 5 < Play.screenSize.width && Play.directionX == 2) {
			DrawImages.edge = true;
		} else if (Play.grassY + 5 > 0 && Play.directionY == 1) {
			DrawImages.edge = true;
		} else if (Play.grassY + Play.grassHeight - 5 < Play.screenSize.height && Play.directionY == 2) {
			DrawImages.edge = true;
		} else {
			DrawImages.edge = false;
		}
		if (edge == false) {
			if (Play.directionX == 1) {

				if (Play.pressedX == true) {
					Computerplayer.waitX += Play.moveSpeed;
					Play.grassX += Play.moveSpeed;
					for (int i = 0; i < buildings.size(); i++) {
						buildings.elementAt(i).setX((buildings.elementAt(i).getX()) + Play.moveSpeed);
						for (int j = 0; j < DrawImages.buildings.elementAt(i).bullets.size(); j++) {
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).x += Play.moveSpeed;
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).targetX += Play.moveSpeed;
						}
					}
					for (int i = 0; i < enemyBuildings.size(); i++) {
						enemyBuildings.elementAt(i).setX((enemyBuildings.elementAt(i).getX()) + Play.moveSpeed);
						for (int j = 0; j < DrawImages.enemyBuildings.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).x += Play.moveSpeed;
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).targetX += Play.moveSpeed;
						}
					}
					for (int i = 0; i < DrawImages.units.size(); i++) {
						DrawImages.units.elementAt(i).setX(DrawImages.units.elementAt(i).getX() + Play.moveSpeed);
						DrawImages.units.elementAt(i).moveToX = DrawImages.units.elementAt(i).moveToX + Play.moveSpeed;
						for (int j = 0; j < DrawImages.units.elementAt(i).bullets.size(); j++) {
							DrawImages.units.elementAt(i).bullets.elementAt(j).x += Play.moveSpeed;
							DrawImages.units.elementAt(i).bullets.elementAt(j).targetX += Play.moveSpeed;
						}

					}
					for (int i = 0; i < Resources.size(); i++) {
						Resources.get(i).setX((Resources.get(i).getX()) + Play.moveSpeed);
					}
					for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
						DrawImages.enemyUnits.elementAt(i)
								.setX(DrawImages.enemyUnits.elementAt(i).getX() + Play.moveSpeed);
						DrawImages.enemyUnits.elementAt(i).moveToX = DrawImages.enemyUnits.elementAt(i).moveToX
								+ Play.moveSpeed;
						for (int j = 0; j < DrawImages.enemyUnits.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).x += Play.moveSpeed;
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).targetX += Play.moveSpeed;
						}
					}
					Buildings.mouseX += Play.moveSpeed;
				}
			}
			if (Play.directionX == 2) {
				if (Play.pressedX == true) {
					Computerplayer.waitX -= Play.moveSpeed;
					Play.grassX -= Play.moveSpeed;
					for (int i = 0; i < buildings.size(); i++) {
						buildings.elementAt(i).setX((buildings.elementAt(i).getX()) - Play.moveSpeed);
						for (int j = 0; j < DrawImages.buildings.elementAt(i).bullets.size(); j++) {
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).x -= Play.moveSpeed;
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).targetX -= Play.moveSpeed;
						}
					}
					for (int i = 0; i < enemyBuildings.size(); i++) {
						enemyBuildings.elementAt(i).setX((enemyBuildings.elementAt(i).getX()) - Play.moveSpeed);
						for (int j = 0; j < DrawImages.enemyBuildings.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).x -= Play.moveSpeed;
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).targetX -= Play.moveSpeed;
						}
					}
					for (int i = 0; i < DrawImages.units.size(); i++) {
						DrawImages.units.elementAt(i).setX((DrawImages.units.elementAt(i).getX()) - Play.moveSpeed);
						DrawImages.units.elementAt(i).moveToX = DrawImages.units.elementAt(i).moveToX - Play.moveSpeed;
						for (int j = 0; j < DrawImages.units.elementAt(i).bullets.size(); j++) {
							DrawImages.units.elementAt(i).bullets.elementAt(j).x -= Play.moveSpeed;
							DrawImages.units.elementAt(i).bullets.elementAt(j).targetX -= Play.moveSpeed;
						}
					}
					for (int i = 0; i < Resources.size(); i++) {
						Resources.get(i).setX((Resources.get(i).getX()) - Play.moveSpeed);
					}
					for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
						DrawImages.enemyUnits.elementAt(i)
								.setX(DrawImages.enemyUnits.elementAt(i).getX() - Play.moveSpeed);
						DrawImages.enemyUnits.elementAt(i).moveToX = DrawImages.enemyUnits.elementAt(i).moveToX
								- Play.moveSpeed;
						for (int j = 0; j < DrawImages.enemyUnits.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).x -= Play.moveSpeed;
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).targetX -= Play.moveSpeed;
						}
					}
					Buildings.mouseX -= Play.moveSpeed;
				}
			}
			if (Play.directionY == 1) {
				if (Play.pressedY == true) {
					Computerplayer.waitY += Play.moveSpeed;
					Play.grassY += Play.moveSpeed;
					for (int i = 0; i < buildings.size(); i++) {
						buildings.elementAt(i).setY((buildings.elementAt(i).getY()) + Play.moveSpeed);
						for (int j = 0; j < DrawImages.buildings.elementAt(i).bullets.size(); j++) {
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).y += Play.moveSpeed;
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).targetY += Play.moveSpeed;
						}
					}
					for (int i = 0; i < enemyBuildings.size(); i++) {
						enemyBuildings.elementAt(i).setY((enemyBuildings.elementAt(i).getY()) + Play.moveSpeed);
						for (int j = 0; j < DrawImages.enemyBuildings.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).y += Play.moveSpeed;
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).targetY += Play.moveSpeed;
						}
					}
					for (int i = 0; i < DrawImages.units.size(); i++) {
						DrawImages.units.elementAt(i).setY((DrawImages.units.elementAt(i).getY()) + Play.moveSpeed);
						DrawImages.units.elementAt(i).moveToY = DrawImages.units.elementAt(i).moveToY + Play.moveSpeed;
						for (int j = 0; j < DrawImages.units.elementAt(i).bullets.size(); j++) {
							DrawImages.units.elementAt(i).bullets.elementAt(j).y += Play.moveSpeed;
							DrawImages.units.elementAt(i).bullets.elementAt(j).targetY += Play.moveSpeed;
						}
					}
					for (int i = 0; i < Resources.size(); i++) {
						Resources.get(i).setY((Resources.get(i).getY()) + Play.moveSpeed);
					}
					for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
						DrawImages.enemyUnits.elementAt(i)
								.setY(DrawImages.enemyUnits.elementAt(i).getY() + Play.moveSpeed);
						DrawImages.enemyUnits.elementAt(i).moveToY = DrawImages.enemyUnits.elementAt(i).moveToY
								+ Play.moveSpeed;
						for (int j = 0; j < DrawImages.enemyUnits.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).y += Play.moveSpeed;
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).targetY += Play.moveSpeed;
						}
					}
					Buildings.mouseY += Play.moveSpeed;
				}
			}
			if (Play.directionY == 2) {
				if (Play.pressedY == true) {
					Computerplayer.waitY -= Play.moveSpeed;
					Play.grassY -= Play.moveSpeed;
					for (int i = 0; i < buildings.size(); i++) {
						buildings.elementAt(i).setY((buildings.elementAt(i).getY()) - Play.moveSpeed);
						for (int j = 0; j < DrawImages.buildings.elementAt(i).bullets.size(); j++) {
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).y -= Play.moveSpeed;
							DrawImages.buildings.elementAt(i).bullets.elementAt(j).targetY -= Play.moveSpeed;
						}
					}
					for (int i = 0; i < enemyBuildings.size(); i++) {
						enemyBuildings.elementAt(i).setY((enemyBuildings.elementAt(i).getY()) - Play.moveSpeed);
						for (int j = 0; j < DrawImages.enemyBuildings.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).y -= Play.moveSpeed;
							DrawImages.enemyBuildings.elementAt(i).bullets.elementAt(j).targetY -= Play.moveSpeed;
						}
					}
					for (int i = 0; i < DrawImages.units.size(); i++) {
						DrawImages.units.elementAt(i).setY((DrawImages.units.elementAt(i).getY()) - Play.moveSpeed);
						DrawImages.units.elementAt(i).moveToY = DrawImages.units.elementAt(i).moveToY - Play.moveSpeed;
						for (int j = 0; j < DrawImages.units.elementAt(i).bullets.size(); j++) {
							DrawImages.units.elementAt(i).bullets.elementAt(j).y -= Play.moveSpeed;
							DrawImages.units.elementAt(i).bullets.elementAt(j).targetY -= Play.moveSpeed;
						}
					}
					for (int i = 0; i < Resources.size(); i++) {
						Resources.get(i).setY((Resources.get(i).getY()) - Play.moveSpeed);
					}
					for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
						DrawImages.enemyUnits.elementAt(i)
								.setY(DrawImages.enemyUnits.elementAt(i).getY() - Play.moveSpeed);
						DrawImages.enemyUnits.elementAt(i).moveToY = DrawImages.enemyUnits.elementAt(i).moveToY
								- Play.moveSpeed;
						for (int j = 0; j < DrawImages.enemyUnits.elementAt(i).bullets.size(); j++) {
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).y -= Play.moveSpeed;
							DrawImages.enemyUnits.elementAt(i).bullets.elementAt(j).targetY -= Play.moveSpeed;
						}
					}
					Buildings.mouseY -= Play.moveSpeed;

				}
			}
		}
		count++;
		// Moves until the chunk distance
		if (count == chunkDistance) {
			Play.pressedY = false;
			Play.pressedX = false;
			count = 0;
		}

		// Checks if the buttons where clicked and if so do the required thing (usually
		// buildings something)
		if (Button.clicked.equals("PowerPlant")) {
			g.drawImage(PowerPlantGreenT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false) {
				if (Play.resourceCount >= powerPlantCost) {
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "powerPlantGreen"));
					Play.resourceCount -= powerPlantCost;
				}
				Button.clicked = " ";
			} else if (Play.click == 0) {
				Button.clicked = " ";
			}
		}
		if (Button.clicked.equals("Barracks")) {
			g.drawImage(BarracksGreenT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false) {
				if (Play.resourceCount >= barracksCost) {
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "barracks"));
					Play.resourceCount -= barracksCost;
				}
				Button.clicked = " ";
			} else if (Play.click == 0) {
				Button.clicked = " ";
			}
		}
		if (Button.clicked.equals("Factory")) {
			g.drawImage(FactoryGreenT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false) {
				if (Play.resourceCount >= factoryCost) {
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "factory"));
					Play.resourceCount -= factoryCost;
				}
				Button.clicked = " ";
			} else if (Play.click == 0) {
				Button.clicked = " ";
			}
		}
		if (Button.clicked.equals("Refinery")) {
			g.drawImage(RefineryGreenT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false) {
				if (Play.resourceCount >= refineryCost) {
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "refinery"));
					Play.resourceCount -= refineryCost;
				}
				Button.clicked = " ";
			} else if (Play.click == 0) {
				Button.clicked = " ";
			}
		}

		if (Button.clicked.equals("Turret")) {
			g.drawImage(TurretT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false) {
				if (Play.resourceCount >= turretCost) {
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "Turret"));
					Play.resourceCount -= turretCost;
				}
				Button.clicked = " ";
			} else if (Play.click == 0) {
				Button.clicked = " ";
			}
		}

		if (Button.clicked.equals("Harvester")) {
			if (Play.resourceCount >= harvesterCost) {
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"Harvester");
				Play.resourceCount -= harvesterCost;
			}
			Button.clicked = " ";

		}
		if (Button.clicked.equals("MediumTank")) {
			if (Play.resourceCount >= mediumTankCost) {
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"MediumTank");
				Play.resourceCount -= mediumTankCost;
			}
			Button.clicked = " ";
		}
		if (Button.clicked.equals("Artillery")) {
			if (Play.resourceCount >= artilleryCost) {
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"Artillery");
				Play.resourceCount -= artilleryCost;
			}
			Button.clicked = " ";
		}
		if (Button.clicked.equals("Humvee")) {
			if (Play.resourceCount >= HumveeCost) {
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"Humvee");
				Play.resourceCount -= HumveeCost;
			}
			Button.clicked = " ";
		}
		if (Button.clicked.equals("MammothTank")) {
			if (Play.resourceCount >= MammothTankCost) {
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"MammothTank");
				Play.resourceCount -= MammothTankCost;
			}
			Button.clicked = " ";
		}
		// Adds all of the buttons to the frame
		for (int i = 0; i < Button.buttons.size(); i++) {
			add(Button.buttons.get(i));
		}

		// If the correct building is pressed display the required buttons (if the
		// factory is clicked on show the units the factory can build)
		if (DrawImages.collision == true && DrawImages.collisionType.equals("barracks")) {
			Button.buttons.get(3).setLocation(DrawImages.XY[6], DrawImages.XY[7]);
			Button.buttons.get(4).setLocation(DrawImages.XY[8], DrawImages.XY[9]);
		} else {
			Button.buttons.get(3).setLocation(-100, -100);
			Button.buttons.get(4).setLocation(-100, -100);
		}
		if (DrawImages.collision == true && DrawImages.collisionType.equals("refinery")) {
			Button.buttons.get(5).setLocation(DrawImages.XY[10], DrawImages.XY[11]);
		} else {
			Button.buttons.get(5).setLocation(-100, -100);
		}
		if (DrawImages.collision == true && DrawImages.collisionType.equals("factory")) {
			Button.buttons.get(6).setLocation(DrawImages.XY[12], DrawImages.XY[13]);
			Button.buttons.get(7).setLocation(DrawImages.XY[14], DrawImages.XY[15]);
			Button.buttons.get(8).setLocation(DrawImages.XY[16], DrawImages.XY[17]);
			Button.buttons.get(9).setLocation(DrawImages.XY[18], DrawImages.XY[19]);
		} else {
			Button.buttons.get(6).setLocation(-100, -100);
			Button.buttons.get(7).setLocation(-100, -100);
			Button.buttons.get(8).setLocation(-100, -100);
			Button.buttons.get(9).setLocation(-100, -100);
		}
		// Draws the selection box
		if (Play.selection == true) {
			g.setColor(new Color(0, 0, 0));
			if (Play.mouseX - Play.selectionX > 0 && Play.mouseY - Play.selectionY > 0) {
				DrawImages.selectionRecX = Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY;
				DrawImages.selectionRecW = Play.mouseX - Play.selectionX;
				DrawImages.selectionRecH = Play.mouseY - Play.selectionY;
			} else if (Play.mouseX - Play.selectionX < 0 && Play.mouseY - Play.selectionY < 0) {
				DrawImages.selectionRecX = Play.selectionX + Play.mouseX - Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY + Play.mouseY - Play.selectionY;
				DrawImages.selectionRecW = -1 * (Play.mouseX - Play.selectionX);
				DrawImages.selectionRecH = -1 * (Play.mouseY - Play.selectionY);

			} else if (Play.mouseX - Play.selectionX > 0 && Play.mouseY - Play.selectionY < 0) {
				DrawImages.selectionRecX = Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY + Play.mouseY - Play.selectionY;
				DrawImages.selectionRecW = Play.mouseX - Play.selectionX;
				DrawImages.selectionRecH = -1 * (Play.mouseY - Play.selectionY);

			} else if (Play.mouseX - Play.selectionX < 0 && Play.mouseY - Play.selectionY > 0) {
				DrawImages.selectionRecX = Play.selectionX + Play.mouseX - Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY;
				DrawImages.selectionRecW = -1 * (Play.mouseX - Play.selectionX);
				DrawImages.selectionRecH = Play.mouseY - Play.selectionY;

			}
			g.drawRect(DrawImages.selectionRecX, DrawImages.selectionRecY, DrawImages.selectionRecW,
					DrawImages.selectionRecH);
		}
		if (Play.click == 0 || Play.click == 1 || Play.click == 2) {
			Play.clicked = Play.click;
		}

		Play.click = 5;
		// Draws the resource counter
		g.drawImage(tiberium, tiberiumX, tiberiumY, 50, 50, this);
		g.setColor(new Color(250, 250, 250));
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		g.drawString(Integer.toString(Play.resourceCount), tiberiumX + 60, tiberiumY + 25);
	}

}

package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Justin Verhoog
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

	private Image PowerPlantGreenT = PowerPlant1T.getImage();
	private Image BarracksGreenT = BarracksGT.getImage();
	private Image FactoryGreenT = FactoryGT.getImage();
	private Image RefineryGreenT = RefineryGT.getImage();
	private Image background = backround.getImage();
	private Image Ore = Ore1.getImage();
	private Image tiberium = tiberium1.getImage();

	public static int count = 0;
	public static final int chunkDistance = 10;
	public static int[] XY = { 0, 700, 100, 700, 200, 700, 300, 700, 100, 600, 100, 650, 300, 650, 200, 650, 200, 600 };
	private static String[] names = { "PowerPlant", "Barracks", "Factory", "Refinery", "Rocket", "Rifle", "Harvester",
			"MediumTank", "Artillery" };
	@SuppressWarnings("unused")
	private static Button buttons = new Button(XY, names);
	public static int collisionIndex = -1;
	public static boolean collision = false;
	public static String collisionType = " ";
	public static Vector<Buildings> buildings = new Vector<Buildings>();
	public static Vector<Units> units = new Vector<Units>();
	public static Vector<Units> enemyUnits = new Vector<Units>();
	public static Vector<Resources> Resources = new Vector<Resources>(500);
	public static int[] valueX = new int[10];
	public static int[] valueY = new int[10];
	public static int selectionRecX = 0;
	public static int selectionRecY = 0;
	public static int selectionRecW = 0;
	public static int selectionRecH = 0;
	public static int rCount = 0;
	public static int tiberiumX = 800;
	public static int tiberiumY = 0;
	public static int powerPlantCost = 750;
	public static int barracksCost = 600;
	public static int factoryCost = 1200;
	public static int refineryCost = 1500;
	public static int artilleryCost = 1200;
	public static int mediumTankCost = 1000;
	public static int harvesterCost = 1600;

	int countFrame = 0;

	// Paint method that draws images
	public void paintComponent(Graphics g) {
		// Increase count
		countFrame++;
		super.paintComponent(g);
		// Draws the background
		g.drawImage(background, Play.grassX, Play.grassY, 8000, 8000, this);
		// Draws the resource counter
		g.drawImage(tiberium, tiberiumX, tiberiumY, 50, 50, this);
		g.setColor(new Color(250, 250, 250));
		g.setFont(new Font("Dialog", Font.PLAIN, 20));
		g.drawString(Integer.toString(Play.resourceCount), tiberiumX + 60, tiberiumY + 25);

		// Creates the resources for the game that are semi-randomly placed on the map
		if (this.countFrame == 1) {
			for (int i = 0; i < 4; i++) {
				int x = i * 200;
				int y = i * 300;
				DrawImages.Resources.add(new Resources(x, y));
			}
			for (int i = 4; i < 500; i++) {
				int x = Play.grassX + (int) (Math.random() * 8000 + Play.grassX);
				int y = Play.grassY + (int) (Math.random() * 8000 + Play.grassY);
				DrawImages.Resources.add(new Resources(x, y));
			}
		}
		// Draws the resources
		for (int i = 0; i < Resources.size(); i++) {
			g.drawImage(Ore, Resources.get(i).getX(), Resources.get(i).getY(), Resources.get(i).getWidth(),
					Resources.get(i).getHeight(), this);
		}

		// Draws the buildings
		for (int i = 0; i < buildings.size(); i++) {
			g.drawImage(buildings.get(i).getName(), buildings.get(i).getX(), buildings.get(i).getY(),
					buildings.get(i).getWeigth(), buildings.get(i).getHeight(), this);
		}
		// Draws the units
		for (int i = 0; i < DrawImages.units.size(); i++) {
			g.drawImage(DrawImages.units.elementAt(i).getImage(), DrawImages.units.elementAt(i).getX(),
					DrawImages.units.elementAt(i).getY(), DrawImages.units.elementAt(i).getWidth(),
					DrawImages.units.elementAt(i).getHeight(), this);
			// Draws rectangle around unit for tests
			g.drawRect(DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
					DrawImages.units.elementAt(i).getWidth(), DrawImages.units.elementAt(i).getHeight());
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
				Selection.selected.remove(i);
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
				DrawImages.enemyUnits.remove(i);
			}
		}

		// Changes the X or Y of everything depending on the direction wanted to move
		if (Play.directionX == 1) {
			if (Play.pressedX == true) {
				Play.grassX += 5;
				for (int i = 0; i < buildings.size(); i++) {
					buildings.elementAt(i).setX((buildings.elementAt(i).getX()) + 5);
				}
				for (int i = 0; i < DrawImages.units.size(); i++) {
					DrawImages.units.elementAt(i).setX(DrawImages.units.elementAt(i).getX() + 5);
					DrawImages.units.elementAt(i).moveToX = DrawImages.units.elementAt(i).moveToX + 5;
				}
				for (int i = 0; i < Resources.size(); i++) {
					Resources.get(i).setX((Resources.get(i).getX()) + 5);
				}
				for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
					DrawImages.enemyUnits.elementAt(i).setX(DrawImages.enemyUnits.elementAt(i).getX() + 5);
				}
				Buildings.mouseX += 5;
				count++;
			}
		}
		if (Play.directionX == 2) {
			if (Play.pressedX == true) {
				Play.grassX -= 5;
				for (int i = 0; i < buildings.size(); i++) {
					buildings.elementAt(i).setX((buildings.elementAt(i).getX()) - 5);
				}
				for (int i = 0; i < DrawImages.units.size(); i++) {
					DrawImages.units.elementAt(i).setX((DrawImages.units.elementAt(i).getX()) - 5);
					DrawImages.units.elementAt(i).moveToX = DrawImages.units.elementAt(i).moveToX - 5;
				}
				for (int i = 0; i < Resources.size(); i++) {
					Resources.get(i).setX((Resources.get(i).getX()) - 5);
				}
				for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
					DrawImages.enemyUnits.elementAt(i).setX(DrawImages.enemyUnits.elementAt(i).getX() - 5);
				}
				Buildings.mouseX -= 5;
				count++;
			}
		}
		if (Play.directionY == 1) {
			if (Play.pressedY == true) {
				Play.grassY += 5;
				for (int i = 0; i < buildings.size(); i++) {
					buildings.elementAt(i).setY((buildings.elementAt(i).getY()) + 5);
				}
				for (int i = 0; i < DrawImages.units.size(); i++) {
					DrawImages.units.elementAt(i).setY((DrawImages.units.elementAt(i).getY()) + 5);
					DrawImages.units.elementAt(i).moveToY = DrawImages.units.elementAt(i).moveToY + 5;
				}
				for (int i = 0; i < Resources.size(); i++) {
					Resources.get(i).setY((Resources.get(i).getY()) + 5);
				}
				for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
					DrawImages.enemyUnits.elementAt(i).setY(DrawImages.enemyUnits.elementAt(i).getY() + 5);
				}
				Buildings.mouseY += 5;
				count++;
			}
		}
		if (Play.directionY == 2) {
			if (Play.pressedY == true) {
				Play.grassY -= 5;
				for (int i = 0; i < buildings.size(); i++) {
					buildings.elementAt(i).setY((buildings.elementAt(i).getY()) - 5);
				}
				for (int i = 0; i < DrawImages.units.size(); i++) {
					DrawImages.units.elementAt(i).setY((DrawImages.units.elementAt(i).getY()) - 5);
					DrawImages.units.elementAt(i).moveToY = DrawImages.units.elementAt(i).moveToY - 5;
				}
				for (int i = 0; i < Resources.size(); i++) {
					Resources.get(i).setY((Resources.get(i).getY()) - 5);
				}
				for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
					DrawImages.enemyUnits.elementAt(i).setY(DrawImages.enemyUnits.elementAt(i).getY() - 5);
				}
				Buildings.mouseY -= 5;
				count++;
			}
		}
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
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "powerPlantGreen.png"));
					Play.resourceCount -= powerPlantCost;
				}
				Button.clicked = " ";
			}
		}
		if (Button.clicked.equals("Barracks")) {
			g.drawImage(BarracksGreenT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false) {
				if (Play.resourceCount >= barracksCost) {
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "barracks.png"));
					Play.resourceCount -= barracksCost;
				}
				Button.clicked = " ";
			}
		}
		if (Button.clicked.equals("Factory")) {
			g.drawImage(FactoryGreenT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false) {
				if (Play.resourceCount >= factoryCost) {
					buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "factory.png"));
					Play.resourceCount -= factoryCost;
				}
				Button.clicked = " ";
			}
		}
		if (Button.clicked.equals("Refinery")) {
			g.drawImage(RefineryGreenT, Play.mouseX - 50, Play.mouseY - 50, 100, 100, this);
			if (Play.click == 0 && Buildings.newBuildingCollision(DrawImages.buildings) == false
					&& Play.resourceCount >= refineryCost) {
				buildings.add(new Buildings(Play.mouseX - 50, Play.mouseY - 50, "refinery.png"));
				Play.resourceCount -= refineryCost;
				Button.clicked = " ";
			}
		}

		if (Button.clicked.equals("Harvester")) {
			if (Play.resourceCount >= harvesterCost) {
				System.out.println("harvester building");
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"Harvester");
				Play.resourceCount -= harvesterCost;
			}
			Button.clicked = " ";

		}
		if (Button.clicked.equals("MediumTank")) {
			if (Play.resourceCount >= mediumTankCost) {
				System.out.println("MediumTank building");
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"MediumTank");
				Play.resourceCount -= mediumTankCost;
			}
			Button.clicked = " ";
		}
		if (Button.clicked.equals("Artillery")) {
			if (Play.resourceCount >= artilleryCost) {
				System.out.println("Artillery building");
				DrawImages.buildings.elementAt(DrawImages.collisionIndex).addQueue(Buildings.mouseX, Buildings.mouseY,
						"Artillery");
				Play.resourceCount -= artilleryCost;
			}
			Button.clicked = " ";
		}
		// Adds all of the buttons to the frame
		for (int i = 0; i < Button.buttons.size(); i++) {
			add(Button.buttons.get(i));
		}

		// If the correct building is pressed display the required buttons (if the
		// factory is clicked on show the units the factory can build)
		if (DrawImages.collision == true && DrawImages.collisionType.equals("barracks.png")) {
			Button.buttons.get(4).setLocation(100, 600);
			Button.buttons.get(5).setLocation(100, 650);
		} else {
			Button.buttons.get(4).setLocation(-100, -100);
			Button.buttons.get(5).setLocation(-100, -100);
		}
		if (DrawImages.collision == true && DrawImages.collisionType.equals("refinery.png")) {
			Button.buttons.get(6).setLocation(300, 650);
		} else {
			Button.buttons.get(6).setLocation(-100, -100);
		}
		if (DrawImages.collision == true && DrawImages.collisionType.equals("factory.png")) {
			Button.buttons.get(7).setLocation(200, 650);
			Button.buttons.get(8).setLocation(200, 600);
		} else {
			Button.buttons.get(7).setLocation(-100, -100);
			Button.buttons.get(8).setLocation(-100, -100);
		}
		// Draws the selection box
		if (Play.selection == true) {
			g.setColor(new Color(0, 0, 0));
			if (Play.mouseX - Play.selectionX > 0 && Play.mouseY - Play.selectionY > 0) {
				DrawImages.selectionRecX = Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY;
				DrawImages.selectionRecW = Play.mouseX - Play.selectionX;
				DrawImages.selectionRecH = Play.mouseY - Play.selectionY;
				// g.drawRect(Play.selectionX , Play.selectionY , Play.mouseX-Play.selectionX,
				// Play.mouseY-Play.selectionY);
			} else if (Play.mouseX - Play.selectionX < 0 && Play.mouseY - Play.selectionY < 0) {
				DrawImages.selectionRecX = Play.selectionX + Play.mouseX - Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY + Play.mouseY - Play.selectionY;
				DrawImages.selectionRecW = -1 * (Play.mouseX - Play.selectionX);
				DrawImages.selectionRecH = -1 * (Play.mouseY - Play.selectionY);
				// g.drawRect(Play.selectionX + Play.mouseX-Play.selectionX , Play.selectionY +
				// Play.mouseY-Play.selectionY , -1 * (Play.mouseX-Play.selectionX), -1 *
				// (Play.mouseY-Play.selectionY));
			} else if (Play.mouseX - Play.selectionX > 0 && Play.mouseY - Play.selectionY < 0) {
				DrawImages.selectionRecX = Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY + Play.mouseY - Play.selectionY;
				DrawImages.selectionRecW = Play.mouseX - Play.selectionX;
				DrawImages.selectionRecH = -1 * (Play.mouseY - Play.selectionY);
				// g.drawRect(Play.selectionX , Play.selectionY + Play.mouseY-Play.selectionY ,
				// Play.mouseX-Play.selectionX, -1 * (Play.mouseY-Play.selectionY));
			} else if (Play.mouseX - Play.selectionX < 0 && Play.mouseY - Play.selectionY > 0) {
				DrawImages.selectionRecX = Play.selectionX + Play.mouseX - Play.selectionX;
				DrawImages.selectionRecY = Play.selectionY;
				DrawImages.selectionRecW = -1 * (Play.mouseX - Play.selectionX);
				DrawImages.selectionRecH = Play.mouseY - Play.selectionY;
				// g.drawRect(Play.selectionX + Play.mouseX-Play.selectionX, Play.selectionY, -1
				// * (Play.mouseX-Play.selectionX), Play.mouseY-Play.selectionY);
			}
			g.drawRect(DrawImages.selectionRecX, DrawImages.selectionRecY, DrawImages.selectionRecW,
					DrawImages.selectionRecH);
		}

	}

}

package main;

import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;

/**
 * Class for all of the buildings
 * 
 * @author Justin
 *
 */
public class Buildings {
	// Declares variables
	private int x;
	private int y;
	private int weigth;
	private int height;
	public int queueTime;
	public String name;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public String direction = "";
	public Vector<Units> queue = new Vector<Units>();
	public int range;
	public int attackCoolDown = 15;
	public int lowestDistance = 1000;
	public int indexOfLowest = -1;
	public boolean attack = false;
	public int bulletWidth = 7;
	public int bulletHeight = 13;
	public int bulletSpeed = 13;
	public int attackCoolDownCount = 0;
	public int damage = 20;
	public int HP;
	public Vector<Bullet> bullets = new Vector<Bullet>();

	//
	/**
	 * Creates the new building and sets its height, weight, x, y, and name, Etc
	 * 
	 * @param x
	 * @param y
	 * @param name The type of building being created
	 */
	public Buildings(int x, int y, String name) {
		if (name.equals("MainBase")) {
			this.HP = 4000;
			this.weigth = 150;
			this.height = 110;
		} else if (name.equals("Turret") || name.equals("TurretRed")) {
			this.HP = 1600;
			this.weigth = 100;
			this.height = 100;
			this.direction = "North";
			this.range = 200;
		} else if (name.equals("MainBaseRed")) {
			this.HP = 4000;
			this.weigth = 150;
			this.height = 120;
		} else {
			this.HP = 2000;
			this.weigth = 100;
			this.height = this.weigth;
		}
		this.x = x;
		this.y = y;
		this.name = name;

	}

	// Getters and setters
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWeigth() {
		return this.weigth;
	}

	public int getHeight() {
		return this.height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWeigth(int w) {
		this.weigth = w;
	}

	public void setHeight(int h) {
		this.height = h;
	}

	/**
	 * Returns the image that should be drawn
	 * 
	 * @return
	 */
	public Image getName() {
		ImageIcon temp = new ImageIcon(this.getClass().getResource(this.name));
		Image Image = temp.getImage();
		return Image;
	}

	/**
	 * Detects collision of the mouse and a building
	 * 
	 * @param buildings The vector of all of the buildings
	 */
	public static void collision(Vector<Buildings> buildings) {
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).x < Play.mouseX && Play.mouseX < buildings.get(i).x + buildings.get(i).weigth
					&& Play.mouseY < buildings.get(i).y + buildings.get(i).height && buildings.get(i).y < Play.mouseY) {
				DrawImages.collision = true;
				DrawImages.collisionIndex = i;
				DrawImages.collisionType = buildings.get(i).name;
				Buildings.mouseX = buildings.get(i).x;
				Buildings.mouseY = buildings.get(i).y;
			}
		}
	}

	//
	/**
	 * Detects collision of buildings and the mouse
	 * 
	 * @param buildings The vector of all of the buildings
	 * @return true or false depending on if the mouse is on a building
	 */
	public static Boolean collisionReturn(Vector<Buildings> buildings) {
		boolean onBuilding = false;
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).x < Play.mouseX && Play.mouseX < buildings.get(i).x + buildings.get(i).weigth
					&& Play.mouseY < buildings.get(i).y + buildings.get(i).height && buildings.get(i).y < Play.mouseY) {
				onBuilding = true;
			}
		}
		return onBuilding;
	}

	/**
	 * Detects collision with buildings and what would be a new buildings the the
	 * player doesn't build things on top of each other
	 * 
	 * @param buildings The vector of all of the buildings
	 * @return true or false depending on if the mouse is on a building or resources
	 */
	public static Boolean newBuildingCollision(Vector<Buildings> buildings) {
		boolean onBuilding = false;
		int mouseX = Play.mouseX - 50;
		int mouseY = Play.mouseY - 50;
		for (int i = 0; i < buildings.size(); i++) {
			if (buildings.get(i).x < mouseX + buildings.get(i).weigth
					&& mouseX < buildings.get(i).x + buildings.get(i).weigth
					&& mouseY < buildings.get(i).y + buildings.get(i).height
					&& buildings.get(i).y < mouseY + buildings.get(i).height) {
				onBuilding = true;
			}
		}
		for (int i = 0; i < DrawImages.Resources.size(); i++) {
			if (DrawImages.Resources.get(i).getX() < mouseX + DrawImages.Resources.get(i).getWidth()
					&& mouseX < DrawImages.Resources.get(i).getX() + DrawImages.Resources.get(i).getWidth()
					&& mouseY < DrawImages.Resources.get(i).getY() + DrawImages.Resources.get(i).getHeight()
					&& DrawImages.Resources.get(i).getY() < mouseY + DrawImages.Resources.get(i).getHeight()) {
				onBuilding = true;
			}
		}
		return onBuilding;
	}

	/**
	 * Adds the unit to the queue for the building
	 * 
	 * @param x
	 * @param y
	 * @param name Type of unit being created
	 */
	public void addQueue(int x, int y, String name) {
		this.queue.add(new Units(x, y, name));
	}

	/**
	 * When the unit is finished building from the queue create it
	 * 
	 * @param side The side of the unit
	 */
	public void createUnitFromQueue(int side) {
		if (side == 0) {
			if (this.queueTime == this.queue.elementAt(0).trainingTime) {
				DrawImages.units.add(new Units(this.x, this.y, this.queue.elementAt(0).name));
				this.queue.remove(0);
				this.queueTime = 0;
			}
		}
		if (side == 1) {
			if (this.queueTime == this.queue.elementAt(0).trainingTime) {
				DrawImages.enemyUnits.add(new Units(this.x, this.y, this.queue.elementAt(0).name));

				this.queue.remove(0);
				this.queueTime = 0;
			}
		}
	}

	/**
	 * Attacks the closest enemy unit
	 * 
	 * @param side The side of the unit
	 */
	public void attack(int side) {
		this.lowestDistance = 1000;
		Vector<Integer> distance = new Vector<Integer>();
		Vector<Integer> instance = new Vector<Integer>();
		this.attackCoolDownCount++;
		// If the unit is in the players army
		if (side == 0) {
			// Detects what enemy units are in range
			for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
				if (Units.collisionDetection(this.x - this.range, this.y - this.range,
						(this.range * 2) + this.getWeigth(), (this.range * 2) + this.getHeight(),
						DrawImages.enemyUnits.elementAt(i).getX(), DrawImages.enemyUnits.elementAt(i).getY(),
						DrawImages.enemyUnits.elementAt(i).getWidth(),
						DrawImages.enemyUnits.elementAt(i).getHeight()) == true) {
					instance.add(i);
					distance.add((int) (Math.sqrt((Math.abs(DrawImages.enemyUnits.elementAt(i).getX() - this.x)
							* Math.abs(DrawImages.enemyUnits.elementAt(i).getX() - this.x))
							+ Math.abs(DrawImages.enemyUnits.elementAt(i).getY() - this.y)
									* Math.abs(DrawImages.enemyUnits.elementAt(i).getY() - this.y))));

				}
			}
			// Finds the closest unit
			for (int i = 0; i < distance.size(); i++) {
				if (distance.elementAt(i).intValue() < lowestDistance) {
					lowestDistance = distance.elementAt(i).intValue();
					indexOfLowest = instance.elementAt(i).intValue();
				}
			}
			// Determines which direction the turret should face
			if (indexOfLowest >= 0) {
				int distanceFromNorth = (int) (Math.sqrt((Math.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
						+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) - this.x + (this.weigth / 2))
						* Math.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) - this.x
								+ (this.weigth / 2)))
						+ Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2)) - this.y)

								* Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
										+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2)) - this.y)));
				int distanceFromSouth = (int) (Math.sqrt((Math.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
						+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) - this.x + (this.weigth / 2))
						* Math.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) - this.x
								+ (this.weigth / 2)))
						+ Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getHeight() / 2))
								- (this.y + this.height))
								* Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
										+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getHeight() / 2))
										- (this.y + this.height))));
				int distanceFromEast = (int) (Math.sqrt((Math.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
						+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) + this.x + this.weigth)
						* Math.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) + this.x
								+ this.weigth))
						+ Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getHeight() / 2))
								- (this.y + (this.height / 2)))
								* Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
										+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getHeight() / 2))
										- (this.y + (this.height / 2)))));
				int distanceFromWest = (int) (Math.sqrt((Math
						.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) - this.x)
						* Math.abs(DrawImages.enemyUnits.elementAt(indexOfLowest).getX()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getWidth() / 2) - this.x))
						+ Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
								+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getHeight() / 2))
								- (this.y + (this.height / 2)))
								* Math.abs((DrawImages.enemyUnits.elementAt(indexOfLowest).getY()
										+ (DrawImages.enemyUnits.elementAt(indexOfLowest).getHeight() / 2))
										- (this.y + (this.height / 2)))));

				if (distanceFromNorth <= distanceFromSouth && distanceFromNorth <= distanceFromWest
						&& distanceFromNorth <= distanceFromEast) {
					this.direction = "North";
				} else if (distanceFromSouth <= distanceFromWest && distanceFromSouth <= distanceFromEast) {
					this.direction = "South";
				} else if (distanceFromEast <= distanceFromWest) {
					this.direction = "East";
				} else {
					this.direction = "West";
				}

				// Attacks the closest unit if attack cool down is up by creating a new bullet
				// that attacks the enemy unit
				if (this.attackCoolDownCount % this.attackCoolDown == 0) {
					DrawImages.enemyUnits.elementAt(indexOfLowest).bullets
							.add(new Bullet(this.x, this.y, this.bulletWidth, this.bulletHeight,
									DrawImages.enemyUnits.elementAt(indexOfLowest).x
											+ DrawImages.enemyUnits.elementAt(indexOfLowest).width / 2,
									DrawImages.enemyUnits.elementAt(indexOfLowest).y
											+ DrawImages.enemyUnits.elementAt(indexOfLowest).height / 2,
									this.bulletSpeed, this.damage));
				}
			}
		}
		// If an enemy unit do the same thing as a friendly unit but checks for players
		// units instead of enemy units
		if (side == 1) {

			// Detects what enemy units are in range
			for (int i = 0; i < DrawImages.units.size(); i++) {
				if (Units.collisionDetection(this.x - this.range, this.y - this.range,
						(this.range * 2) + this.getWeigth(), (this.range * 2) + this.getHeight(),
						DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
						DrawImages.units.elementAt(i).getWidth(), DrawImages.units.elementAt(i).getHeight()) == true) {
					instance.add(i);
					distance.add((int) (Math.sqrt((Math.abs(DrawImages.units.elementAt(i).getX() - this.x)
							* Math.abs(DrawImages.units.elementAt(i).getX() - this.x))
							+ Math.abs(DrawImages.units.elementAt(i).getY() - this.y)
									* Math.abs(DrawImages.units.elementAt(i).getY() - this.y))));

				}
			}
			// Finds the closest unit
			for (int i = 0; i < distance.size(); i++) {
				if (distance.elementAt(i).intValue() < lowestDistance) {
					lowestDistance = distance.elementAt(i).intValue();
					indexOfLowest = instance.elementAt(i).intValue();
				}
			}
			// Determines which direction the turret should face
			if (indexOfLowest >= 0) {
				int distanceFromNorth = (int) (Math.sqrt((Math.abs(DrawImages.units.elementAt(indexOfLowest).getX()
						+ (DrawImages.units.elementAt(indexOfLowest).getWidth() / 2) - this.x + (this.weigth / 2))
						* Math.abs(DrawImages.units.elementAt(indexOfLowest).getX()
								+ (DrawImages.units.elementAt(indexOfLowest).getHeight() / 2) - this.x
								+ (this.weigth / 2)))
						+ Math.abs(DrawImages.units.elementAt(indexOfLowest).getY() - this.y)
								* Math.abs(DrawImages.units.elementAt(indexOfLowest).getY() - this.y)));
				int distanceFromSouth = (int) (Math.sqrt((Math.abs(DrawImages.units.elementAt(indexOfLowest).getX()
						+ (DrawImages.units.elementAt(indexOfLowest).getWidth() / 2) - this.x + (this.weigth / 2))
						* Math.abs(DrawImages.units.elementAt(indexOfLowest).getX()
								+ (DrawImages.units.elementAt(indexOfLowest).getHeight() / 2) - this.x
								+ (this.weigth / 2)))
						+ Math.abs(DrawImages.units.elementAt(indexOfLowest).getY()
								+ DrawImages.units.elementAt(indexOfLowest).getHeight() - (this.y + this.height))
								* Math.abs(DrawImages.units.elementAt(indexOfLowest).getY()
										+ DrawImages.units.elementAt(indexOfLowest).getHeight()
										- (this.y + this.height))));
				int distanceFromEast = (int) (Math.sqrt((Math
						.abs(DrawImages.units.elementAt(indexOfLowest).getX()
								+ DrawImages.units.elementAt(indexOfLowest).getWidth() - this.x + this.weigth)
						* Math.abs(DrawImages.units.elementAt(indexOfLowest).getX()
								+ DrawImages.units.elementAt(indexOfLowest).getHeight() - this.x + this.weigth))
						+ Math.abs((DrawImages.units.elementAt(indexOfLowest).getY()
								+ (DrawImages.units.elementAt(indexOfLowest).getHeight() / 2))
								- (this.y + (this.height / 2)))
								* Math.abs((DrawImages.units.elementAt(indexOfLowest).getY()
										+ (DrawImages.units.elementAt(indexOfLowest).getHeight() / 2))
										- (this.y + (this.height / 2)))));
				int distanceFromWest = (int) (Math
						.sqrt((Math.abs(DrawImages.units.elementAt(indexOfLowest).getX() - this.x)
								* Math.abs(DrawImages.units.elementAt(indexOfLowest).getX() - this.x))
								+ Math.abs((DrawImages.units.elementAt(indexOfLowest).getY()
										+ (DrawImages.units.elementAt(indexOfLowest).getHeight() / 2))
										- (this.y + (this.height / 2)))
										* Math.abs((DrawImages.units.elementAt(indexOfLowest).getY()
												+ (DrawImages.units.elementAt(indexOfLowest).getHeight() / 2))
												- (this.y + (this.height / 2)))));

				if (distanceFromNorth <= distanceFromSouth && distanceFromNorth <= distanceFromWest
						&& distanceFromNorth <= distanceFromEast) {
					this.direction = "North";
				} else if (distanceFromSouth <= distanceFromWest && distanceFromSouth <= distanceFromEast) {
					this.direction = "South";
				} else if (distanceFromEast <= distanceFromWest) {
					this.direction = "East";
				} else {
					this.direction = "West";
				}

				// Attacks the closest unit if attack cool down is up by creating a new bullet
				// that attacks the enemy unit
				if (this.attackCoolDownCount % this.attackCoolDown == 0) {
					DrawImages.units.elementAt(indexOfLowest).bullets
							.add(new Bullet(this.x, this.y, this.bulletWidth, this.bulletHeight,
									DrawImages.units.elementAt(indexOfLowest).x
											+ DrawImages.units.elementAt(indexOfLowest).width / 2,
									DrawImages.units.elementAt(indexOfLowest).y
											+ DrawImages.units.elementAt(indexOfLowest).height / 2,
									this.bulletSpeed, this.damage));
				}
			}
		}
		this.indexOfLowest = -1;
	}

	/**
	 * Returns the image that should be drawn
	 * 
	 * @return
	 */
	public Image getImage() {
		Image Image = null;
		String name = this.name + this.direction + ".png";
		ImageIcon temp = new ImageIcon(this.getClass().getResource(name));
		Image = temp.getImage();
		return Image;
	}

	/**
	 * Moves all of the bullets that are targeting the building
	 */
	public void moveBullets() {
		for (int i = 0; i < this.bullets.size(); i++) {
			// Try to move but if already at the enemy deal the enemy damage and remove the
			// bullet
			try {
				this.bullets.elementAt(i).x = this.bullets.elementAt(i).x
						- ((int) (this.bullets.elementAt(i).x - this.bullets.elementAt(i).targetX)
								/ this.bullets.elementAt(i).moveIntervals);
				this.bullets.elementAt(i).y = this.bullets.elementAt(i).y
						- ((int) (this.bullets.elementAt(i).y - this.bullets.elementAt(i).targetY)
								/ this.bullets.elementAt(i).moveIntervals);
				this.bullets.elementAt(i).moveIntervals -= 1;
				this.bullets.elementAt(i).findDirection();
			} catch (ArithmeticException e) {
				this.HP -= this.bullets.elementAt(i).damage;
				this.bullets.remove(i);
			}

		}
	}
}

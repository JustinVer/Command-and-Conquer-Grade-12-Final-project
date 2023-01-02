package main;

import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;

public class Units {
	// Declare variables
	public static final int moveDistanceBuildings = 40;
	public static final int moveDistanceUnits = 20;
	public int x;
	public int y;
	public int width;
	public int height;
	public int range;
	public int damage;
	public int HP;
	public int trainingTime;
	public int attackCoolDown;
	public int attackCoolDownCount = 0;
	public String name;
	public int speed;
	public int moveToX;
	public int moveToY;
	public int tempMoveToX;
	public int tempMoveToY;
	public String directionX;
	public String directionY;
	public boolean moving = false;
	public int stopMovingCount = 0;
	public boolean newDirectionXBuildings = false;
	public boolean newDirectionYBuildings = false;
	public String newDirectionXDBuildings;
	public String newDirectionYDBuildings;
	public int newDirectionXCountBuildings = 400;
	public int newDirectionYCountBuildings = 400;
	public boolean newDirectionXUnits = false;
	public boolean newDirectionYUnits = false;
	public String newDirectionXDUnits;
	public String newDirectionYDUnits;
	public int newDirectionXCountUnits = 400;
	public int newDirectionYCountUnits = 400;
	public int lowestDistance = 1000;
	public int indexOfLowest = -1;
	public boolean attack = false;
	public int bulletWidth;
	public int bulletHeight;
	public int bulletSpeed;
	public Vector<Bullet> bullets = new Vector<Bullet>();
	public boolean collectingResources = false;
	public int miningRate = 0;
	public int resourceBeingMined;
	public int resourcesInHolding = 0;
	public int maxHolding = 2000;
	public boolean movingToRefinery = false;
	public int IndexOfRefinery = 10000;

//Creates a new unit with the proper X, Y, width, and height, etc
	public Units(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
		if (name.equals("MediumTank")) {
			this.width = 75;
			this.height = 75;
			this.speed = 4;
			this.range = 200;
			this.damage = 25;
			this.HP = 500;
			this.trainingTime = 100;
			this.directionX = "East";
			this.directionY = "South";
			this.attackCoolDown = 25;
			this.bulletWidth = 10;
			this.bulletHeight = 20;
			this.bulletSpeed = 10;
		}
		if (name.equals("Harvester")) {
			this.width = 75;
			this.height = 65;
			this.speed = 5;
			this.range = 150;
			this.damage = 5;
			this.HP = 400;
			this.trainingTime = 100;
			this.directionX = "East";
			this.directionY = "South";
			this.attackCoolDown = 40;
			this.bulletWidth = 6;
			this.bulletHeight = 12;
			this.bulletSpeed = 12;
			this.miningRate = 10;
		}
		if (name.equals("Artillery")) {
			this.width = 100;
			this.height = 100;
			this.speed = 3;
			this.range = 400;
			this.damage = 50;
			this.HP = 400;
			this.trainingTime = 110;
			this.directionX = "East";
			this.directionY = "South";
			this.attackCoolDown = 100;
			this.bulletWidth = 20;
			this.bulletHeight = 24;
			this.bulletSpeed = 8;
		}

	}

	// Some Getters and Setters
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public String getName() {
		return this.name;
	}

	// Returns the image that should be drawn
	public Image getImage() {
		Image Image = null;
		String name = this.name + this.directionY + this.directionX + ".png";
		ImageIcon temp = new ImageIcon(this.getClass().getResource(name));
		Image = temp.getImage();
		return Image;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	// Basic collision detection that can be used by anything
	public static boolean collisionDetection(int rec_1_x, int rec_1_y, int rec_1_w, int rec_1_h, int rec_2_x,
			int rec_2_y, int rec_2_w, int rec_2_h) {
		boolean collision = false;
		if (rec_2_x < rec_1_x + rec_1_w && rec_1_x < rec_2_x + rec_2_w && rec_1_y < rec_2_y + rec_2_h
				&& rec_2_y < rec_1_y + rec_1_h) {
			collision = true;
		}
		return collision;
	}

	// Detects collision with units and buildings then decides how to go around the
	// building
	public void collisionUnitsWithBuildings() {
		// Detects collision of unit with buildings
		for (int i = 0; i < DrawImages.buildings.size(); i++) {
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			// If there is collision determine the appropriate way the unit should respond
			if (Units.collisionDetection(this.x, this.y, this.width, this.height,
					DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
					DrawImages.buildings.elementAt(i).getWeigth(), DrawImages.buildings.elementAt(i).getHeight())) {

				if (this.directionX.equals("East") && this.directionY.equals("")) {
					// Reverses the movement that was made
					this.x = this.x - this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x, this.y - this.height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.y + (this.height / 2)) > (DrawImages.buildings.elementAt(i).getY()
								+ (DrawImages.buildings.elementAt(i).getHeight() / 2))) {
							count1 = 1;
						} else {
							count2 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionYCountBuildings = 0;
						this.newDirectionYDBuildings = "North";
						this.newDirectionYBuildings = true;
					} else if (count2 == 0) {
						this.newDirectionYCountBuildings = 0;
						this.newDirectionYDBuildings = "South";
						this.newDirectionYBuildings = true;
					}
				} else if (this.directionX.equals("East") && this.directionY.equals("North")) {
					// Reverses the movement that was made
					this.x = (this.x - (int) (this.speed / 1.25));
					this.y = (this.y + (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y - height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getHeight(),
							DrawImages.buildings.elementAt(i).getWeigth()) == true) {
						count2++;
					}
					for (int k = 0; k < 3; k++) {
						if (Units.collisionDetection(this.x - (this.width * k), this.y, this.width, this.height,
								DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
								DrawImages.buildings.elementAt(i).getWeigth(),
								DrawImages.buildings.elementAt(i).getHeight()) == true) {
							count3++;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.x = this.x + this.speed;
						}
					} else if (count2 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.y = this.y - this.speed;
						}
					} else if (count3 == 0) {
						this.newDirectionXCountBuildings = 0;
						this.newDirectionXDBuildings = "West";
						this.newDirectionXBuildings = true;
					}

				} else if (this.directionX.equals("East") && this.directionY.equals("South")) {
					// Reverses the movement that was made
					this.x = (this.x - (int) (this.speed / 1.25));
					this.y = (this.y - (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count2++;
					}
					for (int k = 0; k < 3; k++) {
						if (Units.collisionDetection(this.x, this.y - (this.height * k), this.width, this.height,
								DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
								DrawImages.buildings.elementAt(i).getWeigth(),
								DrawImages.buildings.elementAt(i).getHeight()) == true) {
							count3++;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.x = this.x + this.speed;
						}
					} else if (count2 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.y = this.y + this.speed;
						}
					} else if (count3 == 0) {
						this.newDirectionYCountBuildings = 0;
						this.newDirectionYBuildings = true;
						this.newDirectionYDBuildings = "North";
					}

				} else if (this.directionX.equals("West") && this.directionY.equals("")) {
					// Reverses the movement that was made
					this.x = this.x + this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x, this.y - height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.y + (this.height / 2)) > (DrawImages.buildings.elementAt(i).getY()
								+ (DrawImages.buildings.elementAt(i).getHeight() / 2))) {
							count1 = 1;
						} else {
							count2 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionYCountBuildings = 0;
						this.newDirectionYDBuildings = "North";
						this.newDirectionYBuildings = true;
					} else if (count2 == 0) {
						this.newDirectionYCountBuildings = 0;
						this.newDirectionYDBuildings = "South";
						this.newDirectionYBuildings = true;
					}

				} else if (this.directionX.equals("West") && this.directionY.equals("North")) {
					// Reverses the movement that was made
					this.x = (this.x + (int) (this.speed / 1.25));
					this.y = (this.y + (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y - height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count2++;
					}
					for (int k = 0; k < 3; k++) {
						if (Units.collisionDetection(this.x + (this.width * k), this.y, this.width, this.height,
								DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
								DrawImages.buildings.elementAt(i).getWeigth(),
								DrawImages.buildings.elementAt(i).getHeight()) == true) {
							count3++;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.x = this.x - this.speed;
						}
					} else if (count2 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.y = this.y - this.speed;
						}
					} else if (count3 == 0) {
						this.newDirectionXCountBuildings = 0;
						this.newDirectionXDBuildings = "East";
						this.newDirectionXBuildings = true;
					}

				} else if (this.directionX.equals("West") && this.directionY.equals("South")) {
					// Reverses the movement that was made
					this.x = (this.x + (int) (this.speed / 1.25));
					this.y = (this.y - (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + this.height, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count2++;
					}
					for (int k = 0; k < 3; k++) {
						if (Units.collisionDetection(this.x + (this.width * k), this.y, this.width, this.height,
								DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
								DrawImages.buildings.elementAt(i).getWeigth(),
								DrawImages.buildings.elementAt(i).getHeight()) == true) {
							count3++;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.x = this.x - this.speed;
						}
					} else if (count2 == 0) {
						if (this.newDirectionXCountBuildings >= Units.moveDistanceBuildings
								&& this.newDirectionYCountBuildings >= Units.moveDistanceBuildings) {
							this.y = this.y + this.speed;
						}
					} else if (count3 == 0) {
						this.newDirectionXCountBuildings = 0;
						this.newDirectionXDBuildings = "East";
						this.newDirectionXBuildings = true;
					}

				} else if (this.directionY.equals("North") && this.directionX.equals("")) {
					// Reverses the movement that was made
					this.y = this.y + this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.x + (this.width / 2)) >= (DrawImages.buildings.elementAt(i).getX()
								+ (DrawImages.buildings.elementAt(i).getWeigth() / 2))) {
							count2 = 1;
						} else {
							count1 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionXCountBuildings = 0;
						this.newDirectionXDBuildings = "East";
						this.newDirectionXBuildings = true;
					} else if (count2 == 0) {
						this.newDirectionXCountBuildings = 0;
						this.newDirectionXDBuildings = "West";
						this.newDirectionXBuildings = true;
					}

				} else if (this.directionY.equals("South") && this.directionX.equals("")) {
					// Reverses the movement that was made
					this.y = this.y - this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.buildings.elementAt(i).getX(), DrawImages.buildings.elementAt(i).getY(),
							DrawImages.buildings.elementAt(i).getWeigth(),
							DrawImages.buildings.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.x + (this.width / 2)) > (DrawImages.buildings.elementAt(i).getX()
								+ (DrawImages.buildings.elementAt(i).getWeigth() / 2))) {
							count2 = 1;
						} else {
							count1 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionXCountBuildings = 0;
						this.newDirectionXDBuildings = "East";
						this.newDirectionXBuildings = true;
					} else if (count2 == 0) {
						this.newDirectionXCountBuildings = 0;
						this.newDirectionXBuildings = true;
						this.newDirectionXDBuildings = "West";
					}
				}
			}
		}

	}

	// Detects collision with units and units then decides how to go around the
	// units
	public void collisionUnitsWithUnits(int unitNum) {
		// Detects collision of unit with other units
		for (int i = 0; i < DrawImages.units.size(); i++) {
			int count1 = 0;
			int count2 = 0;
			int count3 = 0;
			// If there is collision determine the appropriate way the unit should respond
			if (Units.collisionDetection(this.x, this.y, this.width, this.height, DrawImages.units.elementAt(i).getX(),
					DrawImages.units.elementAt(i).getY(), DrawImages.units.elementAt(i).getWidth(),
					DrawImages.units.elementAt(i).getHeight()) == true && i != unitNum) {
				if (this.directionX.equals("East") && this.directionY.equals("")) {
					// Reverses the movement that was made
					this.x = this.x - this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x, this.y - this.height, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + height, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.y + (this.height / 2)) > (DrawImages.units.elementAt(i).getY()
								+ (DrawImages.units.elementAt(i).getHeight() / 2))) {
							count1 = 1;
						} else {
							count2 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionYCountUnits = 0;
						this.newDirectionYDUnits = "North";
						this.newDirectionYUnits = true;
					} else if (count2 == 0) {
						this.newDirectionYCountUnits = 0;
						this.newDirectionYDUnits = "South";
						this.newDirectionYUnits = true;
					}
				} else if (this.directionX.equals("East") && this.directionY.equals("North")) {
					// Reverses the movement that was made
					this.x = (this.x - (int) (this.speed / 1.25));
					this.y = (this.y + (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					for (int j = 0; j < DrawImages.units.size(); j++) {
						if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
								DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
								DrawImages.units.elementAt(i).getWidth(),
								DrawImages.units.elementAt(i).getHeight()) == true) {
							count1++;
						}
						if (Units.collisionDetection(this.x, this.y - height, this.width, this.height,
								DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
								DrawImages.units.elementAt(i).getHeight(),
								DrawImages.units.elementAt(i).getWidth()) == true) {
							count2++;
						}
						for (int k = 0; k < 3; k++) {
							if (Units.collisionDetection(this.x - (this.width * k), this.y, this.width, this.height,
									DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
									DrawImages.units.elementAt(i).getWidth(),
									DrawImages.units.elementAt(i).getHeight()) == true) {
								count3++;
							}
						}

						// Sets the correct course for the unit to go
						if (count1 == 0) {
							if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
									&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
								this.x = this.x + this.speed;
							}
						} else if (count2 == 0) {
							if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
									&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
								this.y = this.y - this.speed;
							}
						} else if (count3 == 0) {
							this.newDirectionXCountUnits = 0;
							this.newDirectionXDUnits = "West";
							this.newDirectionXUnits = true;
						}

					}

				} else if (this.directionX.equals("East") && this.directionY.equals("South")) {
					// Reverses the movement that was made
					this.x = (this.x - (int) (this.speed / 1.25));
					this.y = (this.y - (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + height, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count2++;
					}
					for (int k = 0; k < 3; k++) {
						if (Units.collisionDetection(this.x, this.y - (this.height * k), this.width, this.height,
								DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
								DrawImages.units.elementAt(i).getWidth(),
								DrawImages.units.elementAt(i).getHeight()) == true) {
							count3++;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
								&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
							this.x = this.x + this.speed;
						}
					} else if (count2 == 0) {
						if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
								&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
							this.y = this.y + this.speed;
						}
					} else if (count3 == 0) {
						this.newDirectionYCountUnits = 0;
						this.newDirectionYUnits = true;
						this.newDirectionYDUnits = "North";
					}

				} else if (this.directionX.equals("West") && this.directionY.equals("")) {
					// Reverses the movement that was made
					this.x = this.x + this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x, this.y - height, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + height, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.y + (this.height / 2)) > (DrawImages.units.elementAt(i).getY()
								+ (DrawImages.units.elementAt(i).getHeight() / 2))) {
							count1 = 1;
						} else {
							count2 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionYCountUnits = 0;
						this.newDirectionYDUnits = "North";
						this.newDirectionYUnits = true;
					} else if (count2 == 0) {
						this.newDirectionYCountUnits = 0;
						this.newDirectionYDUnits = "South";
						this.newDirectionYUnits = true;
					}

				} else if (this.directionX.equals("West") && this.directionY.equals("North")) {
					// Reverses the movement that was made
					this.x = (this.x + (int) (this.speed / 1.25));
					this.y = (this.y + (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y - height, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count2++;
					}
					for (int k = 0; k < 3; k++) {
						if (Units.collisionDetection(this.x + (this.width * k), this.y, this.width, this.height,
								DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
								DrawImages.units.elementAt(i).getWidth(),
								DrawImages.units.elementAt(i).getHeight()) == true) {
							count3++;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
								&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
							this.x = this.x - this.speed;
						}
					} else if (count2 == 0) {
						if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
								&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
							this.y = this.y - this.speed;
						}
					} else if (count3 == 0) {
						this.newDirectionXCountUnits = 0;
						this.newDirectionXDUnits = "East";
						this.newDirectionXUnits = true;
					}

				} else if (this.directionX.equals("West") && this.directionY.equals("South")) {
					// Reverses the movement that was made
					this.x = (this.x + (int) (this.speed / 1.25));
					this.y = (this.y - (int) (this.speed / 1.25));

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x, this.y + this.height, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count2++;
					}
					for (int k = 0; k < 3; k++) {
						if (Units.collisionDetection(this.x + (this.width * k), this.y, this.width, this.height,
								DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
								DrawImages.units.elementAt(i).getWidth(),
								DrawImages.units.elementAt(i).getHeight()) == true) {
							count3++;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
								&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
							this.x = this.x - this.speed;
						}
					} else if (count2 == 0) {
						if (this.newDirectionXCountUnits >= Units.moveDistanceUnits
								&& this.newDirectionYCountUnits >= Units.moveDistanceUnits) {
							this.y = this.y + this.speed;
						}
					} else if (count3 == 0) {
						this.newDirectionXCountUnits = 0;
						this.newDirectionXDUnits = "East";
						this.newDirectionXUnits = true;
					}

				} else if (this.directionY.equals("North") && this.directionX.equals("")) {
					// Reverses the movement that was made
					this.y = this.y + this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.x + (this.width / 2)) >= (DrawImages.units.elementAt(i).getX()
								+ (DrawImages.units.elementAt(i).getWidth() / 2))) {
							count2 = 1;
						} else {
							count1 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionXCountUnits = 0;
						this.newDirectionXDUnits = "East";
						this.newDirectionXUnits = true;
					} else if (count2 == 0) {
						this.newDirectionXCountUnits = 0;
						this.newDirectionXDUnits = "West";
						this.newDirectionXUnits = true;
					}

				} else if (this.directionY.equals("South") && this.directionX.equals("")) {
					// Reverses the movement that was made
					this.y = this.y - this.speed;

					// Checks to see if there is clear space on either side of the unit
					if (Units.collisionDetection(this.x + this.width, this.y, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count1++;
					}
					if (Units.collisionDetection(this.x - this.width, this.y, this.width, this.height,
							DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
							DrawImages.units.elementAt(i).getWidth(),
							DrawImages.units.elementAt(i).getHeight()) == true) {
						count2++;
					}

					// If both sides are clear pick the one that is closest to the destination of
					// the unit
					if (count1 == 0 && count2 == 0) {
						if ((this.x + (this.width / 2)) > (DrawImages.units.elementAt(i).getX()
								+ (DrawImages.units.elementAt(i).getWidth() / 2))) {
							count2 = 1;
						} else {
							count1 = 1;
						}
					}

					// Sets the correct course for the unit to go
					if (count1 == 0) {
						this.newDirectionXCountUnits = 0;
						this.newDirectionXDUnits = "East";
						this.newDirectionXUnits = true;
					} else if (count2 == 0) {
						this.newDirectionXCountUnits = 0;
						this.newDirectionXUnits = true;
						this.newDirectionXDUnits = "West";
					}
				}
				this.stopMovingCount = 0;
			}
			// If there is collision with units have the unit stop moving for a short period
			if (this.stopMovingCount > 20) {
				this.moving = true;
			} else {
				this.moving = false;
			}
			this.stopMovingCount++;
		}
	}

	// Attacks the closest enemy unit
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
						(this.range * 2) + this.getWidth(), (this.range * 2) + this.getHeight(),
						DrawImages.enemyUnits.elementAt(i).getX(), DrawImages.enemyUnits.elementAt(i).getY(),
						DrawImages.enemyUnits.elementAt(i).getWidth(),
						DrawImages.enemyUnits.elementAt(i).getHeight()) == true) {
					instance.add(i);
					distance.add((int) (Math.sqrt((Math.abs(DrawImages.enemyUnits.elementAt(i).getX() - this.x)
							* Math.abs(DrawImages.enemyUnits.elementAt(i).getX() - this.x))
							+ Math.abs(this.y - DrawImages.enemyUnits.elementAt(i).getY()
									* Math.abs(this.y - DrawImages.enemyUnits.elementAt(i).getY())))));

				}
			}
			// If there is only one unit in range create a new bullet that attacks it if the
			// units attack cool down is up
			if (distance.size() == 1) {
				if (this.attackCoolDownCount % this.attackCoolDown == 0) {
					DrawImages.enemyUnits.elementAt(instance.elementAt(0).intValue()).bullets.add(new Bullet(this.x,
							this.y, this.bulletWidth, this.bulletHeight,
							DrawImages.enemyUnits.elementAt(instance.elementAt(0).intValue()).x
									+ DrawImages.enemyUnits.elementAt(instance.elementAt(0).intValue()).width / 2,
							DrawImages.enemyUnits.elementAt(instance.elementAt(0).intValue()).y
									+ DrawImages.enemyUnits.elementAt(instance.elementAt(0).intValue()).height / 2,
							this.bulletSpeed, this.damage));
				}
				// If there are more than 1 enemy unit in range attack the closest enemy unit
			} else if (distance.size() > 1) {
				// Finds the closest unit
				for (int i = 0; i < distance.size(); i++) {
					if (distance.elementAt(i).intValue() < lowestDistance) {
						lowestDistance = distance.elementAt(i).intValue();
						indexOfLowest = instance.elementAt(i).intValue();
					}
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
			for (int i = 0; i < DrawImages.units.size(); i++) {
				if (Units.collisionDetection(this.x - this.range, this.y - this.range,
						(this.range * 2) + this.getWidth(), (this.range * 2) + this.getHeight(),
						DrawImages.units.elementAt(i).getX(), DrawImages.units.elementAt(i).getY(),
						DrawImages.units.elementAt(i).getWidth(), DrawImages.units.elementAt(i).getHeight()) == true) {
					instance.add(i);
					distance.add((int) (Math.sqrt((Math.abs(DrawImages.units.elementAt(i).getX() - this.x)
							* Math.abs(DrawImages.units.elementAt(i).getX() - this.x))
							+ Math.abs(this.y - DrawImages.units.elementAt(i).getY()
									* Math.abs(this.y - DrawImages.units.elementAt(i).getY())))));

				}
			}
			if (distance.size() == 1) {
				if (this.attackCoolDownCount % this.attackCoolDown == 0) {
					DrawImages.units.elementAt(instance.elementAt(0).intValue()).bullets
							.add(new Bullet(this.x, this.y, this.bulletWidth, this.bulletHeight,
									DrawImages.units.elementAt(instance.elementAt(0).intValue()).x
											+ DrawImages.units.elementAt(instance.elementAt(0).intValue()).width / 2,
									DrawImages.units.elementAt(instance.elementAt(0).intValue()).y
											+ DrawImages.units.elementAt(instance.elementAt(0).intValue()).height / 2,
									this.bulletSpeed, this.damage));
				}
			} else if (distance.size() > 1) {
				for (int i = 0; i < distance.size(); i++) {
					if (distance.elementAt(i).intValue() < lowestDistance) {
						lowestDistance = distance.elementAt(i).intValue();
						indexOfLowest = instance.elementAt(i).intValue();
					}
				}
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
	}

	// Moves all of the bullets
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

	// Is only applicable to harvesters. Collects resources.
	public void collectResources() {

		// If the harvester is at the resource subtract from the resource deposit and
		// add to the harvesters resources
		if (this.collectingResources == true) {
			DrawImages.Resources.elementAt(resourceBeingMined).resourceCount -= this.miningRate;
			this.resourcesInHolding += this.miningRate;
		}
		// If the resource deposit has been depleted delete the deposit and find the
		// closest refinery and deposit the resources
		if (DrawImages.Resources.elementAt(resourceBeingMined).resourceCount <= 0) {
			int lowestDistanceToRefinery = 100000;

			DrawImages.Resources.remove(resourceBeingMined);

			Vector<Integer> distance = new Vector<Integer>();
			// Finds the closest refinery
			for (int i = 0; i < DrawImages.buildings.size(); i++) {

				distance.add((int) (Math.sqrt((Math.abs(DrawImages.buildings.elementAt(i).getX() - this.x)
						* Math.abs(DrawImages.buildings.elementAt(i).getX() - this.x))
						+ Math.abs(this.y - DrawImages.buildings.elementAt(i).getY()
								* Math.abs(this.y - DrawImages.buildings.elementAt(i).getY())))));
				if (distance.elementAt(i).intValue() < lowestDistanceToRefinery
						&& DrawImages.buildings.elementAt(i).name.equals("refinery.png")) {
					lowestDistanceToRefinery = distance.elementAt(i).intValue();
					this.IndexOfRefinery = i;
				}
			}
			// Tells all of the harvesters that were harvesting from that deposit to go
			// deposit there resources at the refinery that was determined the closest
			for (int i = 0; i < DrawImages.units.size(); i++) {
				if (DrawImages.units.elementAt(i).name.equals("Harvester")
						&& DrawImages.units.elementAt(i).collectingResources == true
						&& DrawImages.units.elementAt(i).resourceBeingMined > this.resourceBeingMined) {
					DrawImages.units.elementAt(i).resourceBeingMined -= 1;
				} else if (DrawImages.units.elementAt(i).name.equals("Harvester")
						&& DrawImages.units.elementAt(i).collectingResources == true
						&& DrawImages.units.elementAt(i).resourceBeingMined == this.resourceBeingMined) {
					DrawImages.units.elementAt(i).moveToX = DrawImages.buildings.elementAt(this.IndexOfRefinery).getX();
					DrawImages.units.elementAt(i).moveToY = DrawImages.buildings.elementAt(this.IndexOfRefinery).getY();
					DrawImages.units.elementAt(i).moving = true;
					DrawImages.units.elementAt(i).movingToRefinery = true;
					DrawImages.units.elementAt(i).IndexOfRefinery = this.IndexOfRefinery;
				}
			}
		}
		this.collectingResources = false;

		// Detects if a harvester is within range of a resource deposit and then sets
		// mining to true
		for (int i = 0; i < DrawImages.Resources.size(); i++) {
			if (Units.collisionDetection(this.x - 5, this.y - 5, this.width + 10, this.height + 10,
					DrawImages.Resources.elementAt(i).getX(), DrawImages.Resources.elementAt(i).getY(),
					DrawImages.Resources.elementAt(i).getWidth(),
					DrawImages.Resources.elementAt(i).getHeight()) == true) {
				this.collectingResources = true;
				this.resourceBeingMined = i;
				this.moving = false;
			}
		}
		// If the harvester is within range of a refinery deposit resources at the
		// refinery
		if (this.movingToRefinery == true) {
			if (this.resourcesInHolding > 0
					&& Units.collisionDetection(this.x - this.speed, this.y - this.speed, this.width + (this.speed * 2),
							this.height + (this.speed * 2), DrawImages.buildings.elementAt(IndexOfRefinery).getX(),
							DrawImages.buildings.elementAt(IndexOfRefinery).getY(),
							DrawImages.buildings.elementAt(IndexOfRefinery).getWeigth(),
							DrawImages.buildings.elementAt(IndexOfRefinery).getHeight()) == true) {
				this.moving = false;
				this.resourcesInHolding -= this.miningRate;
				Play.resourceCount += this.miningRate;
				// Once done unloading at the refiner find the closest resource deposit to mine
			} else if (this.resourcesInHolding <= 0) {
				System.out.println("Finding more resources");
				Vector<Integer> distance = new Vector<Integer>();
				int lowestDistanceToResources = 10000;
				int indexLDTR = 10000;
				// Finds the closest resource deposit
				for (int i = 0; i < DrawImages.Resources.size(); i++) {
					distance.add((int) (Math.sqrt((Math.abs(DrawImages.Resources.elementAt(i).getX() - this.x)
							* Math.abs(DrawImages.Resources.elementAt(i).getX() - this.x))
							+ Math.abs(this.y - DrawImages.Resources.elementAt(i).getY()
									* Math.abs(this.y - DrawImages.Resources.elementAt(i).getY())))));
					if (distance.elementAt(i).intValue() < lowestDistanceToResources) {
						lowestDistanceToResources = distance.elementAt(i).intValue();
						indexLDTR = i;
					}
				}
				// Moves to the closest resource deposit
				this.moveToX = DrawImages.Resources.elementAt(indexLDTR).getX();
				this.moveToY = DrawImages.Resources.elementAt(indexLDTR).getY();
				this.moving = true;
			}
		}
	}
}

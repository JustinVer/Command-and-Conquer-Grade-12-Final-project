package main;

public class Bullet {
	// Declare variables
	public int x;
	public int y;
	public int width;
	public int height;
	public int targetX;
	public int targetY;
	public int moveIntervals;
	public int speed;
	public int damage;
	public String directionX;
	public String directionY;

	// Sets the x, y, target, speed, etc
	public Bullet(int x, int y, int width, int height, int targetX, int targetY, int speed, int damage) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.targetX = targetX;
		this.targetY = targetY;
		this.damage = damage;
		// Determines how many frames it should take for the unit to move
		this.moveIntervals = (int) (Math.sqrt(((Math.abs(this.targetX - this.x) * Math.abs(this.targetX - this.x))
				+ Math.abs(this.y - this.targetY * Math.abs(this.y - this.targetY)))) / speed);
	}

	// Determines what direction image to draw
	public void findDirection() {
		if (this.x < this.targetX) {
			this.directionX = "East";
		} else if (this.x > this.targetX) {
			this.directionX = "West";
		} else {
			this.directionX = "";
		}
		if (this.y < this.targetY) {
			this.directionY = "South";
		} else if (this.y > this.targetY) {
			this.directionY = "North";
		} else {
			this.directionY = "";
		}
	}
}

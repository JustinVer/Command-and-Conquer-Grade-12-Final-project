package main;

public class Resources {
	// Declare variables
	private int x;
	private int y;
	private int width;
	private int height;
	public int resourceCount;

	// Sets the x, y, width, height, and resource count
	public Resources(int x, int y) {
		this.x = x;
		this.y = y;
		this.setWidth(90);
		this.setHeight(50);
		this.resourceCount = 2000;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;

	}

	public void setY(int y) {
		this.y = y;

	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
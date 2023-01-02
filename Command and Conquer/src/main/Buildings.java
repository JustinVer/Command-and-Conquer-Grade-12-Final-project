package main;

import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;

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
	public Vector<Units> queue = new Vector<Units>();

	// Creates the new building and sets its height, weight, x, y, and name
	public Buildings(int x, int y, String name) {
		if (name.equals("MainBase.png")) {
			this.weigth = 150;
			this.height = 110;
		} else {
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

	// Returns the image that should be drawn
	public Image getName() {
		ImageIcon temp = new ImageIcon(this.getClass().getResource(this.name));
		Image Image = temp.getImage();
		return Image;
	}

//Detects collision of the mouse and a building
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

	// Detects collision of buildings and the mouse
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

	// Detects collision with buildings and what would be a new buildings the the
	// player doesn't build things on top of each other
	public static Boolean newBuildingCollision(Vector<Buildings> buildings) {
		boolean onBuilding = false;
		for (int i = 0; i < buildings.size(); i++) {
			int mouseX = Play.mouseX - (buildings.get(0).weigth / 2);
			int mouseY = Play.mouseY - (buildings.get(0).height / 2);
			if (buildings.get(i).x < mouseX + buildings.get(i).weigth
					&& mouseX < buildings.get(i).x + buildings.get(i).weigth
					&& mouseY < buildings.get(i).y + buildings.get(i).height
					&& buildings.get(i).y < mouseY + buildings.get(i).height) {
				onBuilding = true;
			}
		}
		return onBuilding;
	}

	// Adds the unit to the queue for the building
	public void addQueue(int x, int y, String name) {
		this.queue.add(new Units(x, y, name));
	}

	// When the unit is finished building from the queue create it
	public void createUnitFromQueue() {
		if (this.queueTime == this.queue.elementAt(0).trainingTime) {
			DrawImages.units
					.add(new Units(this.queue.elementAt(0).x, this.queue.elementAt(0).y, this.queue.elementAt(0).name));
			this.queue.remove(0);
			this.queueTime = 0;
		}
	}

}

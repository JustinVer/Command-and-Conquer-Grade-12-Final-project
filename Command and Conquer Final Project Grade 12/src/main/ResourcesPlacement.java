package main;

public class ResourcesPlacement {
	/**
	 * Description: Colision detection of the resources and the building
	 * 
	 * @author Chris
	 *
	 */
public void Placement() {
	//Collision dection to make sure the building and ore do not overlap wuth each other.
	for (int i = 0; i < 4; i++) {
		int x = i * 200;
		int y = i * 300;
		if (Units.collisionDetection(100, 500, 150, 110, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY, 150, 110, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY, 150, 120, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY + 300, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600 - 200,  Play.enemyMainY + 300 + 50, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600-300,  Play.enemyMainY, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600+330,  Play.enemyMainY, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY-300, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600-300,  Play.enemyMainY+600, 100, 100, x, y, 90, 50) == false) {
			DrawImages.Resources.add(new Resources(x, y));

		}
	}
	for (int i = 4; i < 500; i++) {
		int x = Play.grassX + (int) (Math.random() * 8000 + Play.grassX);
		int y = Play.grassY + (int) (Math.random() * 8000);
		if (Units.collisionDetection(100, 500, 150, 110, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY, 150, 110, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY, 150, 120, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY + 300, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600 - 200,  Play.enemyMainY + 300 + 50, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600-300,  Play.enemyMainY, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600+330,  Play.enemyMainY, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600,  Play.enemyMainY-300, 100, 100, x, y, 90, 50) == false
				&& Units.collisionDetection(5600-300,  Play.enemyMainY+600, 100, 100, x, y, 90, 50) == false) {
			DrawImages.Resources.add(new Resources(x, y));

		}
	}
}
}

package main;

import java.util.Vector;

public class Computerplayer {
	public static int waitX = 5500;
	public static int waitY = Play.enemyMainY + 400;

	/**
	 * Description: Ai building logic and action
	 * 
	 * @author Chris
	 *
	 */
	public void aiBuild() {

		// Build the refinery
		if (Play.resourceCountRed >= DrawImages.refineryCost && Play.refineryBuilt == false) {
			DrawImages.enemyBuildings.add(new Buildings(DrawImages.enemyBuildings.elementAt(0).getX(),
					DrawImages.enemyBuildings.elementAt(0).getY() + 300, "RefineryRed"));
			Play.resourceCountRed -= DrawImages.refineryCost;
			Play.refineryBuilt = true;
		}
//Build the harvester and factory
		for (int i = 0; i < DrawImages.enemyBuildings.size(); i++) {
			if (DrawImages.enemyBuildings.elementAt(i).name.equals("RefineryRed")) {
				if (Play.resourceCountRed >= DrawImages.harvesterCost && Play.refineryBuilt == true
						&& Play.harvesterCountRed <= 10) {
					DrawImages.enemyBuildings.elementAt(i).addQueue(DrawImages.enemyBuildings.get(i).getX(),
							DrawImages.enemyBuildings.get(i).getY(), "HarvesterRed");
					Play.resourceCountRed -= DrawImages.harvesterCost;
					Play.harvesterBuilt = true;

					Play.harvesterCountRed += 1;

				} else if (Play.resourceCountRed >= DrawImages.factoryCost && Play.factoryBuilt == false
						&& Play.harvesterCountRed >= 9) {
					DrawImages.enemyBuildings
							.add(new Buildings(
									DrawImages.enemyBuildings.get(1).getX()
											- DrawImages.enemyBuildings.get(1).getWeigth() - 200,
									DrawImages.enemyBuildings.elementAt(0).getY() + 350, "FactoryRed"));
					Play.resourceCountRed -= DrawImages.factoryCost;
					Play.factoryBuilt = true;

				}
			}
		}
//Build the Turrent
		if (Play.Turret < 4 && Play.resourceCountRed >= DrawImages.turretCost && Play.harvesterCountRed >= 5) {
			if (Play.Turret == 0) {
				DrawImages.enemyBuildings.add(new Buildings(DrawImages.enemyBuildings.elementAt(1).getX(),
						DrawImages.enemyBuildings.elementAt(0).getY() - 300, "TurretRed"));
				Play.Turret++;
				Play.resourceCountRed -= DrawImages.turretCost;
			}
			if (Play.Turret == 1) {
				DrawImages.enemyBuildings.add(new Buildings(DrawImages.enemyBuildings.elementAt(1).getX() - 300,
						DrawImages.enemyBuildings.elementAt(0).getY(), "TurretRed"));
				Play.Turret++;
				Play.resourceCountRed -= DrawImages.turretCost;
			}
			if (Play.Turret == 2) {
				DrawImages.enemyBuildings.add(new Buildings(DrawImages.enemyBuildings.elementAt(1).getX() + 330,
						DrawImages.enemyBuildings.elementAt(0).getY(), "TurretRed"));
				Play.Turret++;
				Play.resourceCountRed -= DrawImages.turretCost;
			}
			if (Play.Turret == 3) {
				DrawImages.enemyBuildings.add(new Buildings(DrawImages.enemyBuildings.elementAt(1).getX() - 300,
						DrawImages.enemyBuildings.elementAt(0).getY() + 600, "TurretRed"));
				Play.Turret++;
				Play.resourceCountRed -= DrawImages.turretCost;
			}

		}
//Build the army
		for (int i = 0; i < DrawImages.enemyBuildings.size(); i++) {
			if (DrawImages.enemyBuildings.elementAt(i).name.equals("FactoryRed")) {
				if (Play.factoryBuilt == true && Play.resourceCountRed >= DrawImages.MammothTankCost
						&& Play.harvesterCountRed >= 10) {
					String attackUnit[] = { "MediumTankRed", "ArtilleryRed", "MammothTankRed", "HumveeRed" };
					int random;
					random = (int) (Math.random() * 4 + 1);
					if (random == 0 && Play.resourceCountRed >= DrawImages.mediumTankCost) {
						DrawImages.enemyBuildings.elementAt(i).addQueue(DrawImages.enemyBuildings.get(i).getX(),
								DrawImages.enemyBuildings.get(i).getY(), attackUnit[random]);

						Play.resourceCountRed -= DrawImages.mediumTankCost;
					} else if (random == 1 && Play.resourceCountRed >= DrawImages.artilleryCost) {
						DrawImages.enemyBuildings.elementAt(i).addQueue(DrawImages.enemyBuildings.get(i).getX(),
								DrawImages.enemyBuildings.get(i).getY(), attackUnit[random]);

						Play.resourceCountRed -= DrawImages.artilleryCost;
					} else if (random == 2 && Play.resourceCountRed >= DrawImages.MammothTankCost) {
						DrawImages.enemyBuildings.elementAt(i).addQueue(DrawImages.enemyBuildings.get(i).getX(),
								DrawImages.enemyBuildings.get(i).getY(), attackUnit[random]);

						Play.resourceCountRed -= DrawImages.MammothTankCost;
					} else if (random == 3 && Play.resourceCountRed >= DrawImages.HumveeCost) {
						DrawImages.enemyBuildings.elementAt(i).addQueue(DrawImages.enemyBuildings.get(i).getX(),
								DrawImages.enemyBuildings.get(i).getY(), attackUnit[random]);

						Play.militaryCountRed += 1;

						Play.resourceCountRed -= DrawImages.HumveeCost;
					}

				}
			}
		}
		Selection.moveEnemy();
	}

	/**
	 * Description: Ai attacking logic and tracing
	 * 
	 * @author Chris
	 *
	 */
	public void aiAttack() {
		// find the closest unit and trace them
		if (DrawImages.enemyUnits.size() > 0 && DrawImages.units.size() > 0) {
			for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
				Vector<Integer> distance = new Vector<Integer>();
				int lowestDistanceToEnemy = 10000;
				int indexLDTE = 0;
				// Finds the closest unit
				for (int j = 0; j < DrawImages.units.size(); j++) {
					distance.add((int) (Math.sqrt((Math
							.abs(DrawImages.units.elementAt(j).getX() - DrawImages.enemyUnits.elementAt(i).getX())
							* Math.abs(
									DrawImages.units.elementAt(j).getX() - DrawImages.enemyUnits.elementAt(i).getX()))
							+ Math.abs(DrawImages.units.elementAt(j).getY() - DrawImages.enemyUnits.elementAt(i).getY())
									* Math.abs(DrawImages.units.elementAt(j).getY()
											- DrawImages.enemyUnits.elementAt(i).getY()))));
				}
				for (int j = 0; j < distance.size(); j++) {
					if (distance.elementAt(j).intValue() < lowestDistanceToEnemy) {
						lowestDistanceToEnemy = distance.elementAt(j).intValue();
						indexLDTE = j;
					}
				}
				// Moves to the closest enemy unit
				if (DrawImages.enemyUnits.elementAt(i).name.equals("HarvesterRed") == false
						&& DrawImages.enemyUnits.elementAt(i).attackEnemy == false) {
					if (DrawImages.enemyUnits.elementAt(i).getX() - DrawImages.units.elementAt(indexLDTE).getX() < 300
							|| DrawImages.units.elementAt(indexLDTE).getX()
									- DrawImages.enemyUnits.elementAt(i).getX() < 300
							|| DrawImages.enemyUnits.elementAt(i).getY()
									- DrawImages.units.elementAt(indexLDTE).getY() < 300
							|| DrawImages.units.elementAt(indexLDTE).getY()
									- DrawImages.enemyUnits.elementAt(i).getY() < 300) {
						DrawImages.enemyUnits.elementAt(i).moveToX = DrawImages.units.elementAt(indexLDTE).getX();
						DrawImages.enemyUnits.elementAt(i).moveToY = DrawImages.units.elementAt(indexLDTE).getY();
						DrawImages.enemyUnits.elementAt(i).moving = true;
						DrawImages.enemyUnits.elementAt(i).moveToTarget = true;
					}
				}
			}
		}
		// Attaking the opponent's main base
		if (Play.militaryCountRed % 5 == 0) {
			for (int i = 0; i < DrawImages.enemyUnits.size(); i++) {
				if (DrawImages.enemyUnits.elementAt(i).name.equals("MediumTankRed")
						|| DrawImages.enemyUnits.elementAt(i).name.equals("ArtilleryRed")
						|| DrawImages.enemyUnits.elementAt(i).name.equals("MammothTankRed")
						|| DrawImages.enemyUnits.elementAt(i).name.equals("HumveeRed")
								&& DrawImages.enemyUnits.elementAt(i).moveToTarget == false
								&& DrawImages.enemyUnits.elementAt(i).attackEnemy == false) {
					DrawImages.enemyUnits.elementAt(i).moveToX = DrawImages.buildings.elementAt(0).getX();
					DrawImages.enemyUnits.elementAt(i).moveToY = DrawImages.buildings.elementAt(0).getY();
					DrawImages.enemyUnits.elementAt(i).moving = true;

				}
			}
		}
	}
}
package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * Creates all of the buttons
 * 
 * @author Justin
 *
 */
public class Button {
	// Declare variables
	private Power POW = new Power();
	private MyActionListener2 BAR = new MyActionListener2();
	private MyActionListener3 FAC = new MyActionListener3();
	private MyActionListener4 REF = new MyActionListener4();
	private MyActionListener5 ROT = new MyActionListener5();
	private MyActionListener6 RIT = new MyActionListener6();
	private MyActionListener7 HVT = new MyActionListener7();
	private MyActionListener8 MTK = new MyActionListener8();
	private MyActionListener9 ART = new MyActionListener9();
	private MyActionListener10 HMV = new MyActionListener10();
	private MyActionListener11 MMT = new MyActionListener11();
	private MyActionListener12 TRT = new MyActionListener12();
	public static Border border = BorderFactory.createLineBorder(new Color(255, 255, 255));
	public static Vector<JButton> buttons = new Vector<JButton>();
	public static String clicked = " ";

	// Creates all of the buttons and sets the size and location and adds the
	// appropriate listener
	/**
	 * Creates all of the buttons and adds the listeners
	 * 
	 * @param XY   A vector of all of the X and Y coordinates for the buttons
	 * @param name The name for the button
	 */
	public Button(int[] XY, String[] name) {
		for (int i = 0; i < name.length; i++) {

			JButton temp = new JButton(name[i]);
			temp.setName(name[i]);
			temp.setBounds(XY[i * 2], XY[(i * 2) + 1], 100, 40);

			if (name[i].equals("PowerPlant")) {
				temp.addActionListener(POW);
			}
			if (name[i].equals("Barracks")) {
				temp.addActionListener(BAR);
			}
			if (name[i].equals("Factory")) {
				temp.addActionListener(FAC);
			}
			if (name[i].equals("Refinery")) {
				temp.addActionListener(REF);
			}
			if (name[i].equals("Rocket")) {
				temp.addActionListener(ROT);
			}
			if (name[i].equals("Rifle")) {
				temp.addActionListener(RIT);
			}
			if (name[i].equals("Harvester")) {
				temp.addActionListener(HVT);
			}
			if (name[i].equals("MediumTank")) {
				temp.addActionListener(MTK);
			}
			if (name[i].equals("Artillery")) {
				temp.addActionListener(ART);
			}
			if (name[i].equals("Humvee")) {
				temp.addActionListener(HMV);
			}
			if (name[i].equals("MammothTank")) {
				temp.addActionListener(MMT);
			}
			if (name[i].equals("Turret")) {
				temp.addActionListener(TRT);
			}
			// sets the background, border, foreground, and bounds of the button
			temp.setBackground(new Color(0, 0, 0));
			temp.setBorder(border);
			temp.setForeground(new Color(255, 255, 255));
			temp.setBounds(XY[i * 2], XY[(i * 2) + 1], 100, 40);
			buttons.add(temp);
		}
	}

	/**
	 * Returns the button vector
	 * 
	 * @return Returns the button vector
	 */
	public Vector<JButton> addButtons() {
		return buttons;
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class Power implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "PowerPlant";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener2 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Barracks";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener3 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Factory";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener4 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Refinery";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener5 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Rocket";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener6 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Rifle";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener7 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Harvester";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener8 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "MediumTank";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener9 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Artillery";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener10 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Humvee";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener11 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "MammothTank";
		}
	}

	/**
	 * Listens for the button to be pressed then sets clicked to that button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener12 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			clicked = "Turret";
		}
	}
}

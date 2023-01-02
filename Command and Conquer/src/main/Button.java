package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

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
	private static Border border = BorderFactory.createLineBorder(new Color(255, 255, 255));
	public static Vector<JButton> buttons = new Vector<JButton>();
	public static String clicked = " ";

	// Creates all of the buttons and sets the size and location and adds the
	// appropriate listener
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
			// sets the background, border, foreground, and bounds of the button
			temp.setBackground(new Color(0, 0, 0));
			temp.setBorder(border);
			temp.setForeground(new Color(255, 255, 255));
			temp.setBounds(XY[i * 2], XY[(i * 2) + 1], 100, 40);
			buttons.add(temp);
		}
	}

	// Returns the button vector
	public Vector<JButton> addButtons() {
		return buttons;
	}

	// Listeners listen for the button to be pressed then sets clicked to that
	// button
	private class Power implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("PowerPlant");
			clicked = "PowerPlant";
		}
	}

	private class MyActionListener2 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Barracks");
			clicked = "Barracks";
		}
	}

	private class MyActionListener3 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Factory");
			clicked = "Factory";
		}
	}

	private class MyActionListener4 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Refinery");
			clicked = "Refinery";
		}
	}

	private class MyActionListener5 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Rocket trooper");
			clicked = "Rocket";
		}
	}

	private class MyActionListener6 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Rifle");
			clicked = "Rifle";
		}
	}

	private class MyActionListener7 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Harvester");
			clicked = "Harvester";
		}
	}

	private class MyActionListener8 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("MediumTank");
			clicked = "MediumTank";
		}
	}

	private class MyActionListener9 implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Artillery");
			clicked = "Artillery";
		}
	}
}

package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Creates the start menu
 * 
 * @author Justin
 *
 */
public class StartMenu extends JPanel {
	private static final long serialVersionUID = 5652386887047783656L;
	public static JFrame frame = new JFrame();
	private ImageIcon startPic = new ImageIcon(this.getClass().getResource("StartMenuPic.png"));
	private Image StartMenuPic = startPic.getImage();

	/**
	 * Creates the menu and adds the pictures to it
	 * 
	 * @param num
	 */
	public StartMenu(int num) {

		if (num == 1) {
			StartMenu.frame.getContentPane();
			StartMenu.frame.setSize(Play.screenSize.width, Play.screenSize.height);
			StartMenu.frame.setResizable(false);
			StartMenu.frame.setFocusable(true);
			StartMenu.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			StartMenu.frame.add(new StartMenu(0));
			StartMenu.frame.setVisible(true);
		}

	}

	/**
	 * Paints all of the components and adds the buttons
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(StartMenuPic, 0, 0, Play.screenSize.width, Play.screenSize.height, this);
		JButton startGame = new JButton("CLICK TO START");
		startGame.addActionListener(new MyActionListener());
		startGame.setBounds((Play.screenSize.width / 2) - 55, (Play.screenSize.height / 2) - 20, 110, 40);
		startGame.setBackground(new Color(0, 0, 0));
		startGame.setBorder(Button.border);
		startGame.setForeground(new Color(255, 255, 255));
		add(startGame);
	}

	/**
	 * Listener for button
	 */
	private class MyActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			Play.startGame = true;
			StartMenu.frame.setVisible(false);
		}
	}
}

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
 * Creates the end game menu
 * 
 * @author Justin
 *
 */
public class EndGameMenu extends JPanel {
	private static final long serialVersionUID = 5652386887047783656L;
	public static JFrame frame = new JFrame();
	private ImageIcon youWin = new ImageIcon(this.getClass().getResource("YouWin.png"));
	private Image WinPic = youWin.getImage();
	private ImageIcon youLose = new ImageIcon(this.getClass().getResource("GameOver.png"));
	private Image losePic = youLose.getImage();
	private boolean playerWon = false;

	/**
	 * Creates the frame and adds the images
	 * 
	 * @param num
	 * @param playerWon True if the player won false if the computer won
	 */
	public EndGameMenu(int num, boolean playerWon) {
		this.playerWon = playerWon;
		if (num == 1) {
			Play.EndGameMenuCreated = true;
			EndGameMenu.frame.getContentPane();
			EndGameMenu.frame.setSize(Play.screenSize.width, Play.screenSize.height);
			EndGameMenu.frame.setResizable(false);
			EndGameMenu.frame.setFocusable(true);
			EndGameMenu.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			EndGameMenu.frame.add(new EndGameMenu(0, playerWon));
			EndGameMenu.frame.setVisible(true);
		}

	}

	/**
	 * Draws all of the images and button
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		if (this.playerWon == true) {
			g.drawImage(WinPic, 0, 0, Play.screenSize.width, Play.screenSize.height, this);
		} else {
			g.drawImage(losePic, 0, 0, Play.screenSize.width, Play.screenSize.height, this);
		}
		JButton endGame = new JButton("End Game");
		endGame.addActionListener(new MyActionListener());
		endGame.setBounds((Play.screenSize.width / 2) - 55, (Play.screenSize.height / 2) + 20, 110, 40);
		endGame.setBackground(new Color(0, 0, 0));
		endGame.setBorder(Button.border);
		endGame.setForeground(new Color(255, 255, 255));
		add(endGame);

	}

	/**
	 * Listener for the button
	 * 
	 * @author Justin
	 *
	 */
	private class MyActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			EndGameMenu.frame.setVisible(false);
			System.exit(0);
		}
	}
}

package edu.miracosta.cs113.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

public class MenuGUI extends JFrame
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 450;
	
	private JLabel title;
	private JLabel groupMembers;
	
	//Launch game buttons
	private JButton onePlayerButton;
	private JButton twoPlayerButton;
	private JButton threePlayerButton;
	
	//Button handlers
	private LaunchOnePlayerGame launchOnePlayerGame;
	private LaunchTwoPlayerGame launchTwoPlayerGame;
	private LaunchThreePlayerGame launchThreePlayerGame;
	
	public MenuGUI() {
		
		/**
		 * Frame properties
		 */
		setTitle("CS113 Texas Hold 'Em");
		Container menu = getContentPane();
		setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setBackground(new Color(0,128,0)); //dark green
		
		/**
		 * Labels
		 */
		title = new JLabel("Texas Hold 'Em");
		title.setFont(new Font("serif", Font.BOLD, 48));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setForeground(Color.white);
		title.setBorder(new EmptyBorder(10, 10, 10, 10));
		groupMembers = new JLabel("by Kyle Johnson, Matthew Kressin, and Eric Thompson", SwingConstants.CENTER);
		groupMembers.setFont(new Font("Gill Sans MT", Font.BOLD, 24));
		groupMembers.setHorizontalAlignment(JLabel.CENTER);
		groupMembers.setForeground(Color.white);
		groupMembers.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		/**
		 * Buttons
		 */
		onePlayerButton = new JButton("1 Player");
		onePlayerButton.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		onePlayerButton.setBackground(new Color(236, 240, 241));
		twoPlayerButton = new JButton("2 Players");
		twoPlayerButton.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		twoPlayerButton.setBackground(new Color(236, 240, 241));
		threePlayerButton = new JButton("3 Players");
		threePlayerButton.setFont(new Font("Gill Sans MT", Font.BOLD, 14));
		threePlayerButton.setBackground(new Color(236, 240, 241));


		
		/**
		 * Button listeners
		 */
		launchOnePlayerGame = new LaunchOnePlayerGame();
		onePlayerButton.addActionListener(launchOnePlayerGame);
		
		launchTwoPlayerGame = new LaunchTwoPlayerGame();
		twoPlayerButton.addActionListener(launchTwoPlayerGame);
		
		launchThreePlayerGame = new LaunchThreePlayerGame();
		threePlayerButton.addActionListener(launchThreePlayerGame);
		
		
		
		/**
		 * Format buttons
		 */
		JPanel buttons = new JPanel();
		buttons.setBackground(new Color(0,128,0)); //dark green
		buttons.setLayout(new GridLayout(1,3));
		buttons.setBorder(new EmptyBorder(20, 20, 20, 20));

		
		/**
		 * Add to window
		 */
		menu.add(title);
		menu.add(groupMembers);
		buttons.add(onePlayerButton);
		buttons.add(twoPlayerButton);
		buttons.add(threePlayerButton);
		menu.add(buttons);
		
		/**
		 * Show window
		 */
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private class LaunchOnePlayerGame implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
	
	private class LaunchTwoPlayerGame implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
	
	private class LaunchThreePlayerGame implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}

	
	
	public static void main(String[] args)
	{
		MenuGUI menuGUI = new MenuGUI();
	}
	
}
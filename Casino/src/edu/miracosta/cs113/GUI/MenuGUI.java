package edu.miracosta.cs113.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

public class MenuGUI extends JFrame
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 450;
	
	private static final Color DARK_GREEN = new Color(0,128,0);
	//JPanels
	private JPanel text;
	private JPanel namePanel;
	private JPanel numBotsPanel;
	
	//Labels
	private JLabel title;
	private JLabel groupMembers;
	private JLabel numBotsLabel;
	private JLabel playerNameLabel;
	
	
	//Text field
	private JTextField playerName;
	
	//Launch game numBotsPanel
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
		menu.setBackground(DARK_GREEN);
		
		/**
		 * Labels
		 */
		title = new JLabel("Texas Hold 'Em");
		title.setFont(new Font("serif", Font.BOLD, 48));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setForeground(Color.white);
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		groupMembers = new JLabel("by Kyle Johnson, Matthew Kressin, and Eric Thompson", SwingConstants.CENTER);
		groupMembers.setFont(new Font("Gill Sans MT", Font.BOLD, 24));
		groupMembers.setHorizontalAlignment(JLabel.CENTER);
		groupMembers.setForeground(Color.white);
		groupMembers.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		/**
		 * Player name
		 */
		playerNameLabel = new JLabel("Your name");
		playerNameLabel.setFont(new Font("Gill Sans MT", Font.BOLD, 12));
		playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
		playerNameLabel.setForeground(Color.white);
		playerName = new JTextField();
		
		
		/**
		 * numBotsPanel
		 */
		numBotsLabel = new JLabel("Number of Bots");
		numBotsLabel.setFont(new Font("Gill Sans MT", Font.BOLD, 12));
		numBotsLabel.setHorizontalAlignment(JLabel.CENTER);
		numBotsLabel.setForeground(Color.white);
		
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
		 * Format panels
		 */
		text = new JPanel();
		text.setLayout(new GridLayout(3,1));
		text.setBackground(DARK_GREEN);
		
		namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(1,4));
		namePanel.setBackground(DARK_GREEN); 
		namePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		numBotsPanel = new JPanel();
		numBotsPanel.setLayout(new GridLayout(1,4));
		numBotsPanel.setBackground(DARK_GREEN); 
		numBotsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		
		/**
		 * Add to window
		 */
		text.add(title);
		text.add(groupMembers);
		
		namePanel.add(playerNameLabel);
		namePanel.add(playerName);
		
		numBotsPanel.add(numBotsLabel);
		numBotsPanel.add(onePlayerButton);
		numBotsPanel.add(twoPlayerButton);
		numBotsPanel.add(threePlayerButton);
		
		menu.add(text);
		menu.add(namePanel);
		menu.add(numBotsPanel);
		
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
			PokerGUI game = new PokerGUI(playerName.getText(), 1);
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
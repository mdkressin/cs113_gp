package edu.miracosta.cs113.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.miracosta.cs113.Bot;
import edu.miracosta.cs113.Card;
import edu.miracosta.cs113.Player;
import edu.miracosta.cs113.Table;

/**
 * GUI for each player
 *      ___	  ___
 * 	   |   | |   |
 * 	   | K | | J |
 * 	   |___| |___|
 * 		  Name
 * 		 $Money
 */
public class PlayerGUI extends JPanel {
	
	private static final Color DARK_GREEN = new Color(30, 130, 76);
		
	private JPanel cards;
	
	private JLabel card1Label;
	private JLabel card2Label;
	
	private JLabel name;
	private JLabel money;

	public PlayerGUI(Player player) {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(DARK_GREEN);

		
		
		Card playerCard1 = player.getHand().getHoleCards()[0];
		Card playerCard2 = player.getHand().getHoleCards()[1];
		
		if(playerCard1 != null) 
		{
			String playerCard1FilePath = playerCard1.getFilePath();
			BufferedImage card1Image = getCardImage(playerCard1FilePath);
			card1Label = new JLabel(new ImageIcon(card1Image));
			String playerCard2FilePath = playerCard2.getFilePath();
			BufferedImage card2Image = getCardImage(playerCard2FilePath);
			card1Label = new JLabel(new ImageIcon(card2Image));
		} 
		else 
		{
			card1Label = new JLabel("?");
			card2Label = new JLabel("?");
		}

		cards = new JPanel();
		cards.setLayout(new GridLayout(1,2));
		cards.add(card1Label);
		cards.add(card2Label);
		
		name = new JLabel(player.getName());
		money = new JLabel("$" + player.getMoney() + "");
		
		this.add(cards);
		this.add(name);
		this.add(money);
	}
	
	public void dealHand(Player player, boolean isBot) {
		
		if(isBot) 
		{
			BufferedImage backCardImage = getCardImage("card_back.png");
			card1Label = new JLabel(new ImageIcon(backCardImage));
			card2Label = new JLabel(new ImageIcon(backCardImage));

		} 
		else 
		{
			Card playerCard1 = player.getHand().getHoleCards()[0];
			Card playerCard2 = player.getHand().getHoleCards()[1];
			
			String playerCard1FilePath = playerCard1.getFilePath();
			BufferedImage card1Image = getCardImage(playerCard1FilePath);
			card1Label = new JLabel(new ImageIcon(card1Image));
			String playerCard2FilePath = playerCard2.getFilePath();
			BufferedImage card2Image = getCardImage(playerCard2FilePath);
			card2Label = new JLabel(new ImageIcon(card2Image));
		}
	}
	
	private BufferedImage getCardImage(String cardFilePath) {
				
		try {
			File imageFile = new File("" + cardFilePath);
			return ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}

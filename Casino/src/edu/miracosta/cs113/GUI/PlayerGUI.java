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
		
		
		if(player.getHand().getCards() != null) 
		{
			Card playerCard1 = player.getHand().getHoleCards()[0];
			Card playerCard2 = player.getHand().getHoleCards()[1];
			
			BufferedImage card1Image = getCardImage(playerCard1.getFilePath());
			card1Label = new JLabel(new ImageIcon(card1Image));
			BufferedImage card2Image = getCardImage(playerCard2.getFilePath());
			card2Label = new JLabel(new ImageIcon(card2Image));
		} 
		else 
		{
			BufferedImage card1Image = getCardImage("card_back.png");
			card1Label = new JLabel(new ImageIcon(card1Image));
			BufferedImage card2Image = getCardImage("card_back.png");
			card2Label = new JLabel(new ImageIcon(card2Image));
		}
		
		cards = new JPanel();
		cards.setLayout(new GridLayout(1,2));
		cards.add(card1Label);
		cards.add(card2Label);
		
		name = new JLabel(player.getName());
		money = new JLabel(player.getMoney() + "");
		
		this.add(cards);
		this.add(name);
		this.add(money);
	}
	
	private BufferedImage getCardImage(String cardFilePath) {
				
		try {
			System.out.println("----------"+ cardFilePath);
			return ImageIO.read(new File("./edu.miracosta.cs113.assets/" + cardFilePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}

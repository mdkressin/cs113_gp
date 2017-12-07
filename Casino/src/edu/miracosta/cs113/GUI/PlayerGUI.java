package edu.miracosta.cs113.GUI;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

	public PlayerGUI(Player player, boolean isBot) {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(DARK_GREEN);

		card1Label = new JLabel("");
		card2Label = new JLabel("");
		
		Card playerCard1 = player.getHand().getHoleCards()[0];
		Card playerCard2 = player.getHand().getHoleCards()[1];
		
		if(playerCard1 != null) 
		{
			setCardImage(playerCard1.getFilePath(), card1Label, isBot);
			setCardImage(playerCard2.getFilePath(), card2Label, isBot);
		} 
		else 
		{
			setCardImage("card_back.png", card1Label, isBot);
			setCardImage("card_back.png", card2Label, isBot);
		}

		cards = new JPanel();
		cards.setLayout(new GridLayout(1,2));
		cards.add(card1Label);
		cards.add(card2Label);
		
		name = new JLabel(player.getName());
		formatLabel(name, new Font("Gill Sans MT", Font.PLAIN, 12), new Color(255,255,255));
		
		money = new JLabel("$" + player.getMoney() + "");
		formatLabel(money, new Font("Gill Sans MT", Font.PLAIN, 16), new Color(255,255,255));
		
		this.add(cards);
		this.add(name);
		this.add(money);
	}
	
	
	private void formatLabel(JLabel label, Font font, Color fontColor) {
		label.setFont(font);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(fontColor);
		//label.setBorder(new EmptyBorder(5, 5, 5, 5));
	}


	private void setCardImage(String cardFilePath, JLabel label, boolean isBot) 
	{	
		if(isBot)
		{
			ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/edu/miracosta/cs113/assets/card_back.png").getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
			label.setIcon(imageIcon);
		}
		else
		{
			ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/edu/miracosta/cs113/assets/" + cardFilePath).getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
			label.setIcon(imageIcon);
		}
	}
	
}

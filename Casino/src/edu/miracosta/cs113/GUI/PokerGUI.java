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
import edu.miracosta.cs113.Round;
import edu.miracosta.cs113.Table;

/**
 * The game's main GUI componenet
 * 
 *      	 ___   ___		 ___   ___
 * 	   		|   | |   |		|   | |   |
 * 	   		| ? | | ? |		| ? | | ? |			<--- botsPanel
 * 	   		|___| |___|		|___| |___|
 * 		  		Bot1			    Bot2
 * 		 	   $Money 		   $Money
 * 
 *      			 ___	   ___   ___	   ___
 * 	   			|   | |   | |   | |   |
 * 	   			| 7 | | J | | Q | | 3 |				<--- dealerPanel
 * 	   			|___| |___| |___| |___|
 * 
 *      ___	  ___   		 ___	_________   ______   _______
 * 	   |   | |   | 		|            | |      | |       |
 * 	   | A | | K | 		| Call/Check | | Fold | | Raise |    <--- userPanel
 * 	   |___| |___| 		|____________| |______| |_______|
 *        Name
 *       $Money
 */
public class PokerGUI extends JFrame {
	
	private static final Color DARK_GREEN = new Color(30, 130, 76);
	
    private static final int BIG_BLIND = 10; 
    private static final int START_MONEY = 500;
    
    private final Player humanPlayer;
    private final Table table;

    //Panels
    private JPanel botsPanel;
    private JPanel dealerPanel;
    private JPanel userPanel;
    
    //Player components
    private JButton call;
    private JButton fold;
    private JButton raise;
    

    /**
     * Constructor.
     */
    public PokerGUI(String playerName, int numBots) {
    	
        setTitle("CS113 Texas Hold Em");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(DARK_GREEN);
        setLayout(new GridLayout(3,1));

        /**
         * Initialize player and game variables
         */
        humanPlayer = new Player(playerName, START_MONEY);        
        table = new Table(humanPlayer, numBots);
        
        
        /**
         * Show frame
         */
        setSize(800, 450);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

        /**
         * Start game
         */
		play();
    }
    
    public void play() {
    	
    	//Infinite loop for now
    	while(true) {
    		Round round = new Round(table);
    		
    		System.out.println("New round starting");
            round.resetRound();
    		redrawTable(round);
    		
            System.out.println("Initial deal");
            round.startRound();
    		redrawTable(round);
            round.cyclePlayers();
            
            System.out.println("flop");
            round.flop();
    		redrawTable(round);
            round.cyclePlayers();

            System.out.println("turn");
            round.turn();
    		redrawTable(round);
            round.cyclePlayers();

            System.out.println("river");
            round.river();
    		redrawTable(round);
            round.cyclePlayers();
            System.out.println("Round over");
    	}
    }
    
    /**
     * Redraws all dyanmic elements (cards on the table, pot, player money, etc)
     * 
     * @param round
     */
    public void redrawTable(Round round) {
    		
    	getContentPane().removeAll();
    	
    	/**
         * Bot panel (top)
         */
        botsPanel = new JPanel();
        botsPanel.setBackground(DARK_GREEN);
        botsPanel.setLayout(new FlowLayout());
        
        ArrayList<Player> players = table.getPlayers();
        
        for(int i = 1; i < players.size(); i++) 
        {
        	botsPanel.add(new PlayerGUI(players.get(i)));
        }
        
        /**
         * Dealer panel (middle)
         */
        dealerPanel = new JPanel();
        dealerPanel.setBackground(DARK_GREEN);
        dealerPanel.setLayout(new FlowLayout());
        dealerPanel.add(new JLabel("$" + round.getPot()));
        
        for(Card c : round.getCardsInPlay()) 
        {
        	if(c != null)
        	{
        		dealerPanel.add(new JLabel(new ImageIcon(getCardImage(c.getFilePath()))));
        	}
        }
        
        
        /**
         * User panel (bottom)
         */
        userPanel = new JPanel();
        userPanel.setBackground(DARK_GREEN);
        userPanel.setLayout(new FlowLayout());
        userPanel.add(new PlayerGUI(humanPlayer));
        userPanel.add(new JButton("Call/Check"));
        userPanel.add(new JButton("Fold"));
        userPanel.add(new JButton("Raise"));

        
        //Add all game GUI elements to window
      	this.add(botsPanel);
      	this.add(dealerPanel);
      	this.add(userPanel);
    }
    
    private void setCardImage(String cardFilePath, JLabel label) 
	{	
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/edu/miracosta/cs113/assets/" + cardFilePath).getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
		label.setIcon(imageIcon);
	}
    
    private BufferedImage getCardImage(String cardFilePath) {
		
		try {
			File imageFile = new File("src/edu/miracosta/cs113/assets/" + cardFilePath);
			return ImageIO.read(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
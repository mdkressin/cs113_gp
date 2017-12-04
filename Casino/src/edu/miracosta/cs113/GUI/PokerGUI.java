package edu.miracosta.cs113.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import edu.miracosta.cs113.Bot;
import edu.miracosta.cs113.Card;
import edu.miracosta.cs113.Player;
import edu.miracosta.cs113.Table;

/**
 * The game's main frame.
 * 
 * This is the core class of the Swing UI client application.
 */
public class PokerGUI extends JFrame {
	
    private static final int BIG_BLIND = 10; 
    private static final int START_MONEY = 500;
    
    private final Player humanPlayer;

    
    
    private JPanel botsPanel;

    /**
     * Constructor.
     */
    public PokerGUI(String playerName, int numBots) {
    	
        super("Texas Hold'em poker");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0, 128, 0)); //Dark green
        setLayout(new GridBagLayout());

        /**
         * Initialize player and game variables
         */
        humanPlayer = new Player(playerName, START_MONEY);        
        Table table = new Table(humanPlayer, numBots);
        
        
        /**
         * Create persistent game GUI components
         */
        botsPanel = new JPanel();
        botsPanel.setLayout(new GridLayout(1, numBots));
        
        ArrayList<Player> players = table.getPlayers();
        
        for(int i = 1; i < players.size(); i++) 
        {
        		botsPanel.add(new PlayerGUI(players.get(i)));
        }
        
        
        // Show the frame.
        setSize(800, 450);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Add all game GUI elements to window
		this.add(botsPanel);

        /**
         * Start game
         */
    }
}
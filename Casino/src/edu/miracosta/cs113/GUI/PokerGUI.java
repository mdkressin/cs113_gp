package edu.miracosta.cs113.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.miracosta.cs113.Card;
import edu.miracosta.cs113.Player;
import edu.miracosta.cs113.Round;
import edu.miracosta.cs113.Table;

/**
 * The game's main GUI component
 * 
 *      	     ___   ___		 ___   ___
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
 * 	   |___| |___| 		|__$300/$0___| |______| |_______|
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
    

    /**
     * Constructor.
     */
    public PokerGUI(String playerName, int numBots) {
    	
        setTitle("CS113 Texas Hold Em");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(DARK_GREEN);
        setLayout(new GridLayout(3,1));

        /**
         * Initialize player and table variables
         */
        humanPlayer = new Player(playerName, START_MONEY, false);        
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
    	
    		Round round = new Round(table);
    		
	    	//Infinite loop for now, TODO: stop when user money < 0
	    	while(true) {
	    		
	    		round.resetRound();
	    		//redrawTable(round);
	    		
				//Give each player two cards
				round.startRound();
				
				//Redraw table after all players have cards, waits for user input
				redrawTable(round);
				
				//After user input and betting is over, show flop() and redraw
	            round.flop();
	    		redrawTable(round);
	    		
				//After user input and betting is over, show turn() and redraw
	            round.turn();
	    		redrawTable(round);
	
				//After user input and betting is over, show river() and redraw
	            round.river();
	    		redrawTable(round);
	    	}
    }
   
    /**
     * Redraws all dynamic elements (cards on the table, pot, player money, etc)
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
        	botsPanel.add(new PlayerGUI(players.get(i), true));
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
        userPanel.add(new PlayerGUI(humanPlayer, false));
        userPanel.add(setCallButton(round));
        userPanel.add(new JButton("Fold"));
        userPanel.add(new JButton("Raise"));

        
        //Add all game GUI elements to window
      	this.add(botsPanel);
      	this.add(dealerPanel);
      	this.add(userPanel);
    }
    
    /**
     * Set up the correct label and listener for "Call" button
     * 
     * @param round
     * @return JButton
     */
    public JButton setCallButton(Round round)
    {
    	JButton callBtn;
        
        if(round.getLastBet() == 0) 
        {
        	callBtn = new JButton("Check");
        } 
        else 
        {
        	callBtn = new JButton("Call $" + round.getLastBet());
        }
        
        callBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                playerChoice(round, 1);
                
                try {
					cycleBots(round);
				} catch (Exception error) {
					error.printStackTrace();
				}
            }
        });
        return callBtn;
    }
    
    /**
     * Iterates through each bot 
     * @throws Exception 
     */
    public void cycleBots(Round round) throws Exception
    {
    		ArrayList<Player> players = round.players;
    		int index = round.getIndex();
    		
    		//Iterate through all bots
    		while(players.get(index).isBot())
    		{
    			//Pause for 1 second, better user experience than instant move
				pause(1000);
				
				//TODO: Make decision based on strength of hand
				//For now, bot calls/checks no matter what
				playerChoice(round, 1);			
				
				//Alter resulting round variables from human or bot actions
				
				round.moveToNextPlayer();
    		}
    }

	/**
	 * Has the player make a choice of calling, folding, raising, or checking
	 * 
	 * @param player The player that made the choice
	 * @param choice The choice
	 * 					1 -> Call/Check
	 * 					2 -> Fold
	 * 					3 -> Raise			
	 */
	public void playerChoice(Round round, int choice)
	{
		Player player = round.players.get(round.getIndex());
		
	    if (choice == 1)
	    {
	    		player.call(round.getLastBet());
	    }
	    else if (choice == 2)
	    {
	    		player.fold();
	    }
	    else if (choice == 3)
	    {
	        /*
	        int playerBet = ;
	        round.raise(playerBet);
	        */
	        round.raise(100);
	    }
	}

    
    
    public void pause(int milliseconds) throws Exception
    {
        ActionListener taskPerformer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
        };
        Timer timer = new Timer(milliseconds ,taskPerformer);
        timer.setRepeats(false);
        timer.start();

        //Thread.sleep(milliseconds);
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
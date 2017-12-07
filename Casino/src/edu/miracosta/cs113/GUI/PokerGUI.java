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

import edu.miracosta.cs113.Bot;
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
	    		redrawTable(round);
	    		
			//Give each player two cards
			round.startRound();
			
			//Redraw table after all players have cards
			redrawTable(round);
			
			cycleBots(round);
	            
	            System.out.println("flop");
	            round.flop();
	    		redrawTable(round);
	            round.cycleBots();
	
	            System.out.println("turn");
	            round.turn();
	    		redrawTable(round);
	            round.cycleBots();
	
	            System.out.println("river");
	            round.river();
	    		redrawTable(round);
	            round.cycleBots();
	            System.out.println("Round over");
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
        userPanel.add(new JButton("Call/Check"));
        userPanel.add(new JButton("Fold"));
        userPanel.add(new JButton("Raise"));

        
        //Add all game GUI elements to window
      	this.add(botsPanel);
      	this.add(dealerPanel);
      	this.add(userPanel);
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
			playerChoice(players.get(index), round, 1);			
			
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
	public void playerChoice(Player player, Round round, int choice)
	{
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
	        //System.out.println("Enter amount to raise by:");
	        int playerBet = ;
	        player.bet(playerBet);
	        lastBetter = player; //So it keeps looping until we reach the last player who raised
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
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
 *				 ___   ___   ___   ___
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
public class PokerGUI extends JFrame 
{
	
	private static final Color DARK_GREEN = new Color(30, 130, 76);
	
    private static final int BIG_BLIND = 10; 
    private static final int START_MONEY = 500;
    
    private final Player humanPlayer;
    private final Table table;
    
    private Round round;
    private int stage;

    //Panels
    private JPanel botsPanel;
    private JPanel dealerPanel;
    private JPanel userPanel;
    
    //Dealer Labels
    private JLabel potLabel;
    private JLabel[] dealerCardLabels;

    
    private JButton callBtn;
    private JButton foldBtn;
    private JButton raiseBtn;
    
    private PlayerGUI[] botGUIs;
    
    private PlayerGUI humanGUI;
    

    /**
     * Constructor.
     */
    public PokerGUI(String playerName, int numBots) {
    	
        setTitle("CS113 Texas Hold Em");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(DARK_GREEN);
        setLayout(new GridLayout(3,1));

        //Initialize player and table variables
        humanPlayer = new Player(playerName, START_MONEY, false);        
        table = new Table(humanPlayer, numBots);
        round = new Round(table);
        
        //Instantiate panels with default styling
    	botsPanel = createPanel();
    	dealerPanel = createPanel();
    	userPanel = createPanel();
    	
    	//Instantiate Bot GUI's
    	botGUIs = new PlayerGUI[numBots];
    	for(int i = 1; i < round.players.size(); i++) 
        {
    		botGUIs[i-1] = new PlayerGUI(round.players.get(i), true);
        	botsPanel.add(botGUIs[i-1]);
        }
    	
    	//Instantiate dealer labels
    	potLabel = new JLabel("Pot: $0");
    	dealerPanel.add(potLabel);
    	dealerCardLabels = new JLabel[5];
    	for(int j = 0; j < 5; j++)
    	{
    		dealerCardLabels[j] = new JLabel("");
    		dealerPanel.add(dealerCardLabels[j]);
    	}
    	
    	
    	//Instantiate human GUI
    	humanGUI = new PlayerGUI(humanPlayer, false);
        
        //Create and set listeners on control buttons
        callBtn = new JButton("Call");
        setListener(callBtn, 1);
        foldBtn = new JButton("Fold");
        setListener(foldBtn, 2);
        raiseBtn = new JButton("Raise");
        setListener(raiseBtn, 3);
        
        //Add human GUI and control buttons to user panel
        userPanel.add(humanGUI);
        userPanel.add(callBtn);
        userPanel.add(foldBtn);
        userPanel.add(raiseBtn);
       
        //Add all panels
        add(botsPanel);
      	add(dealerPanel);
      	add(userPanel);
      	
        //Show frame
        setSize(800, 450);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		System.out.println("Table and human created, calling play()");
		
		//Start game
		newRound();

    }

    public void nextStage() 
    {
    	stage++;
    	switch (stage) 
    	{
	        case 1:  round.flop();
	                 break;
	        case 2:  round.turn();
	                 break;
	        case 3:  round.river();
	                 break;
	        case 4:  endRound();
	        		 break;
    	}
    	updateGUI();
    }
    
	public void newRound() 
	{	
		stage = 0;
		round.startRound();
		updateGUI();
    	//TODO: loop, stop when user money < 0
    }
	
	public void endRound()
	{
		//TODO: show cards, find winner, and award winnings logic
		stage = 0;
		round.resetRound();
		resetGUI();
		newRound();
	}
   
    /**
     * Updates all dynamic elements (cards on the table, pot, player money, etc)
     */
    public void updateGUI() {
    		
    	//Bots panel
        updateBotsPanel();
        
        //Dealer panel
        updateDealerPanel();
        
        //User panel
        updateUserPanel();
    }
    
    /**
     * Clear GUI elements for new round
     */
    public void resetGUI() 
    {
    	for(int j = 0; j < 5; j++)
    	{
    		dealerCardLabels[j].setIcon(null);
    	}
    }
    
    /**
     * Updates the PlayerGUI for each bot
     */
    private void updateBotsPanel() 
    {
    	//Iterate through each bot
    	for(int i = 1; i < round.players.size(); i++) 
        {
    		botGUIs[i-1].update(round.players.get(i));
        }
	}
    
    /**
     * Update PlayerGUI for human user and control button labels
     */
    private void updateUserPanel() 
    {
    	humanGUI.update(round.players.get(0));
    	editControlButtons();
    }

    /**
     * Updates the pot and cards shown of dealer panel
     */
	public void updateDealerPanel() 
    {
    	potLabel.setText("Pot: $" + round.getPot());
    	
    	//TODO: Change dealers cards (cardsInPlay of Round) to array[5]
    	ArrayList<Card> dealerCards = round.getCardsInPlay();
    	
    	for(int i = 0; i < dealerCards.size(); i++)
    	{
    		setCardImage(dealerCards.get(i).getFilePath(), dealerCardLabels[i]);
    	}
    }
	
    
    /**
     * Iterates through each player 
     * 
     * @throws Exception 
     */
    public void cyclePlayers() throws Exception
    {
		ArrayList<Player> players = round.players;
		int index = round.getIndex();
		
		while(players.get(index) != round.getLastBetter()) 
		{
			index = round.getIndex();
			
			//If the current player is a bot, run bot decision process
			if(players.get(index).isBot())
			{
				//Pause for 1 second, better user experience than instant move
				pause(1000);
				
				//TODO: Make decision based on strength of hand
				//	    For now, bot calls/checks no matter what
				System.out.println("bot: " + players.get(index).getName() + " called");
				playerChoice(round, 1);
				updateGUI();
			}
			else
			{
				//If it's the human player, get out of this loop and wait for their move
				break;
			}
			
			round.moveToNextPlayer();
			updateGUI();
		}

		nextStage();
    }

	/**
	 * Has the player make a choice of calling, folding, raising, or checking
	 * 
	 * @param round The round choice was made
	 * @param choice The choice
	 * 					1 -> Call/Check
	 * 					2 -> Fold
	 * 					3 -> Raise			
	 */
	public void playerChoice(Round round, int choice)
	{
		Player player = round.players.get(round.getIndex());
		
	    if (choice == 1) //Call
	    {
	    	player.call(round.getLastBet());
	    }
	    else if (choice == 2) //Fold
	    {
	    	player.fold();
	    }
	    else if (choice == 3) //Raise
	    {
	        /*
	        int playerBet = ;
	        round.raise(playerBet);
	        */
	        round.raise(50);
	    }
	    
	    updateGUI();
	}
	
	/**
     * Sets a listener on passed button
     * 
     * @param button Button to set listener on
     * @param action Corresponding integer for button action
     * 					1 - Call
     * 					2 - Fold
     * 					3 - Raise
     */
    public void setListener(JButton button, int action)
    {
    	button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	//Call player choice
                playerChoice(round, 1);
                
                try {
					cyclePlayers();
				} catch (Exception error) {
					error.printStackTrace();
				}
            }
        });
    }
    
    /**
     * Change text for control buttons
     */
    private void editControlButtons() 
    {
    	if(round.getLastBet() == 0) 
        {
    		callBtn.setText("Check");
        } 
        else 
        {
        	callBtn.setText("Call $" + round.getLastBet());
        }
	}
    
    /**
     * Helper method creates new JPanel with default styling
     * 
     * @return new JPanel
     */
	private JPanel createPanel() {
    	
    	JPanel newPanel = new JPanel();
    	newPanel.setBackground(DARK_GREEN);
    	newPanel.setLayout(new FlowLayout());
    	
    	return newPanel;
	}

	/**
	 * Pause program for passed time
	 * 
	 * @param milliseconds Time to pause
	 * @throws Exception
	 */
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
    
    private void clearCardImage(JLabel label) 
	{	
		label.setIcon(null);
	}
}
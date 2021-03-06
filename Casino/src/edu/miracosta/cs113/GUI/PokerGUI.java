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
import java.util.Random;

import edu.miracosta.cs113.Card;
import edu.miracosta.cs113.HandScore;
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
	
    private static final int BIG_BLIND   = 10; 
    private static final int START_MONEY = 500;
    
    private final Player humanPlayer;
    private final Table table;
    
    private Round round;
    private int stage;
    HandScore score;

    //Panels
    private JPanel botsPanel;
    private JPanel dealerPanel;
    private JPanel userPanel;
    
	private JPanel endRoundPanel;

    
    //Dealer Labels
    private JLabel potLabel;
    private JLabel[] dealerCardLabels;

    
    private JButton callBtn;
    private JButton foldBtn;
    private JButton raiseBtn;
    private JTextField raiseInput;
    private JLabel raiseLabel;
    
    private PlayerGUI[] botGUIs;
    
    private PlayerGUI humanGUI;
    

    /**
     * Constructor.
     */
    public PokerGUI(String playerName, int numBots) 
    {
    	
        setTitle("CS113 Texas Hold Em");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(DARK_GREEN);
        setLayout(new BorderLayout());

        //Initialize player and table variables
        humanPlayer = new Player(playerName, START_MONEY, false);        
        table = new Table(humanPlayer, numBots);
        round = new Round(table);
        score = new HandScore();
        
        //Instantiate panels with default styling
    	botsPanel = createPanel(new FlowLayout());
    	dealerPanel = createPanel(new FlowLayout());
    	userPanel = createPanel(new FlowLayout());
    	endRoundPanel = createPanel(new BorderLayout());
    	
    	
    	//Instantiate Bot GUI's
    	botGUIs = new PlayerGUI[numBots];
    	for(int i = 1; i < round.players.size(); i++) 
        {
    		botGUIs[i-1] = new PlayerGUI(round.players.get(i), true);
        	botsPanel.add(botGUIs[i-1]);
        }
    	
    	//Instantiate dealer labels
    	potLabel = new JLabel("Pot: $0");
		formatLabel(potLabel, new Font("Gill Sans MT", Font.PLAIN, 14), new Color(255,255,255));
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
        
    	//raiseInput = new JTextField("$      ");
        raiseInput = new JTextField("      ");
    	raiseInput.setBackground(Color.WHITE);
    	raiseInput.setForeground(Color.BLACK);
    	
    	raiseLabel = new JLabel("$");
        raiseLabel.setLabelFor(raiseInput);
        raiseLabel.setForeground(Color.WHITE);
        raiseLabel.setEnabled(true);
        
        //Add human GUI and control buttons to user panel
        userPanel.add(humanGUI);
        userPanel.add(callBtn);
        userPanel.add(foldBtn);
        userPanel.add(raiseBtn);
        userPanel.add(raiseLabel);
        userPanel.add(raiseInput);
        
        
       
        //Add all panels
        add(botsPanel, BorderLayout.NORTH);
      	add(dealerPanel, BorderLayout.CENTER);
      	add(userPanel, BorderLayout.SOUTH);
      	
        //Show frame
        setSize(800, 450);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		System.out.println("Table and human created, calling newRound()");
		
		//Start game
		newRound();

    }

    private void formatLabel(JLabel label, Font font, Color fontColor) {
		label.setFont(font);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(fontColor);
		//label.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

    /**
     * Resets stage variables and moves to the next stage
     */
	public void nextStage() 
    {
		System.out.println("\nMove to next stage");
		round.stageReset();
		
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
    	if(stage > 0 && stage < 4)
    	{
    		updateGUI();
    	}
    	
    	if (round.players.get(0).hasFolded())
    	{
    		try 
    		{
    			cyclePlayers();
    		} 
    		catch (Exception e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
    		}
    	}
    }
    
	public void newRound() 
	{	
		//resetGUI();

		stage = 0;
		round.setLastBetter(round.players.get(table.getBigBlind()));
		//Reactivate user buttons
		callBtn.setEnabled(true);
		foldBtn.setEnabled(true);
		raiseBtn.setEnabled(true);

		round.startRound();

		updateGUI();

		if(round.getIndex() != 0)
        {
	        	try {
				cyclePlayers();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		
		updateGUI();
		
    }
	
	public void endRound()
	{
		//TODO: adjust how the winner is found for better accuracy on similar hands
		System.out.println("\nInside endRound()");

		Player bestPlayer = null;
		
		for (Player p : round.players)
		{
			if (!p.hasFolded())
			{
				
				if(bestPlayer == null)
				{
					bestPlayer = p;
				}
				else if (score.calculateScore(p.getHand().getCards())  > score.calculateScore(bestPlayer.getHand().getCards()))
				{
					bestPlayer = p;
				}
			}
		}
		
		bestPlayer.won(round.getPot());
		
		stage = 0;
				
		JLabel winnerLabel = new JLabel("Winner: " + bestPlayer.getName());
		//TODO: Add winning hand label? ^ + "\n" + bestlayer.getHand() or something
		formatLabel(winnerLabel, new Font("Gill Sans MT", Font.PLAIN, 16), new Color(255,255,255));
		endRoundPanel.add(winnerLabel, BorderLayout.NORTH);
		
		JButton newRoundBtn = new JButton("Next Round");
		setNewRoundListener(newRoundBtn);
		endRoundPanel.add(newRoundBtn, BorderLayout.CENTER);
		add(endRoundPanel, BorderLayout.EAST);
		
		//Deactivate user buttons
		callBtn.setEnabled(false);
		foldBtn.setEnabled(false);
		raiseBtn.setEnabled(false);
				
		updateGUI();
		
		for(int i = 1; i < round.players.size(); i++) 
        {
    		System.out.println("-----show cards called");
			if(!round.players.get(i).hasFolded())
			{
	    			botGUIs[i-1].showCards(round.players.get(i));
			}
        }
		
		round.resetRound();

		
	}
   
    /**
     * Updates all dynamic elements (cards on the table, pot, player money, etc)
     */
    public void updateGUI() 
    {
    		//System.out.println("updateGUI() called");
    	
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
	    	endRoundPanel.removeAll();
	    	remove(endRoundPanel);
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
	    	updateControlButtons();
    }

    /**
     * Updates the pot and cards shown of dealer panel
     */
	public void updateDealerPanel() 
	    {
	    	potLabel.setText("Pot: $" + round.getPot());
	    	
	    	//TODO: Change dealers cards (cardsInPlay of Round) to array[5]
	    	// Or maybe not because we call clear() every round which is nice
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
				
		System.out.println("\nStarted cycling players --- start index: " + round.getIndex());
		
				
		while(players.get(round.getIndex()) != round.getLastBetter()) 
		{
			int currentIndex = round.getIndex();
			Player currentPlayer = players.get(currentIndex);
			
			
			//If the human player folded, skip them here to avoid the break; statement
			if (!currentPlayer.isBot() && currentPlayer.hasFolded())
    		{
    			round.moveToNextPlayer();
    		}
			
			System.out.println("Current player: " + currentPlayer.getName() + " ---- index: " + currentIndex);
			
			//If the current player is a bot, run bot decision process
			if(currentPlayer.isBot())
			{	
				playerChoice(currentPlayer.makeDecision(round));
				updateGUI();
				round.moveToNextPlayer();	
			}
			else
			{
				updateGUI();
				//If it's the human player, get out of this loop and wait for their move
				break;
			}
						
		    checkForPrematureEnd();
		    
		} // End while loop
				
		System.out.println("\nStopped cycling players");		
		

		//On break, if player is bot, call nextStage()
		if(round.players.get(round.getIndex()).isBot())
		{
			System.out.println("This player is a bot and while loop was exited: calling nextStage()");
			nextStage();
		}
		else
		{
			if(round.players.get(round.getIndex()) == round.getLastBetter() && stage != 0)
			{
				System.out.println("This player is human and they are the last better: calling nextStage()");
				nextStage();
			}
		}
		
    }
    
    /**
     * End the round if only one player remains
     * (all other players have folded)
     */
    public void checkForPrematureEnd()
    {
    	int numPlayersIn = 0;
	    
	    for(Player p : round.players)
	    {
	    	if(!p.hasFolded())
	    	{
	    		numPlayersIn++;
	    	}
	    }
	    
	    if(numPlayersIn < 2)
	    {
			System.out.println("\nAll players except " + numPlayersIn + " folded, calling endRound()");
	    	endRound();
	    }

    	
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
	public void playerChoice(int choice)
	{
			
		Player player = round.players.get(round.getIndex());
		
	    if (choice == 1) //Call
	    {	    	
	
	    		if((round.getLastBet() - player.getMoneyBet() <= 0))
	    		{
	    			player.setLastAction("Checked");
	        		System.out.println(player.getName() + " checked");
	        		
	        		//On initial betting phase, big blind checking should go to next stage
	        		if(player.isBigBlind())
	        		{
	        			System.out.println("Big blind checked: calling nextStage(");
	        			nextStage();
	        		}

	
	    		}
	    		else
	    		{
	    			//Total raising amount minus money player has already bet
	    			int callAmount = round.getLastBet() - player.getMoneyBet();
	    			
	    			if (player.getMoney() < callAmount)
	    			{
	    				callAmount = player.getMoney();
	    			}
	    			
	    			player.setLastAction("Called $" + callAmount);
	    			player.call(callAmount);
	    			round.addToPot(callAmount);
	    			
	        		System.out.println(player.getName() + " called $" + callAmount);
	        		updateGUI();
	    		}   
	    }
	    else if (choice == 2) //Fold
	    {
    		if (round.getLastBet() <= 0 && player.isBot())
    		{
    			playerChoice(1);
    		}
    		else
    		{
    			player.fold();
	    		player.setLastAction("Folded");
    			System.out.println(round.players.get(round.getIndex()).getName() + " folded: " + round.players.get(round.getIndex()).hasFolded());
    			
    			//If the player that folded was the last better, set to next player
    			if(round.getLastBetter() == player)
    			{
    				round.setLastBetter(round.findNextPlayer(round.getIndex()));
    			}
    		}
	    }
	    else if (choice == 3) //Raise
	    {
    		int raiseAmount = 0;
    		
	    	if (player.isBot())
	    	{
	    		//TODO: calculate bot raise amount
	    		raiseAmount = (score.calculateScore(player.getHand().getCards())/2);
	    	}
	    	else
	    	{
	    		//TODO: if playerBet is lower than lastBet or empty, throw error and dialog
	    		raiseAmount = Integer.parseInt(raiseInput.getText().replaceAll("[\\D]", ""));
	    	}
	    	
	    	//Checking they have enough money for their choice
	    	if (raiseAmount > player.getMoney())
    		{
    			raiseAmount = player.getMoney();
    		}
	    	
	    	if(round.getLastBet() > raiseAmount)
	    	{
	    		playerChoice(1);
	    	}
	    	else
	    	{
		    	round.raise(raiseAmount - player.getMoneyBet());
		    	round.setLastBetter(player);
		    	//round.addToPot(raiseAmount); //Moving add to pot to raise
	    		System.out.println(round.players.get(round.getIndex()).getName() + " raised $" + raiseAmount);
	    		player.setLastAction("Raised $" + raiseAmount);
	    	}
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
    	button.addActionListener(new ActionListener() 
    	{
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	
            		//Call player choice
                playerChoice(action);
                
                round.moveToNextPlayer();
                                
                try 
                {
					cyclePlayers();
				} 
                catch (Exception error) 
                {
					error.printStackTrace();
				}
            }
        });
    }
    
    public void setNewRoundListener(JButton button)
    {
    	button.addActionListener(new ActionListener() 
    	{
            @Override
            public void actionPerformed(ActionEvent e) 
            {
	            	resetGUI();
	            	newRound();
            }
        });
    }
    
    /**
     * Change text for control buttons
     */
    private void updateControlButtons() 
    {
    	//If last bet = 0 or player already put last bet in this round
    	if(round.getLastBet() == 0 || round.getLastBet() -  round.players.get(round.getIndex()).getMoneyBet() == 0) 
        {
    		callBtn.setText("Check");
        } 
        else 
        {
        	callBtn.setText("Call $" + (round.getLastBet() - round.players.get(round.getIndex()).getMoneyBet()));
        }
	}
    
    
    /**
     * Helper method creates new JPanel with default styling
     * 
     * @return new JPanel
     */
	private JPanel createPanel(LayoutManager layout) 
	{
    	
    	JPanel newPanel = new JPanel();
    	newPanel.setBackground(DARK_GREEN);
    	newPanel.setLayout(layout);
    	
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
        ActionListener taskPerformer = new ActionListener() 
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
			}
        };
        Timer timer = new Timer(milliseconds ,taskPerformer);
        timer.start();
        Thread.sleep(milliseconds);

    }
    
    private void setCardImage(String cardFilePath, JLabel label) 
	{	
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/edu/miracosta/cs113/assets/" + cardFilePath).getImage().getScaledInstance(80,100, Image.SCALE_DEFAULT));
		label.setIcon(imageIcon);
	}
    
    private void clearCardImage(JLabel label) 
	{	
		label.setIcon(null);
	}
}
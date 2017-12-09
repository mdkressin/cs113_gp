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
   
    private final int BOT_THRESHOLD  = 10;
    private final int CHANCE_TO_FOLD = 7;
    private final int CHANCE_TO_CALL = 6;
    
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

		System.out.println("Table and human created, calling play()");
		
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
    		round.setLastBet(0);
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
	    	//updateGUI();
    }
    
	public void newRound() 
	{	
		resetGUI();

		stage = 0;
		round.setLastBetter(round.players.get(table.getBigBlind()));
		round.startRound();
		updateGUI();
    	//TODO: loop, stop when user money < 0
    }
	
	public void endRound()
	{
		//TODO: show cards, adjust how the winner is found for better accuracy on similar hands
		
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
		
		
		
		updateGUI();

		for(int i = 1; i < round.players.size(); i++) 
        {
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
    	//System.out.println("UPDATE GUI CALLED");
    	
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
    	editControlButtons();
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
			
			
			//currentPlayer.toggleTurn(); //Toggle on current player turn
			updateGUI();
			
			System.out.println("Current player: " + currentPlayer.getName() + " ---- index: " + currentIndex);
			
			//If the current player is a bot, run bot decision process
			if(currentPlayer.isBot())
			{
				//System.out.println("\nNumber of bots still in hand: ");
				
				//Pause for 1 second, better user experience than instant move
				//pause(10000);
				
				//TODO: Tweak decision making randomness
								
				int botScore = score.calculateScore(currentPlayer.getHand().getCards());
				Random randNum  = new Random();
				//Random value between 1 and 10 to make bot less predictable
				int botRandomness = randNum.nextInt(10) + 1;
				
				if ((botScore < BOT_THRESHOLD/2) || ((round.getLastBet() >= botScore) && (botRandomness <= CHANCE_TO_FOLD))) //Fold
				{
					System.out.println("\n" + currentPlayer.getName() + " folded");
					playerChoice(2); //score is very low OR bet is too high(70% chance this affects choice)
				}
				else if ((botScore < BOT_THRESHOLD) || (botRandomness <= CHANCE_TO_CALL) || (round.getLastBet() >= currentPlayer.getMoney())) //Call
				{
					System.out.println("\n" + currentPlayer.getName() + " called");
					playerChoice(1); //score is somewhat low(60% chance this affects choice)
				}
				else //Raise
				{
					System.out.println("\n" + currentPlayer.getName() + " raised");
					playerChoice(3);
				}
				
				updateGUI();
			}
			else
			{
				//If it's the human player, get out of this loop and wait for their move
				break;
			}
			
			//currentPlayer.toggleTurn(); //Toggle off current player
			updateGUI();
		    round.moveToNextPlayer();

		    checkForPrematureEnd();
			
			//updateGUI();
		}
		
		System.out.println("\nStopped cycling players");
		
		//updateGUI();
		if(players.get(round.getIndex()) == round.getLastBetter())
		{
			nextStage();
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
		
		//TODO: check for money < 0 and run appropriate fixes
		//TODO: Bot responding to player raises is broken
		//TODO: Neither bot or player should be able to fold when the call amount is $0
		
		
	
		Player player = round.players.get(round.getIndex());
		
	    if (choice == 1) //Call
	    {	    	
	    	    if ((player.getMoney() < round.getLastBet()) && (player !=  round.getLastBetter()))
	    	    {
	    	    	playerChoice(2);
	    	    }
	    	    else
	    	    {
		    		if((round.getLastBet() <= 0) || (player == round.getLastBetter()))
		    		{
		    			player.setLastAction("Checked");
		    		}
		    		else
		    		{
		    			player.setLastAction("Called $" + round.getLastBet());
		    			player.call(round.getLastBet());
		    			round.addToPot(round.getLastBet());
		    		}
		    		
		    		System.out.println(round.players.get(round.getIndex()).getName() + " called $" + round.getLastBet());
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
	    		}
	    }
	    else if (choice == 3) //Raise
	    {
	    		int raiseAmount = 0;
	    		
		    	if (player.isBot())
		    	{
		    		//TODO: calculate bot raise amount
		    		raiseAmount = (score.calculateScore(player.getHand().getCards())/2) + round.getLastBet();
		    	}
		    	else
		    	{
		    		//TODO: if playerBet is lower than lastBet or empty, throw error and dialog
		    		raiseAmount = Integer.parseInt(raiseInput.getText().replaceAll("[\\D]", "")) + round.getLastBet();
		    	}
		    	//Checking they have enough money for their choice
		    	if (raiseAmount > player.getMoney())
	    		{
	    			raiseAmount = player.getMoney();
	    		}
		    	round.raise(raiseAmount);
		    	round.addToPot(raiseAmount);
	    		System.out.println(round.players.get(round.getIndex()).getName() + " raised $" + raiseAmount);
	    		player.setLastAction("Raised " + raiseAmount);

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
                
                //round.players.get(round.getIndex()).toggleTurn(); //Toggle on user turn
                updateGUI();
                
                round.moveToNextPlayer();
                
                updateGUI();
                
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
package edu.miracosta.cs113;

import java.util.ArrayList;

public class Round
{
    private int pot;

    public ArrayList<Player> players;
    
    private ArrayList<Card> cardsInPlay = new ArrayList<Card>();
    
    private Player lastBetter;
            
    private int index;
    
    private int lastBet;
    
    private Table table;

    /**
     * Create a new round with constants from passed Table
     * 
     * @param table
     */
    public Round(Table table)
    {
	    	this.table = table;
	    	
	    	players = table.players;
	    	
        pot = 0;
        
        index = 0;
        
	    	players.get(table.getBigBlind()).setBigBlind(true);
	    	players.get(table.getSmallBlind()).setSmallBlind(true);
    }

    /**
     * Get index <- current player index
     */
    public int getIndex() 
    {
		return index;
    }
    
    /**
     * Set the last bet amount
     */
    public void setLastBet(int amount) 
    {
    	lastBet += amount;
    }
    
    
    /**
     * Get the last bet
     */
    public int getLastBet() 
    {
		return lastBet;
    }
    
    /**
     * Set the last better
     * 
     * @param player Player who raised last
     */
    public void setLastBetter(Player player) 
    {
    	lastBetter = player;
    }
    
    /**
     * Get the last better
     */
    public Player getLastBetter() 
    {
		return lastBetter;
    }
    
    /**
     * Add amount to the pot
     */
    public void addToPot(int amount) 
    {
    	pot += amount;
    }
    
    /**
     * Get the pot
     */
    public int getPot() 
    {
    	return pot;
    }
    
    /**
     * Get the table
     */
    public Table getTable()
    {
    	return table;
    }

    /**
     * Move to the next player
     * 
     * TODO examine infinite recursion case when all players have folded
     * 
     */
    public void moveToNextPlayer()
    {
    		System.out.println("Moving to next player... index: " + this.index);
		//Move to next player
		this.index++;
		
		//If new index is out of bounds, set to 0
		if (this.index > (players.size() - 1))
        {
			this.index = 0;
        }
    		
		System.out.println("Moved to next player... index: " + this.index);

    		//If this player folded, recursively call this method to move to the next player
        if (players.get(this.index).hasFolded())
        {
            moveToNextPlayer();
        }
    }
    
    /**
     * Move to the next player
     * 
     * TODO examine infinite recursion case when all players have folded
     * 
     * @return Player landed on
     */
    public Player findNextPlayer(int currentIndex)
    {
    		System.out.println("Finding next player... index: " + index);
		int i = currentIndex;
		i++;
		
		//If new index is out of bounds, set to 0
		if (i > (players.size() - 1))
        {
            i = 0;
        }
    		
    		//If this player folded, recursively call this method to move to the next player
        if (players.get(i).hasFolded())
        {
            return findNextPlayer(i);
        }
        else
        {
    			return players.get(i);
        }
    }
    
    /**
     * Call when player raises with new amount
     * 
     * @param amount New last bet value
     */
    public void raise(int amount) 
    {
    	pot += amount + lastBet;
		players.get(index).bet(amount + lastBet);
		setLastBet(amount + lastBet);
		setLastBetter(players.get(index));
    }
    
    /**
     * Gives each player 2 cards to start with
     */
    public void startRound()
    {
    		System.out.println("\nInside startRound()");
    	
        for (Player p : players)
        {
        	//Deal initial cards to player
        	p.addToHand(table.getDeck().deal());
        	p.addToHand(table.getDeck().deal());

            System.out.println("Gave " + p.getHand() + " to " + p.getName());
            
        }
        
        players.get(table.getBigBlind()).setBigBlind(true);
        players.get(table.getSmallBlind()).setSmallBlind(true);

        players.get(table.getBigBlind()).addBlind(table.BIG_BLIND);
        addToPot(table.BIG_BLIND);

        players.get(table.getSmallBlind()).addBlind(table.SMALL_BLIND);   
        addToPot(table.SMALL_BLIND);
        
        //Ante up
        setLastBet(table.BIG_BLIND);
        
		players.get(table.getBigBlind()).setLastAction("Big Blind: $" + Table.BIG_BLIND);
		players.get(table.getSmallBlind()).setLastAction("Small Blind: $" + Table.SMALL_BLIND);
		
		//Player that starts round is after big blind
        index = table.getBigBlind() + 1;
        if(index > players.size() - 1)
        {
        		index = 0;
        }

    }

    /**
     * Places initial 3 cards in the center of the table
     */
    public void flop()
    {
    	System.out.println("\nInside flop()");
    	
    	Card tableCard1 = table.getDeck().deal();
        cardsInPlay.add(tableCard1);
        addToAllHands(tableCard1);
        
        Card tableCard2 = table.getDeck().deal();
        cardsInPlay.add(tableCard2);
        addToAllHands(tableCard2);
        
        Card tableCard3 = table.getDeck().deal();
        cardsInPlay.add(tableCard3);
        addToAllHands(tableCard3);
        
        System.out.println("Cards on the table: " + cardsInPlay);
    }
    
    /**
     * Places fourth card on table
     */
    public void turn()
    {
    	System.out.println("\nInside turn()");
	    	
    	Card tableCard4 = table.getDeck().deal();
        cardsInPlay.add(tableCard4);
        addToAllHands(tableCard4);
        
        System.out.println("Cards on the table: " + cardsInPlay);
    }
    
    /**
     * Places fifth and final card on the table
     */
    public void river()
    {
    	System.out.println("\nInside river()");
    		
    	Card tableCard5 = table.getDeck().deal();
        cardsInPlay.add(tableCard5);
        addToAllHands(tableCard5);
        
        System.out.println("Cards on the table: " + cardsInPlay);
    }
    
    public void addToAllHands(Card card) 
    {
    	for(int i = 0; i < players.size(); i++) 
    	{
    		players.get(i).addToHand(card);
    	}
    }
    
    /**
     * Reset all players, clear the cards on the table, and resets index
     */
    public void resetRound()
    {
		resetAndIncrementBlinds();
	
    	lastBetter = players.get(table.getBigBlind() + 1);
    	lastBet = 0;
    			
        for (Player player : players)
        {
            player.reset();
        }
        
        //Reset cards in play
        cardsInPlay.clear();
        
        //Reset pot
        pot = 0;

        //Shuffle and reset deck
        table.getDeck().shuffle();
        
        //Player that starts round is after big blind
        index = table.getBigBlind();
        if(index > players.size() - 1)
        {
        		index = 0;
        }
    }
    
    /**
     * Reset raised and called after each stage
     */
	public void stageReset() 
	{
		for(Player player : players)
		{
			player.resetStatus();
		}
		
	    	//lastBetter = players.get(table.getBigBlind());
	    	lastBet = 0;
	    	
	    	//Player that starts betting
        index = table.getBigBlind() + 1;
        if(index > players.size() - 1)
        {
        		index = 0;
        }
        lastBetter = players.get(index);

	}
    
	/**
	 * Clear previous blind holders, increment blinds, then set true
	 * for new blind holders
	 */
	public void resetAndIncrementBlinds()
	{
    	players.get(table.getBigBlind()).setBigBlind(false);
    	players.get(table.getSmallBlind()).setSmallBlind(false);
    	table.incrementBlinds();
    	players.get(table.getBigBlind()).setBigBlind(true);
    	players.get(table.getSmallBlind()).setSmallBlind(true);
	}
	
    /**
     * Get cards on the board
     */
    public ArrayList<Card> getCardsInPlay() 
    {
		return cardsInPlay;
    }
}

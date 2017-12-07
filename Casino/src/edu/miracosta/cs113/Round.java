package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.Scanner;

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
        
        
        //TODO: add Blind logic, right now it is always 0
        index = table.getBigBlind();
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
    	lastBet = amount;
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
     * Move to the next player
     * 
     * TODO examine infinite recursion case when all players have folded
     */
    public void moveToNextPlayer()
    {
		//Move to next player
		index++;
		
		//If new index is out of bounds, reset to 0
		if (index > (players.size() - 1))
        {
            index = 0;
        }
    		
    	//If this player folded, recursively call this method again
        if (players.get(index).hasFolded())
        {
            moveToNextPlayer();
        }
    }
    
    /**
     * Call when player raises with new amount
     * 
     * @param amount New last bet value
     */
    public void raise(int amount) {
    	setLastBet(amount);
    	lastBetter = players.get(index);
    }
    
    /**
     * Gives each player 2 cards to start with
     */
    public void startRound()
    {
    	System.out.println("\nInside startRound()");
    	
        for (Player p : players)
        {
        	p.addToHand(table.getDeck().deal());
        	p.addToHand(table.getDeck().deal());

            System.out.println("Gave " + p.getHand() + " to " + p.getName());
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
        for (Player player : players)
        {
            player.resetStatus();
        }
        
        //Reset cards in play
        cardsInPlay.clear();
        

        //TODO: uncomment once flow works for first deck
        //table.getDeck().shuffle();
        
        //TODO big blind logic
        index = 0;
    }
    
    /**
     * Get cards on the board
     */
    public ArrayList<Card> getCardsInPlay() {
    	return cardsInPlay;
    }
}

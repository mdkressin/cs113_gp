package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.Scanner;

public class Round
{
    private int pot;

    public ArrayList<Player> players = new ArrayList<Player>();
    
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
        for (Player p : players)
        {
            Hand currentHand = p.getHand();
            currentHand.addCard(table.getDeck().deal());
            currentHand.addCard(table.getDeck().deal());
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
     * Places initial 3 cards in the center of the table
     */
    public void flop()
    {
        cardsInPlay.add(table.getDeck().deal());
        cardsInPlay.add(table.getDeck().deal());
        cardsInPlay.add(table.getDeck().deal());
    }
    /**
     * Places fourth card on table
     */
    public void turn()
    {
        cardsInPlay.add(table.getDeck().deal());
    }
    /**
     * Places fifth and final card on the table
     */
    public void river()
    {
        cardsInPlay.add(table.getDeck().deal());
    }
    
    /**
     * Get cards on the board
     */
    public ArrayList<Card> getCardsInPlay() {
    	return cardsInPlay;
    }
}

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

    //Constructors
    public Round()
    {
        table = new Table();
        pot = 0;
    }
    
    public Round(Table table)
    {
    		this.table = table;
        pot = 0;
        
        //TODO: add Blind logic, right now it is always 0
        index = table.getBigBlind();
    }
    
    public Round(Player userPlayer)
    {
    		table = new Table(userPlayer);
    		pot = 0;
    }
    
    public Round(Player userPlayer, int numBots)
    {
        table = new Table(userPlayer, numBots);
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
     * Reset all players, clear the cards on the table, and reset index
     */
    public void resetRound()
    {
        for (Player player : players)
        {
            player.resetStatus();
        }
        
        //Resetting list of active players and cards in play

        cardsInPlay.clear();

        //TODO big blind logic
        index = 0;
    }

    /**
     * Cycles through each player to see if they want to call, fold, or raise their bet
     * Keeps track of who the current player is and their index in players
    
    public void cyclePlayers()
    {
        boolean cyclingPlayers = true;
        while (cyclingPlayers)
        {
            System.out.println("Amount of active players: " + players.size());
            System.out.println("Current player's index: " + index);
            System.out.println("Current player: " + currentPlayer.getName());
            System.out.println("Last player in cycle: " + lastBetter.getName());

            if (currentPlayer == lastBetter)
            {
                System.out.println("Last player in cycle");
                cyclingPlayers = false;
                playerChoice(players.get(index));
            }
            else if (index == (players.size() - 1))
            {
                System.out.println("Last player in list");
                playerChoice(players.get(index));
                //index = 0;
            }
            else
            {
                System.out.println("No issues");
                playerChoice(players.get(index));
            }
            moveToNextPlayer();
        }
    }
     */

    /**
     * Has the player make a choice of calling, folding, raising, or checking
     * 
     * @param player The player that made the choice
     * @param choice The choice
     * 					1 -> Call/Check
     * 					2 -> Fold
     * 					3 -> Raise			
    public void playerChoice(Player player, int choice)
    {
        if (choice == 1)
        {
        		
        }
        else if (choice == 2)
        {
        		player.fold();
            players.remove(player);
        }
        else if (choice == 3)
        {
            //System.out.println("Enter amount to raise by:");
            int playerBet = ;
            player.bet(playerBet);
            lastBetter = player; //So it keeps looping until we reach the last player who raised
        }
    }
    */

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

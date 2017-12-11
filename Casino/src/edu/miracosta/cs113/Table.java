package edu.miracosta.cs113;

import java.util.ArrayList;

public class Table
{
	private static final String[] BOT_NAMES = {"Kyle", "Matt", "Eric"};

    private int bigBlind;
    private int smallBlind;
    
    CardDeck deck;
    
    private Player userPlayer;
    
    ArrayList<Player> players = new ArrayList<Player>();

    /**
     * Constructor creates a new Table with the human player,
     * bots, and initializes table variables
     * 
     * @param userPlayer Human Player
     * @param numBots Number of bots to create and add to table
     */
    public Table(Player userPlayer, int numBots)
    {
        this.userPlayer = userPlayer;
        deck = new CardDeck();
        deck.shuffle();
        initializePlayers(userPlayer, numBots);
        
        bigBlind = 0;
        smallBlind = players.size() - 1;
    }

    /**
     * Move to the next player
     * 
     * TODO examine infinite recursion case when all players have folded
     */
    public void incrementBlinds()
    {
    	bigBlind++;
    	smallBlind++;
		
		//If new index is out of bounds, set to 0
		if (this.bigBlind > (players.size() - 1))
        {
            this.bigBlind = 0;
        }
		
		//If new index is out of bounds, set to 0
		if (this.smallBlind > (players.size() - 1))
        {
            this.smallBlind = 0;
        }
    }
    
    /**
     * Add the human player and create bots for the table
     * 
     * @param userPlayer Human Player
     * @param numBots Number of bots to create and add to table
     */
    private void initializePlayers(Player userPlayer, int numBots)
    {
        players.add(userPlayer);
        
        for (int i = 0; i < numBots; i++)
        {
            players.add(new Player(BOT_NAMES[i], 500, true));
        }
    }

    //Getters
    public CardDeck getDeck()
    {
        return deck;
    }
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    public Player getUserPlayer()
    {
        return userPlayer;
    }
    public int getBigBlind()
    {
        return bigBlind;
    }
    public int getSmallBlind()
    {
        return smallBlind;
    }
}

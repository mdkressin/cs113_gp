package edu.miracosta.cs113;

import java.util.ArrayList;

public class Table
{
	private static final String[] BOT_NAMES = {"Kyle", "Matt", "Eric"};

    private final int DEFAULT_NUM_BOTS = 2;

    private int botNumber = 0;
    private int bigBlind;
    private int smallBlind;
    
    CardDeck deck = new CardDeck();
    
    private Player userPlayer;
    
    ArrayList<Player> players = new ArrayList<Player>();

    //Constructors
    public Table()
    {
        userPlayer = new Player();
        deck.shuffle();
        initializePlayers(userPlayer, DEFAULT_NUM_BOTS);
    }
    public Table(Player userPlayer)
    {
        this.userPlayer = userPlayer;
        deck.shuffle();
        initializePlayers(userPlayer, DEFAULT_NUM_BOTS);
    }
    public Table(Player userPlayer, int numBots)
    {
        this.userPlayer = userPlayer;
        deck.shuffle();
        initializePlayers(userPlayer, numBots);
    }

    //Helper Methods
    private void initializePlayers(Player userPlayer, int numBots)
    {
        players.add(userPlayer);
        for (int i = 0; i < numBots; i++)
        {
            players.add(new Player(BOT_NAMES[i], 500));
            botNumber++;
        }
    }
    
    public void newRound() {
    		Round round = new Round(this);
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

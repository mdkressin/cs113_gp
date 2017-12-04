package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.Scanner;

public class Round
{
    private int pot = 0;

    public ArrayList<Player> players = new ArrayList<Player>();
    
    private ArrayList<Card> cardsInPlay = new ArrayList<Card>();
    
    private Player lastPlayerInCycle;
    
    private Player currentPlayer;
    
    private int currentPlayerIndex;
    
    private Table table;

    //Constructors
    public Round()
    {
        table = new Table();
    }
    
    public Round(Table table)
    {
        this.table = table;
    }
    
    public Round(Player userPlayer)
    {
        table = new Table(userPlayer);
    }
    
    public Round(Player userPlayer, int numBots)
    {
        table = new Table(userPlayer, numBots);
    }

    public void playRound()
    {
        System.out.println("New round starting");
        resetRound();
        System.out.println();
        System.out.println("Initial deal");
        startRound();
        cyclePlayers();
        System.out.println();
        System.out.println("flop");
        flop();
        cyclePlayers();
        System.out.println();
        System.out.println("turn");
        turn();
        cyclePlayers();
        System.out.println();
        System.out.println("river");
        river();
        cyclePlayers();
        System.out.println("Round over");
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
     * Sets all players to "active" again, clears the cards in play, and resets the current/last players
     */
    private void resetRound()
    {
        players.clear();
        for (Player p : table.getPlayers())
        {
            players.add(p);
        }
        //Resetting list of active players and cards in play

        cardsInPlay.clear();
        //Resetting the current and last-in-cycle players
        currentPlayer = players.get(0);
        currentPlayerIndex = 0;
        lastPlayerInCycle = players.get(players.size() - 1);
    }

    /**
     * Cycles through each player to see if they want to call, fold, or raise their bet
     * Keeps track of who the current player is and their index in players
     */
    private void cyclePlayers()
    {
        boolean cyclingPlayers = true;
        while (cyclingPlayers)
        {
            System.out.println("Amount of active players: " + players.size());
            System.out.println("Current player's index: " + currentPlayerIndex);
            System.out.println("Current player: " + currentPlayer.getName());
            System.out.println("Last player in cycle: " + lastPlayerInCycle.getName());

            if (currentPlayer == lastPlayerInCycle)
            {
                System.out.println("Last player in cycle");
                cyclingPlayers = false;
                playerChoice(players.get(currentPlayerIndex));
            }
            else if (currentPlayerIndex == (players.size() - 1))
            {
                System.out.println("Last player in list");
                playerChoice(players.get(currentPlayerIndex));
                //currentPlayerIndex = 0;
            }
            else
            {
                System.out.println("No issues");
                playerChoice(players.get(currentPlayerIndex));
            }
            moveToNextPlayer();
        }
    }

    /**
     * Has the player make a choice of calling, folding, raising, or checking
     * @param p The player to get the choice from
     */
    private void playerChoice(Player p)
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("1. Call, 2. Fold, or 3. Raise?");
        int playerChoice = keyboard.nextInt();
        if (playerChoice == 1)
        {

        }
        else if (playerChoice == 2)
        {
            p.playerFolds();

            players.remove(p);
        }
        else if (playerChoice == 3)
        {
            System.out.println("Enter amount to raise by:");
            int playerBet = keyboard.nextInt();
            p.bet(playerBet);
            lastPlayerInCycle = p; //So it keeps looping until we reach the last player who raised
        }
    }
    /**
     * Moves on to the next player
     */
    private void moveToNextPlayer()
    {
        if (!currentPlayer.hasFolded()) //If they haven't been removed from active players the index moves up
        {
            currentPlayerIndex++;
        }
        if (currentPlayerIndex > (players.size() - 1))
        {
            System.out.println("RESET CURRENT PLAYER INDEX TO 0");
            currentPlayerIndex = 0;
        }
        System.out.println("CURRENT PLAYER INDEX: " + currentPlayerIndex);
        currentPlayer = players.get(currentPlayerIndex);
    }

    /**
     * Places initial 3 cards in the center of the table
     */
    private void flop()
    {
        cardsInPlay.add(table.getDeck().deal());
        cardsInPlay.add(table.getDeck().deal());
        cardsInPlay.add(table.getDeck().deal());
    }
    /**
     * Places fourth card on table
     */
    private void turn()
    {
        cardsInPlay.add(table.getDeck().deal());
    }
    /**
     * Places fifth and final card on the table
     */
    private void river()
    {
        cardsInPlay.add(table.getDeck().deal());
    }
    
    /**
     * Get cards on the board
     */
    public ArrayList<Card> getCardsInPlay() {
    		return cardsInPlay;
    }
    
    /**
     * Get cards on the board
     */
    public int getPot() {
    		return pot;
    }
}

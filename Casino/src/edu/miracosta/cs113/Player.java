package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.Random;

public class Player 
{
    private final int TOTAL_BOT_THRESHOLD = 75000; //Threshold for using totalScore
    private final int BOT_THRESHOLD  = 10;         //Threshold for calculateScore
    private final int CHANCE_TO_FOLD = 6;
    private final int CHANCE_TO_CALL = 6;
	//Constants
	private final String DEFAULT_NAME = "Default";
	private final int DEFAULT_MONEY = 500;

	//Player's name
	private String name;
	
	//Player's cards
	private Hand hand;
	
	//Whether or not this player is a bot
	private boolean isBot;
	
	//Player's money
	private int money;
	
	private String lastAction;

	private boolean hasFolded;
	private boolean hasCalled;
	private boolean hasRaised;
	
	//The amount this player has already bet during stage
	private int moneyBet;
	
	//True only when 
	private boolean isTurn;
	
	private boolean isBigBlind;
	private boolean isSmallBlind;
	
	public Player()
	{
		this.name = DEFAULT_NAME;
		this.money = DEFAULT_MONEY;
		this.isBot = false;
		this.hand = new Hand();
	}
	/**
	 * Constructs a new player with a name, initial money, and a new empty hand
	 *  
	 * @param name Player's name
	 * @param money Buy-in amount
	 */
	public Player(String name, int money, boolean isBot) {
		this.name = name;
		this.money = money;
		this.isBot = isBot;
		this.hand = new Hand();
	}

	/**
	 * Add a card to the player's hand
	 * 
	 * @param card Card to add
	 */
	public void addToHand(Card card) {
		hand.addCard(card);
	}
	
	/**
	 * Sets player's status to "folded"
	 */
	public void fold() {
		hasFolded = true;
	}

	/**
	 * Sets player's status to "called"
	 */
	public void call(int amount) {
		money -= amount;
		moneyBet += amount;
		hasCalled = true;
	}

	/**
     * Subtract from the player's money total
     * 
     * @param amount of money
     */
    public void bet(int amount) 
    {
		money -= amount;
		moneyBet += amount;
		hasRaised = true;
    }
    
    public void addBlind(int blind)
    {
    	money -= blind;
    	moneyBet += blind;
    }
    /**
     * Set the last action string
     * 
     * @param action
     */
    public void setLastAction(String action)
    {
    	lastAction = action;
    }
    
    /**
     * Get the last action string
     * 
     * @param action
     */
    public String getLastAction()
    {
    	return lastAction;
    }
    
	/**
	 * Reset player turn variables
	 */
	public void resetStatus() {
		hasCalled = false;
		hasRaised = false;
		moneyBet = 0;
	}
	
	/**
	 * Clear hand and reset variable
	 */
	public void reset() 
	{
		hasCalled = false;
		hasRaised = false;
		hasFolded = false;
		hand.removeAllCards();
		moneyBet = 0;
	}

	public Hand getHand()
	{
		return hand;
	}
    /**
     * Return the player's name.
     * 
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the player's current amount of money
     * 
     * @return The amount of money.
     */
    public int getMoney() {
        return money;
    }

	/**
	 * Returns whether the player has folded.
	 *
	 * @return "Folded" status
	 */
	public boolean hasFolded() {
		return hasFolded;
	}
	/**
	 * Returns whether the player has folded.
	 *
	 * @return "Called" status
	 */
	public boolean hasCalled() 
	{
		return hasCalled;
	}
	/**
	 * Return whether or not the player has raised this round
	 *
	 * @return "Raised" status
	 */
	public boolean hasRaised() 
	{
		return hasRaised;
	}
    
    
    /**
     * Add to the player's money total
     * 
     * @param amount of money
     */
    public void won(int amount) 
    {
    		money = money + amount;
    }
    
	public boolean isBot() 
	{
		return isBot;
	}
	
	public void toggleTurn() 
	{
		isTurn = !isTurn;
	}
	public boolean isTurn() 
	{
		return isTurn;
	}
	public int getMoneyBet() {
		return moneyBet;
	}
	public boolean isBigBlind() {
		return isBigBlind;
	}
	public void setBigBlind(boolean isBigBlind) {
		this.isBigBlind = isBigBlind;
	}
	public boolean isSmallBlind() {
		return isSmallBlind;
	}
	public void setSmallBlind(boolean isSmallBlind) {
		this.isSmallBlind = isSmallBlind;
	}
	
	public int makeDecision(Round round)
	{
		HandScore score = new HandScore();
		int botScore = 0;
		Random randNum  = new Random();
		//Random value between 1 and 10 to make bot less predictable
		int botRandomness = randNum.nextInt(10) + 1;
		
		//TODO: Tweak decision making randomness
		if (round.getCardsInPlay().size() < 3)		
		{
			botScore = score.calculateScore(hand.getCards());
			
			if ((botScore < BOT_THRESHOLD/2) || ((round.getLastBet() >= botScore) && (botRandomness <= CHANCE_TO_FOLD)) || (money <= 0)) //Fold
			{
				System.out.println("\n" + name + " folded");
				return 2;
			}
			else if ((botScore < BOT_THRESHOLD) || (botRandomness <= CHANCE_TO_CALL) || (round.getLastBet() >= money)) //Call
			{
				System.out.println("\n" + name + " called");
				return 3;
			}
			else //Raise
			{
				System.out.println("\n" + name + " raised");
				return 3;
			}
		}
		else
		{
			ArrayList<Card> inPlay = round.getCardsInPlay();
			Card[] cards = new Card[inPlay.size() + 2];
			int i = 0;
			for (Card c : inPlay)
			{
				cards[i] = c;
				i++;
			}
			cards[i++] = hand.getHoleCards()[0];
			cards[i++] = hand.getHoleCards()[1];
			botScore = (int) score.totalScore(cards);
			
			if ((botScore < TOTAL_BOT_THRESHOLD/2) || ((round.getLastBet() >= botScore/1000) && (botRandomness <= CHANCE_TO_FOLD)) || (money <= 0)) //Fold
			{
				System.out.println("\n" + name + " folded");
				return 2;
			}
			else if ((botScore < BOT_THRESHOLD) || (botRandomness <= CHANCE_TO_CALL) || (round.getLastBet() >= money)) //Call
			{
				System.out.println("\n" + name + " called");
				return 1;
			}
			else //Raise
			{
				System.out.println("\n" + name + " raised");
				return 3;
			}
		}
	}
}

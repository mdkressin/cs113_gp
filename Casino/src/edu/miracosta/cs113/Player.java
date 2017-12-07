package edu.miracosta.cs113;


public class Player 
{
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

	private boolean hasFolded;
	private boolean hasCalled;
	private boolean hasChecked;
	
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
		money = money - amount;
		hasCalled = true;
	}

	/**
     * Subtract from the player's money total
     * 
     * @param amount of money
     */
    public void bet(int amount) {
    		money = money - amount;
    }

	/**
	 * Resets fold, call, and check status of player to false
	 */
	public void resetStatus() {
		hand.removeAllCards();
		hasFolded = false;
		hasCalled = false;
		hasChecked = false;
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
	public boolean hasCalled() {
		return hasCalled;
	}
	/**
	 * Returns whether the player has folded.
	 *
	 * @return "Checked" status
	 */
	public boolean hasChecked() {
		return hasChecked;
	}
    
    
    /**
     * Add to the player's money total
     * 
     * @param amount of money
     */
    public void won(int amount) {
    		money = money + amount;
    }
    
	public boolean isBot() {
		return isBot;
	}
}

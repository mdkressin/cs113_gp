package edu.miracosta.cs113;

public class Player {

	//Player's name
	private String name;
	
	//Player's cards
	private Hand hand;
	
	//Player's money
	private int money;
	
	
	/**
	 * Constructs a new player with a name, intitial money, and a new empty hand
	 *  
	 * @param name Player's name
	 * @param money Buy-in amount
	 */
	public Player(String name, int money) {
		this.name = name;
		this.money = money;
		this.hand = new Hand();
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
     * Subtract from the player's money total
     * 
     * @param amount of money
     */
    public void bet(int amount) {
    	money = money - amount;
    }
    
    /**
     * Add to the player's money total
     * 
     * @param amount of money
     */
    public void won(int amount) {
    	money = money + amount;
    }
}

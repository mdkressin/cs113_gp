package edu.miracosta.cs113;

public class Card implements Comparable<Card> {
   
	
	//String representation of the value's value int value
    public static final String[] CARDS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
    //String representation of the value's suit int value
    public static final String[] SUITS = { "diamonds", "clubs", "hearts", "spades" };

    //Value of the value
    private final int value;
    //Suit of the value
    private final int suit;
    
    /**
     * Construct a Card based on value and suit.
     * 
     * @param value
     *            The value for the corresponding value
     *                Ace      = 12;
     *                King     = 11;
     *                Queen    = 10;
     *                Jack     = 9;
     *                Ten      = 8;
     *                Nine     = 7;
     *                Eight    = 6;
     *                Seven    = 5;
     *                Six      = 4;
     *                Five     = 3;
     *                Four     = 2;
     *                Three    = 1;
     *                Duece    = 0;
     * @param suit
     *                Spades   = 3;
     *                Hearts   = 2;
     *                Clubs    = 1;
     *                Diamonds = 0;
     * 
     * @throws IllegalArgumentException
     *             If the rank or suit is invalid.
     */
    public Card(int value, int suit) {
        if (value < 0 || value > 12) {
            throw new IllegalArgumentException("Invalid rank");
        }
        if (suit < 0 || suit > 3) {
            throw new IllegalArgumentException("Invalid suit");
        }
        this.value = value;
        this.suit = suit;
    }
    
    /**
     * Returns the suit.
     * 
     * @return The suit.
     */
    public int getSuit() {
        return suit;
    }
    
    /**
     * Returns the value.
     * 
     * @return The value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Check if two values are the same.
     * 
     * @param obj Other value
     * 
     * @return boolean 
     * 				True if this object and passed value are equal
     * 				False if not equal
     * 
     * @throws IllegalArgumentException
     *             If the rank or suit is invalid.
     */
    @Override
    public boolean equals(Object obj) {
    	
    	if(obj instanceof Card) { 
    		Card other = (Card) obj;
    		return this.value == other.value && this.suit == other.suit;
    	} else {
    		System.out.println("Invalid parameter");
    		return false;
    	}
    }

    /**
     * Compare two values
     * 
     * @return int
     * 			Less than 1 if this value is weaker than passed value
     * 			Greater than 1 if this value is stronger than the passed value
     * 			Zero if the two values have equal values
     * 
     * @param Card
     * 			Card to compare this carad to
     */
    @Override
    public int compareTo(Card other) {
    	return this.value - other.value;
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString() {
        return CARDS[value] + " of " + SUITS[suit];
    }
    
    /**
     * Get formatted file path for the corresponding image asset for this card
     * 
     * @return String
     */
    public String getFilePath() {
    	return CARDS[value] + "_of_" + SUITS[suit] + ".png";
    }
    
}
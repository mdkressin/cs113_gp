package edu.miracosta.cs113;

public class Hand {
    
    //Array of cards in the hand
    private Card[] cards = new Card[7];
    
    /** The current number of cards in this hand. */
    private int numCards = 0;
    
    /**
     * Empty constructor
     */
    public Hand() {
        // Empty implementation.
    }
    
    /**
     * Constructor with an array of initial cards.
     * 
     * @param cards
     *            The initial cards.
     * 
     * @throws IllegalArgumentException
     *             If the array is null or the number of cards is invalid.
     */
    public Hand(Card[] cards) {
        addCards(cards);
    }
    
    
    /**
     * Returns the number of cards.
     * 
     * @return The number of cards.
     */
    public int size() {
        return numCards;
    }
    
    /**
     * Adds a single card.
     * 
     * The card is inserted at such a position that the hand remains sorted
     * (highest value cards first).
     * 
     * @param card
     *            The card to add.
     * 
     * @throws IllegalArgumentException
     *             If the card is null.
     */
    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Null card");
        }
        
        int index = -1;
        for (int i = 0; i < numCards; i++) {
            if (card.compareTo(cards[i]) > 0) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            cards[numCards] = card;
            numCards++;
        } else {
            cards[index] = card;
            numCards++;
        }
    }
    
    /**
     * Add multiple cards to the hand.
     * 
     * The cards are inserted at such a position that the hand remains sorted
     * (highest ranking cards first).
     * 
     * @param cards
     *            The cards to add.
     */
    public void addCards(Card[] cards) {
        if (cards == null) {
            throw new IllegalArgumentException("No cards in the array");
        }
        if (cards.length > 7) {
            throw new IllegalArgumentException("Too many cards");
        }
        for (Card card : cards) {
            addCard(card);
        }
    }
    
    
    /**
     * Returns the cards.
     *
     * @return The cards.
     */
    public Card[] getCards() {
        return cards;
    }
    
    /**
     * Removes all cards.
     */
    public void removeAllCards() {
        numCards = 0;
    }
    
    /**
     * String representation of hand
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numCards; i++) {
            sb.append(cards[i]);
            if (i < (numCards - 1)) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
    
}

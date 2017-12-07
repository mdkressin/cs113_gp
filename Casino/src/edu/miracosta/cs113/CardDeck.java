package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class CardDeck
{
    private static final int DECK_SIZE = 52;
    
    /** The cards in the deck. */
    private Card[] cards;
    
    /** The index of the next card to deal. */
    private int cardIndex;

    /**
     * Constructor populates the cards array with a full, ordered deck
     */
    public CardDeck() 
    {
        cards = new Card[DECK_SIZE];
        int cardIndex = 0;
        for (int suit = 3; suit >= 0; suit--) 
        {
            for (int value = 12; value >= 0 ; value--) 
            {
                cards[cardIndex] = new Card(value, suit);
                cardIndex++;
            }
        }
    }
    
    /**
     * Shuffles the deck.
     */
    public void shuffle() 
    {
    	Random random = new Random();
    	
        for (int oldIndex = 0; oldIndex < DECK_SIZE; oldIndex++) 
        {
            int newIndex = random.nextInt(DECK_SIZE);
            Card tempCard = cards[oldIndex];
            cards[oldIndex] = cards[newIndex];
            cards[newIndex] = tempCard;
        }
        cardIndex = 0;
    }
    
    /**
     * Resets the deck without shuffling
     */
    public void reset() {
        cardIndex = 0;
    }
    
    /**
     * Deals a single card.
     *
     * @return  the card dealt
     */
    public Card deal() 
    {
        if (cardIndex + 1 >= DECK_SIZE) 
        {
            throw new IllegalStateException("No cards left in deck");
        }
        return cards[cardIndex++];
    }
    
    /**
     * Deals multiple cards at once.
     * 
     * @param noOfCards
     *            The number of cards to deal.
     * 
     * @return A list of Cards with the passed size, taken off the top of the deck
     * 
     * @throws IllegalArgumentException
     *             If the number of cards is invalid.
     * @throws IllegalStateException
     *             If there are no cards left in the deck.
     */
    public List<Card> deal(int numCards) {
        if (numCards < 1) {
            throw new IllegalArgumentException("Use deal() or ask for more than 1 card");
        }
        if (cardIndex + numCards >= DECK_SIZE) {
            throw new IllegalStateException("No cards left in deck");
        }
        List<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < numCards; i++) {
            dealtCards.add(cards[cardIndex]);
            cardIndex++;
        }
        return dealtCards;
    }
    
    
    /**
     * Return a string of all cards in the deck
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card);
            sb.append(' ');
        }
        return sb.toString().trim();
    }
}
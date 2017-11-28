package edu.miracosta.cs113;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class CardDeck
{
    private final int DECK_SIZE = 52;

    private String[]        cardFace  = {"Heart", "Diamond", "Club", "Spade"};       //Possible faces
    private int[]           cardValue = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}; //11-Ace 12-Queen 13-King (No joker)
    private int             numCards  = 0; //Keeps track of how many cards are in the deck (NOT "in play" or "out of play")
    private ArrayList<Card> cardsInPlay    = new ArrayList<>(); //Stores cards in play so we can add them back to the deck (or out of play)
    private ArrayList<Card> cardsOutOfPlay = new ArrayList<>(); //Stores cards out of play after a round, to be re-added to deck later
    private Stack<Card>     deck           = new Stack<Card>();

    //Constructor
    public CardDeck()
    {
        buildDeck();
        shuffleDeck();
    }

    /**
     * Builds the deck with one of each possible combination of card faces and values
     * Adds these cards to the stack and keeps track of the number added
     */
    private void buildDeck()
    {
        int faceCount = 0;
        int valueCount = 0;
        //Cycles through until reaching the full deck size
        for (int i = 0; i < DECK_SIZE; i++)
        {
            //Once every possible value has been added for a face, this resets the value and moves on to the next face
            if (valueCount > (cardValue.length - 1))
            {
                valueCount = 0;
                faceCount++;
                //Safeguard if the deck size is changed (which it shouldn't be) and it adds more than
                //(faceCount * faceValue) cards
                if (faceCount > (cardFace.length - 1))
                {
                    faceCount = 0;
                }
            }
            Card newCard = new Card(cardFace[faceCount], cardValue[valueCount]);
            deck.push(newCard);
            numCards++;
            valueCount++;
        }
    }
    /**
     * Gives the top Card from the deck stack, stores it as "in play"
     */
    public Card dealHand()
    {
        Card tempCard = deck.pop();
        cardsInPlay.add(tempCard);
        numCards--;
        return tempCard;
    }

    /**
     * Places a card out of play so it cannot be drawn by the dealer, until the deck is rebuilt
     * @param c The card to be put out of play
     * @return  True if the card was in play and the add to "out of play" was successful (card MUST have been dealt and
     *          therefore "in play" to be put "out of play", otherwise the card wasn't in the deck)
     */
    public boolean putOutOfPlay(Card c)
    {
        if (cardsInPlay.contains(c))
        {
            cardsInPlay.remove(c);
            cardsOutOfPlay.add(c);
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * Places cards in play, out of play, and in the deck into a LinkedList (since it resizes itself whenever we remove
     * a card), then adds back into the deck in a random order. (picks a value from 0 to the LinkedList size)
     * randomized order.
     */
    public void shuffleDeck()
    {
        Random r = new Random();
        LinkedList<Card> cardList = new LinkedList<>();

        while (!deck.empty())
        {
            cardList.add(deck.pop());
            numCards--;
        }
        for (Card c : cardsInPlay)
        {
            cardList.add(c);
        }
        cardsInPlay.clear();
        for (Card c : cardsOutOfPlay)
        {
            cardList.add(c);
        }
        cardsOutOfPlay.clear();
        while (!cardList.isEmpty())
        {
            int randomValue = r.nextInt(cardList.size());
            deck.push(cardList.get(randomValue));
            numCards++;
            cardList.remove(randomValue);
        }
        listCards();
    }
    /**
     * Removes and lists the cards in the deck, then re-adds them (mostly for testing purposes)
     */
    public void listCards()
    {
        ArrayList<Card> tempCardHolder = new ArrayList<>();

        //Pops each card off the stack, then stores it in the temporary ArrayList
        for (int i = 0; i < (numCards); i++)
        {
            Card newCard = deck.pop();
            tempCardHolder.add(newCard);
        }
        //Adds the cards back into the deck in the same order as before
        for (int i = (tempCardHolder.size() - 1); i >= 0; i--)
        {
            deck.push(tempCardHolder.get(i));
        }
        tempCardHolder.clear();
    }
    //Setters
    public void setDeck(Stack<Card> deck)
    {
        this.deck = deck;
    }
    //Getters
    public Stack<Card> getDeck()
    {
        return deck;
    }
    public int getNumCardsInDeck()
    {
        return numCards;
    }
}

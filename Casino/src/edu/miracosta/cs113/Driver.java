package edu.miracosta.cs113;

import java.util.Stack;

public class Driver
{
    public static void main(String[] args)
    {
    	//Some simple  tests of the deck
        CardDeck mydeck = new CardDeck();
        mydeck.listCards();
        mydeck.dealHand();
        mydeck.dealHand();
        
        Card newCard = (mydeck.dealHand());
        mydeck.putOutOfPlay(newCard);
        mydeck.shuffleDeck();
        Stack<Card> tempDeck = mydeck.getDeck();
        int counter = 0;
        for (Card c: tempDeck)
        {
            System.out.println(c.getValue() + " of " + c.getFace() + "s");
            counter++;
        }
        System.out.println(counter); //Should bee 52, deck size
    }
}

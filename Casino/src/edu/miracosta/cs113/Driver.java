package edu.miracosta.cs113;


public class Driver
{
    public static void main(String[] args)
    {
    	//Some simple  tests of the deck
        CardDeck deck = new CardDeck();
        
        System.out.println("All cards:");
        System.out.println(deck);
        
        System.out.println("Shuffled deck:");
        deck.shuffle();
        System.out.println(deck);
        
        System.out.println("Deal hand:");
        deck.shuffle();
        System.out.println(deck.deal(2));
        
        System.out.println("Deal flop:");
        deck.shuffle();
        System.out.println(deck.deal(3));
        
        //Testing the HandScore's sorting method
        System.out.println();
        System.out.println("Sorting cards");
        CardDeck newdeck = new CardDeck();
        HandScore score = new HandScore();
        //Hand unsortedHand = new Hand();
        Card[] unsortedCards = new Card[7];
        for (int i = 0; i < unsortedCards.length; i++)
        {
            unsortedCards[i] = deck.deal();
        }

        score.calculateScore(unsortedCards);

        Round round = new Round();
        round.playRound();
    }
}

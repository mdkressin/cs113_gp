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
    }
}

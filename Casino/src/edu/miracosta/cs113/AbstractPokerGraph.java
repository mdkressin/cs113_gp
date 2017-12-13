package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Abstract base class for poker graphs.
 * @param <E> The element type of the vertices in the graph
 */
public abstract class AbstractPokerGraph<E> implements Graph<E> {
	// Data Fields
	/** The number of vertices. */
	private int numV;
	/** Flag to indicate whether this is a directed graph. */
	private boolean isDirected;
	/** The id of the next vertex. */
	private static int vertexId = 0;
	
	// Constructor
	/**
	 * Construct a graph with the specified number of vertices and the directed
	 * flag. If the directed flag is true, this is a directed graph.
	 * @param numV The number of vertices
	 * @param directed The directed flag
	 */
	public AbstractPokerGraph(int numV, boolean directed)
	{
		this.numV = numV;
		this.isDirected = directed;
	}
	
	// Accessor methods
	/**
	 * Return the number of vertices.
	 * @return The number of vertices
	 */
	@Override
	public int getNumV() 
	{
		return numV;
	}
	
	/**
	 * Return whether this is a directed graph.
	 * @return true if this is a directed graph
	 */
	@Override
	public boolean isDirected() 
	{
		return isDirected;
	}
	
	// Other Methods
	
	/**
	 * Generate a graph of all possible hands based off the passed in hand
	 * @param currentHand	The hand that hand possibilities will be based on
	 * @param graph	The graph to build
	 * @return	a graph containing possible hand combinations
	 */
	public static <E> Graph<E> possibleHandsGraph(Graph<E> graph, E currentHand) 
	{
		// base case - graph is full
		if (vertexId >= graph.getNumV())
		{
			return graph;
		}
		// base case - reached largest hand size (7)
		if (((Hand) currentHand).size() >= Hand.MAX_HAND_SIZE)
		{
			return graph;
		}
		
		/* The source vertex. */
		Vertex<E> source = new Vertex<E>(currentHand, vertexId++);
		/* The destination vertex. */
		Vertex<E> dest;
		/* The edge to insert into the graph. */
		Edge<E> edge;
		/* Create a deck to calculate hand possibilities .*/
		CardDeck deck = new CardDeck();
		Card[] handCards = ((Hand) currentHand).getCards();
		/* The current number of cards in the hand. */
		int handSize = ((Hand) currentHand).size();
		/* Holds a reference to a card drawn from the deck. */
		Card card;
		
		try 
		{
			while (true)
			{ // will exit once the deck runs out of cards by throwing an exception
				E possibleHand;// new hand combination
				card = deck.deal(); // draw a card from the deck
				boolean newCard = true; // flag for determining if card from deck is already in the hand
				int i = 0;
				while (newCard && i < handSize)
				{ // make sure card is not already in the hand
					if (card.equals(handCards[i]))
					{
						newCard = false;
					}
					i++;
				}
				if (newCard)
				{
					// create card array to hold the new hand
					Card[] cards = new Card[handSize+1];
					int j = 0;
					for (Card c : handCards)
					{ // add the cards from the previous hand into the new possible hand
						cards[j] = c;
						j++;
					}
					cards[j] = card; // add new (possible) card
					possibleHand = (E) new Hand(cards);
					// recursive call to calculate possible hands
					possibleHandsGraph(graph, possibleHand);
					// check if graph is full
					if (vertexId >= graph.getNumV())
					{
						return graph; // cannot continue inserting into graph, return
					}
					dest = new Vertex<E>(possibleHand, vertexId++);
					edge = new Edge<E>(source,dest);
					// insert the edge into the graph
					graph.insert((Edge<E>) edge);
				}
			}
		} catch (IllegalStateException e) 
		{ // gone through all possibilities from deck
			return graph;
		}
	}
}

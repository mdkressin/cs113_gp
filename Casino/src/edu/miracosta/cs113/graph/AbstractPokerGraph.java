package edu.miracosta.cs113.graph;

import edu.miracosta.cs113.Card;
import edu.miracosta.cs113.CardDeck;
import edu.miracosta.cs113.Hand;

/**
 * Abstract base class for poker graphs.
 */
public abstract class AbstractPokerGraph implements PokerGraph{

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
	 * Load the edges of a graph from the data in an input file. The file
	 * should contain a series of lines, each line with two or three data values.
	 * The first is the source, the second is the destination, and the optional
	 * third is the weight
	 * @param scan The Scanner connected to the data file
	 */
/*	public void loadEdgesFromFile(Scanner scan) 
	{
		StringTokenizer tokens;
		String line,source,dest,weight;
		while (scan.hasNextLine())
		{
			line = scan.nextLine();
			tokens = new StringTokenizer(line);
			if (tokens.hasMoreTokens())
			{
				source = tokens.nextToken();
				if (tokens.hasMoreTokens())
				{
					dest = tokens.nextToken();
					if (tokens.hasMoreTokens())
					{ // line contains optional weight
						weight = tokens.nextToken();
						this.insert(new Edge(Integer.parseInt(source),
											Integer.parseInt(dest), 
											Double.parseDouble(weight)));
					}
					else 
					{ // line does not contain optional weight
						this.insert(new Edge(Integer.parseInt(source),
								Integer.parseInt(dest)));
					}
				}
			}
		}
	}*/
	
	/**
	 * Factory method to create a graph and load the data from an input file. The
	 * first line of the input file should contain the number of vertices. The 
	 * remaining lines should contain the edge data as described under
	 * loadEdgesFromFile.
	 * @param scan The Scanner connected to the data file
	 * @param isDirected true if this is a directed graph, false otherwise
	 * @param type	The string "Matrix" if an adjacency matrix is to be created,
	 * 				and the string "List" if an adjacency list is to be created
	 * @throws IllegalArgumentException if type is neither "Matrix" nor "List"
	 * @return	the graph created from the data file
	 */
/*	public static Graph createGraph(Scanner scan, boolean isDirected, String type)
	{
		int numV = scan.nextInt();
		AbstractPokerGraph returnValue = null;
		if (type.equalsIgnoreCase("Matrix")) 
		{
			returnValue = new MatrixPokerGraph(numV, isDirected);
		}
		else if (type.equalsIgnoreCase("List"))
		{
			returnValue = new ListPokerGraph(numV, isDirected);
		}
		else 
		{
			throw new IllegalArgumentException();
		}
		returnValue.loadEdgesFromFile(scan);
		return returnValue;
	}*/
	
	/**
	 * Generate a graph of all possible hands based off the passed in hand
	 * @param currentHand	The hand that hand possibilities will be based on
	 * @param graph	The graph to build
	 * @return	a graph containing possible hand combinations
	 */
	public static PokerGraph createGraph(PokerGraph graph, Hand currentHand) 
	{
		/* The source vertex. */
		HandVertex source = new HandVertex(currentHand, vertexId++);
		/* The destination vertex. */
		HandVertex dest;
		/* The edge to insert into the graph. */
		PokerEdge edge;
		/* Create a deck to calculate hand possibilities .*/
		CardDeck deck = new CardDeck();
		Card[] handCards = currentHand.getCards();
		/* The current number of cards in the hand. */
		int handSize = currentHand.size();
		/* Holds a reference to a card drawn from the deck. */
		Card card;
		try 
		{
			while (true)
			{ // will exit once the deck runs out of cards by throwing an exception
				Hand possibleHand = new Hand();// new hand combination
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
					for (Card c : handCards)
					{ // add the cards from the previous hand into the new possible hand
						possibleHand.addCard(c);
					}
					// add the new card to the next possible hand
					possibleHand.addCard(card);
					createGraph(graph, possibleHand); // recursive call to calculate possible hands
					dest = new HandVertex(possibleHand, vertexId++);
					edge = new PokerEdge(source,dest);
					graph.insert(edge);
				}
			}
		} catch (IllegalStateException e) 
		{ // gone through all possibilities from deck
			return graph;
		}
	}
}


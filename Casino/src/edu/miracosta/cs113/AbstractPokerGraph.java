package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Abstract base class for poker graphs.
 */
public abstract class AbstractPokerGraph implements Graph {
	// Data Fields
	/** The number of vertices. */
	private int numV;
	/** Flag to indicate whether this is a directed graph. */
	private boolean isDirected;
	
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
	public void loadEdgesFromFile(Scanner scan) 
	{
		//stub
	}
	
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
	public static Graph createGraph(Scanner scan, boolean isDirected, String type)
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
	}
}

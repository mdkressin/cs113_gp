package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

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
	
	// inner class to implement depth-first search algorithm
	public class DepthFirstSearch
	{
		// Data Fields
		/** A reference to the graph being searched. */
		Graph graph;
		/** Array of parents in the depth-first search. */
		private int[] parent;
		/** Flag to indicate whether this vertex has been visited. */
		private boolean[] visited;
		/** The array that contains each vertex in discover order. */
		private int[] discoveryOrder;
		/** The array that contains each vertex in finish order. */
		private int[] finishOrder;
		/** The index that indicates the discovery order. */
		private int discoverIndex = 0;
		/** The index that indicates the finish order. */
		private int finishIndex = 0;
		
		/**
		 * Construct the depth-first search of a Graph starting at vertex 0 and
		 * visiting the start vertices in ascending order.
		 * @param graph	The graph
		 */
		public DepthFirstSearch(Graph graph) 
		{
			this.graph = graph;
			int n = graph.getNumV();
			parent = new int[n];
			visited = new boolean[n];
			discoveryOrder = new int[n];
			finishOrder = new int[n];
			for (int i = 0; i < n; i++)
			{
				parent[i] = -1;
			}
			for (int i = 0; i < n; i++)
			{
				if (!visited[i])
				{
					depthFirstSearch(i);
				}
			}
		}
		/**
		 * Construct the depth-first search of a Graph selecting the start vertices
		 * in the specified order. The first vertex visited is order[0]
		 * @param graph	The graph
		 * @param order	The array giving the order in which the start vertices
		 * 				should be selected
		 */
		public DepthFirstSearch(Graph graph, int[] order)
		{
			this.graph = graph;
			int n = graph.getNumV();
			parent = new int[n];
			visited = new boolean[n];
			discoveryOrder = new int[n];
			finishOrder = new int[n];
			for (int i = 0; i < n; i++)
			{
				parent[i] = -1;
			}
			for (int i = 0; i < n; i++)
			{
				if (!visited[order[i]])
				{
					depthFirstSearch(order[i]);
				}
			}
		}
		
		/**
		 * Recursively depth-first search the graph starting at vertex current.
		 * @param current	The start vertex
		 */
		public void depthFirstSearch(int current)
		{
			/* Mark the current vertex visited. */
			visited[current] = true;
			discoveryOrder[discoverIndex++] = current;
			/* Examine each vertex adjacent to the current index. */
			Iterator<Edge> itr = graph.edgeIterator(current);
			while (itr.hasNext())
			{
				int neighbor = itr.next().getDest();
				/* Process a neighbor that has not been visited */
				if (!visited[neighbor])
				{
					/* Insert (current, neighbor) into the depth-first 
					 * search tree. */
					parent[neighbor] = current;
					/* Recursively apply the algorithm starting at neighbor. */
					depthFirstSearch(neighbor);
				}
			}
			/* Mark current finished. */ 
			finishOrder[finishIndex++] = current;
		}
	} // end of DepthFirstSearch inner class
}

package edu.miracosta.cs113.graph;

import java.util.Iterator;
import edu.miracosta.cs113.Hand;

/**
 * Class to implement the depth-first search algorithm
 * 
 */
public class dfsPoker
{
	// Data Fields
	/** A reference to the graph being searched. */
	private PokerGraph graph;
	/** Array of parents in the depth-first search. */
	private HandVertex[] parent;
	/** Flag to indicate whether this vertex has been visited. */
	private boolean[] visited;
	/** The array that contains each vertex in discover order. */
	private HandVertex[] discoveryOrder;
	/** The array that contains each vertex in finish order. */
	private HandVertex[] finishOrder;
	/** The index that indicates the discovery order. */
	private int discoverIndex = 0;
	/** The index that indicates the finish order. */
	private int finishIndex = 0;
	
	/**
	 * Construct the depth-first search of a Graph starting at vertex 0 and
	 * visiting the start vertices in ascending order.
	 * @param graph	The graph
	 */
	public dfsPoker(PokerGraph graph) 
	{
		this.graph = graph;
		int n = graph.getNumV();
		parent = new HandVertex[n];
		visited = new boolean[n];
		discoveryOrder = new HandVertex[n];
		finishOrder = new HandVertex[n];
		for (int i = 0; i < n; i++)
		{
			//parent[i] = -1;
			parent[i] = null;
		}
		for (int i = 0; i < n; i++)
		{
			if (!visited[i])
			{
				depthFirstSearch(parent[i]);
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
	public dfsPoker(PokerGraph graph, HandVertex[] order)
	{
		this.graph = graph;
		int n = graph.getNumV();
		parent = new HandVertex[n];
		visited = new boolean[n];
		discoveryOrder = new HandVertex[n];
		finishOrder = new HandVertex[n];
		for (int i = 0; i < n; i++)
		{
			//parent[i] = -1;
			parent[i] = null;
		}
		for (int i = 0; i < n; i++)
		{
			if (!visited[order[i].getId()])
			{
				depthFirstSearch(order[i]);
			}
		}
	}
	
	/**
	 * Recursively depth-first search the graph starting at vertex current.
	 * @param current	The start vertex
	 */
	public void depthFirstSearch(HandVertex current)
	{
		/* Mark the current vertex visited. */
		visited[current.getId()] = true;
		discoveryOrder[discoverIndex++] = current;
		/* Examine each vertex adjacent to the current index. */
		Iterator<PokerEdge> itr = graph.edgeIterator(current.getId());
		while (itr.hasNext())
		{
			HandVertex neighbor = (HandVertex) itr.next().getDest();
			/* Process a neighbor that has not been visited */
			if (!visited[neighbor.getId()])
			{
				/* Insert (current, neighbor) into the depth-first 
				 * search tree. */
				parent[neighbor.getId()] = current;
				/* Recursively apply the algorithm starting at neighbor. */
				depthFirstSearch(neighbor);
			}
		}
		/* Mark current finished. */ 
		finishOrder[finishIndex++] = current;
	}
	
	// Accessors
	public HandVertex[] getDiscoveryOrder()
	{
		return discoveryOrder;
	}
	public HandVertex[] getFinishOrder()
	{
		return finishOrder;
	}
}

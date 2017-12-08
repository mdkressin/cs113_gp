package edu.miracosta.cs113;

import java.util.Iterator;

/**
 * Class to implement the depth-first search algorithm
 */
public class DepthFirstSearch<E extends Vertex>
{
	// Data Fields
	/** A reference to the graph being searched. */
	Graph graph;
	/** Array of parents in the depth-first search. */
	private E[] parent;
	/** Flag to indicate whether this vertex has been visited. */
	private boolean[] visited;
	/** The array that contains each vertex in discover order. */
	private E[] discoveryOrder;
	/** The array that contains each vertex in finish order. */
	private E[] finishOrder;
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
		parent = (E[]) new Object[n];
		visited = new boolean[n];
		discoveryOrder = (E[]) new Object[n];
		finishOrder = (E[]) new Object[n];
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
	public DepthFirstSearch(Graph graph, E[] order)
	{
		this.graph = graph;
		int n = graph.getNumV();
		parent = (E[]) new Object[n];
		visited = new boolean[n];
		discoveryOrder = (E[]) new Object[n];
		finishOrder = (E[]) new Object[n];
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
	public void depthFirstSearch(E current)
	{
		/* Mark the current vertex visited. */
		visited[current.getId()] = true;
		discoveryOrder[discoverIndex++] = current;
		/* Examine each vertex adjacent to the current index. */
		Iterator<Edge> itr = graph.edgeIterator(current.getId());
		while (itr.hasNext())
		{
			E neighbor = (E) itr.next().getDest();
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
	public E[] getDiscoveryOrder()
	{
		return discoveryOrder;
	}
	public E[] getFinishOrder()
	{
		return finishOrder;
	}
}

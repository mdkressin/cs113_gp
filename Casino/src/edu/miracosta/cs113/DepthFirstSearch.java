package edu.miracosta.cs113;

import java.util.Iterator;

/**
 * Class to implement the depth-first search algorithm
 * 
 * @param <T> type that is a Vertex or one of its subclasses
 * @param <E> The element type of the vertices in the graph
 */
public class DepthFirstSearch<T extends Vertex<E>, E>
{
	// Data Fields
	/** A reference to the graph being searched. */
	private Graph<T,E> graph;
	/** Array of parents in the depth-first search. */
	private T[] parent;
	/** Flag to indicate whether this vertex has been visited. */
	private boolean[] visited;
	/** The array that contains each vertex in discover order. */
	private T[] discoveryOrder;
	/** The array that contains each vertex in finish order. */
	private T[] finishOrder;
	/** The index that indicates the discovery order. */
	private int discoverIndex = 0;
	/** The index that indicates the finish order. */
	private int finishIndex = 0;
	
	/**
	 * Construct the depth-first search of a Graph starting at vertex 0 and
	 * visiting the start vertices in ascending order.
	 * @param graph	The graph
	 */
	public DepthFirstSearch(Graph<T,E> graph) 
	{
		this.graph = graph;
		int n = graph.getNumV();
		parent = (T[]) new Object[n];
		visited = new boolean[n];
		discoveryOrder = (T[]) new Object[n];
		finishOrder = (T[]) new Object[n];
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
	public DepthFirstSearch(Graph graph, T[] order)
	{
		this.graph = graph;
		int n = graph.getNumV();
		parent = (T[]) new Object[n];
		visited = new boolean[n];
		discoveryOrder = (T[]) new Object[n];
		finishOrder = (T[]) new Object[n];
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
	public void depthFirstSearch(T current)
	{
		/* Mark the current vertex visited. */
		visited[current.getId()] = true;
		discoveryOrder[discoverIndex++] = current;
		/* Examine each vertex adjacent to the current index. */
		Iterator<Edge<T,E>> itr = graph.edgeIterator(current.getId());
		while (itr.hasNext())
		{
			T neighbor = (T) itr.next().getDest();
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
	public T[] getDiscoveryOrder()
	{
		return discoveryOrder;
	}
	public T[] getFinishOrder()
	{
		return finishOrder;
	}
}

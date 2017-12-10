package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

/**
 * A ListPokerGraph is an extension of the AbstractPokerGraph abstract class that
 * uses an array of lists to represent edges. A ListPokerGraph is a generic implementation of
 * the ListGraph from the textbook.
 *
 * @param <T> type that is a Vertex or one of its subclasses
 * @param <E> The element type of the vertices in the graph
 */
public class ListPokerGraph<T extends Vertex<E>, E> extends AbstractPokerGraph<T, E> {
	// Data Fields
	/** An array of Lists to contain the edges that originate with each vertex. */
	private List<Edge<T,E>>[] edges;
	
	// Constructor
	/**
	 * Construct a graph with the specified number of vertices and directionality
	 * @param numV	The number of vertices
	 * @param directed	The directionality flag
	 */
	public ListPokerGraph(int numV, boolean directed)
	{
		super(numV,directed);
		edges = (List<Edge<T, E>>[]) new List[numV];
		for (int i = 0; i < numV; i++)
		{
			edges[i] = new LinkedList<Edge<T,E>>();
		}
	}
	
	/**
	 * Insert a new edge into the graph
	 * @param edge	The new edge
	 */
	@Override
	public void insert(Edge<T,E> edge) 
	{
		T vertex = (T) edge.getSource();
		edges[((T) vertex).getId()].add(edge);
		//edges[edge.getSource()].add(edge);
		
		if (!isDirected())
		{
			edges[edge.getDest().getId()].add(new Edge<T,E>(edge.getDest(), 
												edge.getSource(), 
												edge.getWeight()));
		}
	}

	/**
	 * Get the edge between two vertices. If an edge does not exist, an Edge with a
	 * weight of Double.POSITIVE_INFINITY is returned.
	 * @param source	The source
	 * @param dest	The destination
	 * @return the edge between these two vertices
	 */
	@Override
	public Edge<T,E> getEdge(T source, T dest) 
	{
		Edge<T,E> target = new Edge<T,E>(source,dest, Double.POSITIVE_INFINITY);
		
		for (Edge<T,E> edge : edges[source.getId()])
		{
			if(edge.equals(target))
			{
				return edge; // Desired edge found, return it
			}
		}
		// Assert: All edges for source checked
		return target;
	}

	/**
	 * Returns an iterator to the edges that originate from a give vertex
	 * @param source	The source
	 * @return The iterator that goes through the graph starting at source
	 */
	@Override
	public Iterator<Edge<T,E>> edgeIterator(int source) 
	{
		return edges[source].iterator();
	}
	
	/**
	 * Determine whether an edge exists.
	 * @param source	The source vertex
	 * @param dest	The destination vertex
	 * @return	true if there is an edge from source to dest.
	 */
	public boolean isEdge(T source, T dest)
	{
		return edges[source.getId()].contains(new Edge<T,E>(source, dest));
	}
}

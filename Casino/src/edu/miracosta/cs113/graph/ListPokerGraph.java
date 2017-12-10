package edu.miracosta.cs113.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A ListPokerGraph is an extension of the AbstractPokerGraph abstract class that
 * uses an array of lists to represent edges. A ListPokerGraph is a generic implementation of
 * the ListGraph from the textbook.
 *
 */
public class ListPokerGraph extends AbstractPokerGraph{
	// Data Fields
	/** An array of Lists to contain the edges that originate with each vertex. */
	private List<PokerEdge>[] edges;
	
	// Constructor
	/**
	 * Construct a graph with the specified number of vertices and directionality
	 * @param numV	The number of vertices
	 * @param directed	The directionality flag
	 */
	public ListPokerGraph(int numV, boolean directed)
	{
		super(numV,directed);
		edges = (List<PokerEdge>[]) new List[numV];
		for (int i = 0; i < numV; i++)
		{
			edges[i] = new LinkedList<PokerEdge>();
		}
	}
	
	/**
	 * Insert a new edge into the graph
	 * @param edge	The new edge
	 */
	@Override
	public void insert(PokerEdge edge) 
	{
		HandVertex vertex = (HandVertex) edge.getSource();
		edges[((HandVertex) vertex).getId()].add(edge);
		//edges[edge.getSource()].add(edge);
		
		if (!isDirected())
		{
			edges[edge.getDest().getId()].add(new PokerEdge(edge.getDest(), 
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
	public PokerEdge getEdge(HandVertex source, HandVertex dest) 
	{
		PokerEdge target = new PokerEdge(source,dest, Double.POSITIVE_INFINITY);
		
		for (PokerEdge edge : edges[source.getId()])
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
	public Iterator<PokerEdge> edgeIterator(int source) 
	{
		return edges[source].iterator();
	}
	
	/**
	 * Determine whether an edge exists.
	 * @param source	The source vertex
	 * @param dest	The destination vertex
	 * @return	true if there is an edge from source to dest.
	 */
	public boolean isEdge(HandVertex source, HandVertex dest)
	{
		return edges[source.getId()].contains(new PokerEdge(source, dest));
	}
}

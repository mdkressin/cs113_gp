package edu.miracosta.cs113;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;

/**
 * A ListPokerGraph is an extension of the AbstractPokerGraph abstract class that
 * uses an array of lists to represent edges.
 */
public class ListPokerGraph<E extends Vertex> extends AbstractPokerGraph {
	// Data Fields
	/** An array of Lists to contain the edges that originate with each vertex. */
	private List<Edge>[] edges;
	
	// Constructor
	/**
	 * Construct a graph with the specified number of vertices and directionality
	 * @param numV	The number of vertices
	 * @param directed	The directionality flag
	 */
	public ListPokerGraph(int numV, boolean directed)
	{
		super(numV,directed);
		edges = new List[numV];
		for (int i = 0; i < numV; i++)
		{
			edges[i] = new LinkedList<Edge>();
		}
	}
	
	/**
	 * Insert a new edge into the graph
	 * @param edge	The new edge
	 */
	@Override
	public void insert(Edge edge) 
	{
		E vertex = (E) edge.getSource();
		edges[vertex.getId()].add(edge);
		//edges[edge.getSource()].add(edge);
		
		if (!isDirected())
		{
			edges[edge.getDest().getId()].add(new Edge(edge.getDest(), 
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
	public Edge getEdge(Object source, Object dest) 
	{
		Edge target = new Edge((E) source, (E) dest, Double.POSITIVE_INFINITY);
		
		for (Edge<E> edge : edges[((E) source).getId()])
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
	public Iterator<Edge> edgeIterator(int source) 
	{
		return edges[source].iterator();
	}
	
	/**
	 * Determine whether an edge exists.
	 * @param source	The source vertex
	 * @param dest	The destination vertex
	 * @return	true if there is an edge from source to dest.
	 */
	public boolean isEdge(E source, E dest)
	{
		return edges[source.getId()].contains(new Edge<E>(source, dest));
	}
}

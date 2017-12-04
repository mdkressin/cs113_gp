package edu.miracosta.cs113;

import java.util.Iterator;

/**
 * A MatrixPokerGraph is an extension of the AbstractPokerGraph abstract class 
 * that uses a two-dimensional array to represent the graph.
 */
public class MatrixPokerGraph extends AbstractPokerGraph {
	/**
	 * Creates an Iterator object for MatrixPokerGraph to use on arrays
	 */
	private class Iter implements Iterator<Edge>
	{

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Edge next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	// Data Fields
	/** A two-dimensional array that represents the graph. */
	private double[][] edges;
	
	// Constructor
	/**
	 * Construct a graph with the specified number of vertices and directionality
	 * @param numV	The number of vertices
	 * @param directed	The directionality flag
	 */
	public MatrixPokerGraph(int numV, boolean directed)
	{
		super(numV,directed);
		edges = new double[numV][numV];
	}
	/**
	 * Insert a new edge into the graph
	 * @param edge	The new edge
	 */
	@Override
	public void insert(Edge edge) {
		// TODO Auto-generated method stub

	}

	/**
	 * Get the edge between two vertices. If an edge does not exist, an Edge with a
	 * weight of Double.POSITIVE_INFINITY is returned.
	 * @param source	The source
	 * @param dest	The destination
	 * @return the edge between these two vertices
	 */
	@Override
	public Edge getEdge(int source, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns an iterator to the edges that originate from a give vertex
	 * @param source	The source
	 * @return The iterator that goes through the graph starting at source
	 */
	@Override
	public Iterator<Edge> edgeIterator(int source) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Determine whether an edge exists.
	 * @param source	The source vertex
	 * @param dest	The destination vertex
	 * @return	true if there is an edge from source to dest.
	 */
	public boolean isEdge(int source, int dest)
	{//stub
		return false;
	}
}

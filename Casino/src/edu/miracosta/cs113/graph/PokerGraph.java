package edu.miracosta.cs113.graph;

import java.util.Iterator;


/**
 * Interface to specify a Graph ADT. A graph is a set of vertices and a set of
 * edges. Vertices are represented by integers from 0 to n - 1. Edges are ordered
 * pairs of vertices. Each implementation of the Graph interface should provide a
 * constructor that specifies the number of vertices and whether or not the graph
 * is directed
 * 
 */
public interface PokerGraph {
	// Accessor Methods
	/**
	 * Return the number of vertices
	 * @return The number of vertices
	 */
	int getNumV();
	
	/**
	 * Determine whether this is a directed graph.
	 * @return true if this is a directed graph
	 */
	boolean isDirected();
	
	/**
	 * Insert a new edge into the graph
	 * @param edge The new edge
	 */
	void insert(PokerEdge edge);
	
	/**
	 * Get the edge between two vertices.
	 * @param source The source vertex
	 * @param dest The destination vertex
	 * @return	The Edge between these two vertices or an Edge with a weight of 
	 * 			Double.POSITIVE_INFINITY if there is no edge
	 */
	PokerEdge getEdge(HandVertex source, HandVertex dest);
	
	/**
	 * Return an iterator to the edges connected to a given vertex.
	 * @param source The source vertex
	 * @return	An Iterator<Edge> to the vertices connected to the source
	 */
	Iterator<PokerEdge> edgeIterator(int source);
}

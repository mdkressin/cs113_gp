package edu.miracosta.cs113.graph;

import edu.miracosta.cs113.Hand;

/**
 * Vertex class that constructs nodes to place into a graph data structure. 
 * @author Matt
 */
public class HandVertex {
	/** Data of item at the vertex. */
	private Hand data;
	/** Id number to use with graph algorithms. */
	private int id;
	
	/**
	 * Constructs a vertex to place into a graph with data and an id
	 * @param data	The data at the vertex
	 * @param id	The identifier of the vertex
	 */
	public HandVertex(Hand data, int id)
	{
		this.data = data;
		this.id = id;
	}
	
	// Accessor Methods
	/**
	 * Returns the data at the vertex
	 * @return	The data at the vertex
	 */
	public Hand getData()
	{
		return data;
	}
	/**
	 * Returns the id of the vertex
	 * @return	The id of the vertex
	 */
	public int getId()
	{
		return id;
	}
}


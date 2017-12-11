package edu.miracosta.cs113;

/**
 * Vertex class that constructs nodes to place into a graph data structure. 
 * @author Matt
 *
 * @param <E>	The element of the data at the vertex
 */
public class Vertex<E> {
	/** Data of item at the vertex. */
	private E data;
	/** Id number to use with graph algorithms. */
	private int id;
	
	/**
	 * Constructs a vertex to place into a graph with data and an id
	 * @param data	The data at the vertex
	 * @param id	The identifier of the vertex
	 */
	public Vertex(E data, int id)
	{
		this.data = data;
		this.id = id;
	}
	
	// Accessor Methods
	/**
	 * Returns the data at the vertex
	 * @return	The data at the vertex
	 */
	public E getData()
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
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || (getClass() != obj.getClass()))
		{
			return false;
		}
		Vertex<E> other = (Vertex<E>) obj;
		return this.id == other.id && this.data.equals(other.data);
	}
	@Override
	public String toString()
	{
		return data.toString();
	}
}

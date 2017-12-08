package edu.miracosta.cs113;

public class Vertex<E> {
	/** Data of item at the vertex. */
	private E data;
	/** Id number to use with graph algorithms. */
	private int id;
	
	public Vertex(E data, int id)
	{
		this.data = data;
		this.id = id;
	}
	
	public E getData()
	{
		return data;
	}
	public int getId()
	{
		return id;
	}
}

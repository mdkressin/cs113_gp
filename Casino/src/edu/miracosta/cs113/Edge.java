package edu.miracosta.cs113;

public class Edge {
	// Data Fields
	/** The destination vertex of an edge. */
	private int dest;
	/** The source vertex for an edge. */
	private int source;
	/** The weight. */
	private double weight;
	
	// Constructors
	/**
	 * Constructs an Edge from source to dest. Sets the weight to 1.0
	 * @param source The source vertex
	 * @param dest The destination vertex
	 */
	public Edge(int source, int dest)
	{
		
	}
	/**
	 * Constructs an Edge from source to des. Sets the weight to w.
	 * @param source The source vertex
	 * @param dest The destination vertex
	 * @param weight The weight of the edge
	 */
	public Edge(int source, int dest, double w)
	{
		
	}
	
	// Methods
	/**
	 * Compares two edges for equality. Edges are equal if their source and
	 * destination vertices are the same. The weight is not considered.
	 * @param obj The Edge to compare against
	 * @return true if the source and destination vertices of the Edges are equal
	 * 			to their counterpart's, else return false
	 */
	@Override
	public boolean equals(Object obj)
	{
		//stub
		return false;
	}
	/**
	 * Returns the destination vertex
	 * @return The destination vertex
	 */
	public int getDest()
	{
		//stub
		return 0;
	}
	/**
	 * Returns the source vertex
	 * @return The source vertex
	 */
	public int getSource()
	{
		//stub
		return 0;
	}
	/**
	 * Returns the weight
	 * @return The weight of the Edge
	 */
	public double getWeight()
	{
		//stub
		return 0;
	}
	/**
	 * Returns the hash code for an edge. The hash code depends only on the source
	 * and the destination
	 * @return The hash code for the edge
	 */
	@Override
	public int hashCode() 
	{
		//stub
		return 0;
	}
	/**
	 * Returns a string representation of the Edge
	 * @return The string representation fo the Edge
	 */
	@Override
	public String toString() 
	{
		//stub
		return "";
	}
}

package edu.miracosta.cs113.graph;

/**
 * Customized implementation of the Edge class from the text book. The Edge class will contain
 * the source vertex, the destination vertex, and the weight.
 *
 */
public class PokerEdge {
	// Data Fields
	/** The destination vertex of an edge. */
	private HandVertex dest;
	/** The source vertex for an edge. */
	private HandVertex source;
	/** The weight. */
	private double weight;
	
	// Constructors
	/**
	 * Constructs an Edge from source to dest. Sets the weight to 1.0
	 * @param source The source vertex
	 * @param dest The destination vertex
	 */
	public PokerEdge(HandVertex source, HandVertex dest)
	{
		this.source = source;
		this.dest = dest;
		weight = 1.0; // signifies non-weighted graph
	}
	/**
	 * Constructs an Edge from source to dest. Sets the weight to w.
	 * @param source The source vertex
	 * @param dest The destination vertex
	 * @param weight The weight of the edge
	 */
	public PokerEdge(HandVertex source, HandVertex dest, double w)
	{
		this.source = source;
		this.dest = dest;
		this.weight = w;
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
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		PokerEdge other = (PokerEdge) obj;
		return (this.source.equals(other.source)) && (this.dest.equals(other.dest));
	}
	/**
	 * Returns the destination vertex
	 * @return The destination vertex
	 */
	public HandVertex getDest()
	{
		return dest;
	}
	/**
	 * Returns the source vertex
	 * @return The source vertex
	 */
	public HandVertex getSource()
	{
		return source;
	}
	/**
	 * Returns the weight
	 * @return The weight of the Edge
	 */
	public double getWeight()
	{
		return weight;
	}
	/**
	 * Returns the hash code for an edge. The hash code depends only on the source
	 * and the destination
	 * @return The hash code for the edge
	 */
	@Override
	public int hashCode() 
	{
		return source.hashCode() * dest.hashCode();
	}
	/**
	 * Returns a string representation of the Edge
	 * @return The string representation of the Edge
	 */
	@Override
	public String toString() 
	{
		return "Edge from vertex " + source.toString() + " to vertex " + dest.toString();
	}
}

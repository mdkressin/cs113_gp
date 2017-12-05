package edu.miracosta.cs113;

import org.junit.Assert;
import org.junit.Test;

/**
 * Algorithm to test graph implementation
 * 1.	test constructors
 * 2.		test constructor of ListPokerGraph
 * 3.		test constructor of MatrixPokerGraph
 * 4.	test method insert
 * 5.		test method insert in ListPokerGraph
 * 6.			instantiate ListPokerGraph object
 * 7.			instantiate Edge objects
 * 8.			insert Edge objects into the ListPokerGraph
 * 9.		test method insert in MatrixPokerGraph
 * 10.			repeat steps 6-8 for MatrixPokerGraph
 * 11.
 *
 */
public class GraphTester {

	@Test
	public void testListPokerGraphConstructor()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Assert.assertFalse(list == null);
		int vertices = list.getNumV();
		Assert.assertTrue(vertices == 7);
		Assert.assertFalse(list.isDirected());
	}
	@Test
	public void testMatrixPokerGraphConstructor()
	{
		AbstractPokerGraph matrix = new MatrixPokerGraph(7,false);
		Assert.assertFalse(matrix == null);
		int vertices = matrix.getNumV();
		Assert.assertTrue(vertices == 7);
		Assert.assertFalse(matrix.isDirected());
	}
	@Test
	public void testListPokerGraphInsert()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Edge edge1 = new Edge(0,2);
		Edge edge2 = new Edge(2,4);
		Edge edge3 = new Edge(4,6);
		
		list.insert(edge1);
		Assert.assertTrue(((ListPokerGraph) list).isEdge(0,2));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(0, 2).equals(edge1));
		// test with edge not in list
		Assert.assertFalse(((ListPokerGraph) list).isEdge(2, 4));
		
		list.insert(edge2);
		list.insert(edge3);
		Assert.assertTrue(((ListPokerGraph) list).isEdge(2,4));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(2, 4).equals(edge2));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(4,6));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(4, 6).equals(edge3));	
	}
	@Test
	public void testCreateGraph() {
		
	}

}

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
 * 11.	test method getEdge
 * 12.		test method getEdge in ListPokerGraph
 * 13.			instantiate ListPokerGraph object
 * 14.			insert Edges into graph
 * 15.			getEdges from graph and check to see if they're correct
 * 16.		test method getEdge in MatrixPokerGraph
 * 17.			repeat steps 13-15 for MatrixPokerGraph
 * 18.	test method isEdge
 * 19.		test method isEdge in ListPokerGraph
 * 20.			instantiate ListPokerGraph object
 * 21.			insert Edges into graph
 * 22.			check with edges in graph and edges not in the graph
 * 23.		test method isEdge in MatrixPokerGraph
 * 24.			repeat steps 20-22 for MatrixPokerGraph
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
	public void testListPokerGraphGetEdge()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Edge edge1 = new Edge(0,2);
		Edge edge2 = new Edge(2,4);
		Edge edge3 = new Edge(4,6);
		
		list.insert(edge1);
		list.insert(edge2);
		list.insert(edge3);
		
		Edge e1 = list.getEdge(edge1.getSource(), edge1.getDest());
		Assert.assertTrue(edge1.equals(e1));
		Edge e2 = list.getEdge(edge2.getSource(), edge2.getDest());
		Edge e3 = list.getEdge(edge3.getSource(), edge3.getDest());
		Assert.assertTrue(edge2.equals(e2) && edge3.equals(e3));
	}
	@Test
	public void testListPokerGraphIsEdge()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Edge edge1 = new Edge(0,2);
		Edge edge2 = new Edge(2,4);
		Edge edge3 = new Edge(4,6);
		
		list.insert(edge1);
		list.insert(edge2);
		list.insert(edge3);
		
		Assert.assertTrue(((ListPokerGraph) list).isEdge(0, 2));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(2, 4));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(4, 6));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(6, 4));//undirected graph
		
		// test for edges that are not in the graph
		Assert.assertFalse(((ListPokerGraph) list).isEdge(0, 6));
		Assert.assertFalse(((ListPokerGraph) list).isEdge(2, 6));
		Assert.assertFalse(((ListPokerGraph) list).isEdge(0, 4));
	}
	@Test
	public void testCreateGraph() {
		
	}

}
